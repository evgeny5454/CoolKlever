package com.evgeny5454.testapp.presenter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.evgeny5454.testapp.data.entity.CharacterModel
import com.evgeny5454.testapp.databinding.ItemCharacterBinding
import com.evgeny5454.testapp.presenter.model.ItemCharacter
import javax.inject.Inject

class CharacterAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<CharacterModel, CharacterAdapter.ItemHolder>(DiffUtilCallback) {

    private var onItemClickListener: ((ItemCharacter) -> Unit)? = null

    fun setOnItemClickListener(itemCharacter: (ItemCharacter) -> Unit) {
        onItemClickListener = itemCharacter
    }

    inner class ItemHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        //private val context = itemView.context
        fun bind(character: CharacterModel) = with(binding) {
            glide.load(character.imageUri).into(image)
            name.text = character.name
            description.text = character.description
            item.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(
                        ItemCharacter(
                            imageUrl = character.imageUri,
                            name = character.name,
                            description = character.description
                        )
                    )
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemCharacterBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object DiffUtilCallback : DiffUtil.ItemCallback<CharacterModel>() {
        override fun areItemsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CharacterModel, newItem: CharacterModel): Boolean {
            return oldItem == newItem
        }

        override fun getChangePayload(oldItem: CharacterModel, newItem: CharacterModel): Any? {
            return if (oldItem != newItem) true else null
        }

    }
}
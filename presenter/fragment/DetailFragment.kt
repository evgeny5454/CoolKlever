package com.evgeny5454.testapp.presenter.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.RequestManager
import com.evgeny5454.testapp.databinding.FragmentDetailBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    @Inject
    lateinit var glide: RequestManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailBinding.inflate(layoutInflater)
        initUi()
        return binding.root
    }

    private fun initUi() {
        glide.load(args.itemCharacter.imageUrl).into(binding.image)
        binding.name.text = args.itemCharacter.name
        binding.description.text = args.itemCharacter.description
    }
}
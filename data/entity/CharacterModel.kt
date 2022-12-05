package com.evgeny5454.testapp.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.evgeny5454.testapp.other.Constants
import java.util.*

@Entity(tableName = Constants.CHARACTER_TABLE)
data class CharacterModel(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val description: String = "",
    val modified: Date,
    val imageUri: String
) : Comparable<CharacterModel> {
    override fun compareTo(other: CharacterModel): Int = name.compareTo(other.name)
}

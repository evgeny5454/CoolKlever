package com.evgeny5454.testapp.presenter.model

import java.io.Serializable

data class ItemCharacter(
    val imageUrl: String,
    val name: String,
    val description: String
) : Serializable

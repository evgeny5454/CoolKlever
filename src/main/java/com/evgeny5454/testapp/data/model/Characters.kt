package com.evgeny5454.testapp.data.model

data class Characters(val data: Data)

data class Data(val results: List<Result>)

data class Result(
    val description: String,
    val id: Int,
    val modified: String,
    val name: String,
    val thumbnail: Thumbnail,
)

data class Thumbnail(val extension: String, val path: String)
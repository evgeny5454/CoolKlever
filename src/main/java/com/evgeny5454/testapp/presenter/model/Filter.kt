package com.evgeny5454.testapp.presenter.model

data class Filter(
    val id : Int,
    val filterName : String = "",
    val isChecked : Boolean = false,
)

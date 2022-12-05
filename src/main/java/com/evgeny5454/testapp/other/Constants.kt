package com.evgeny5454.testapp.other

import com.evgeny5454.testapp.presenter.model.Filter

object Constants {
    const val BASE_URL = "http://gateway.marvel.com/v1/"
    const val CHARACTER_TABLE = "character_table"

    val filterList = listOf(
        Filter(filterName = "По алфавиту", isChecked = false, id = 0),
        Filter(filterName = "По дате изменения", isChecked = false, id = 1),
        Filter(filterName = "По алфовиту в обратном порядке", isChecked = false, id = 2),
        Filter(
            filterName = "По дате изменения в обратном порядке",
            isChecked = false,
            id = 3
        )
    )
}
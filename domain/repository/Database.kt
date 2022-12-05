package com.evgeny5454.testapp.domain.repository

import com.evgeny5454.testapp.data.entity.CharacterModel
import io.reactivex.Single

interface Database {
    fun getData(): Single<List<CharacterModel>>
}
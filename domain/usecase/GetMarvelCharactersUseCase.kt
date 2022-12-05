package com.evgeny5454.testapp.domain.usecase

import com.evgeny5454.testapp.domain.repository.Repository
import javax.inject.Inject

class GetMarvelCharactersUseCase @Inject constructor(private val repository: Repository) {
    fun execute() = repository.getData()
}
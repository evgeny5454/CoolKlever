package com.evgeny5454.testapp.domain.usecase

import com.evgeny5454.testapp.domain.repository.Repository
import javax.inject.Inject

class GetCharactersFromApiUseCase @Inject constructor(private val repository: Repository) {
    suspend fun execute() = repository.getCharacters()
}
package com.evgeny5454.testapp.domain.repository

interface Network {
    suspend fun getCharacters(): Boolean
}
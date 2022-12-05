package com.evgeny5454.testapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.evgeny5454.testapp.data.entity.CharacterModel
import io.reactivex.Single

@Dao
interface AppDao {
    @Query("SELECT * FROM CHARACTER_TABLE")
    fun getAllRecords(): Single<List<CharacterModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character: CharacterModel)
}
package com.evgeny5454.testapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.evgeny5454.testapp.data.entity.CharacterModel
import com.evgeny5454.testapp.other.Constants.CHARACTER_TABLE

@Database(entities = [CharacterModel::class], version = 1, exportSchema = false)
@TypeConverters(TypesConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private var DB_INSTANCE: AppDatabase? = null

        fun getAppDBInstance(context: Context): AppDatabase {
            if (DB_INSTANCE == null) {
                DB_INSTANCE = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    CHARACTER_TABLE
                ).allowMainThreadQueries()
                    .build()
            }
            return DB_INSTANCE!!
        }
    }
}
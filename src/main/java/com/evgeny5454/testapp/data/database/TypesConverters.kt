package com.evgeny5454.testapp.data.database

import androidx.room.TypeConverter
import java.util.*

class TypesConverters {

    @TypeConverter
    fun fromDate(date: Date?) = date?.time

    @TypeConverter
    fun toDate(long: Long?) = long?.let { Date(it) }
}
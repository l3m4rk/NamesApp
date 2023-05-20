package dev.l3m4rk.namesapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [LocalPerson::class], version = 1, exportSchema = false)
abstract class PersonDatabase : RoomDatabase() {
    abstract fun personDao(): PersonDao
}
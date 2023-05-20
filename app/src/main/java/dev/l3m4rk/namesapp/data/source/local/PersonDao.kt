package dev.l3m4rk.namesapp.data.source.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PersonDao {

    @Query("SELECT * FROM person")
    fun observeAll(): Flow<List<LocalPerson>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(person: LocalPerson)

    @Query("DELETE FROM person")
    suspend fun deleteAll()
}
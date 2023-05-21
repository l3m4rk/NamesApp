package dev.l3m4rk.namesapp.data

import dev.l3m4rk.namesapp.data.source.local.LocalPerson
import dev.l3m4rk.namesapp.data.source.local.PersonDao
import dev.l3m4rk.namesapp.data.source.local.toPerson
import dev.l3m4rk.namesapp.di.IoDispatcher
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface PersonsRepository {

    val persons: Flow<List<Person>>

    suspend fun addPerson(name: String, age: Int)

    suspend fun deleteAllPersons()
}

class PersonsRepositoryImpl @Inject constructor(
    private val localDataSource: PersonDao,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
): PersonsRepository {

    override val persons: Flow<List<Person>>
        get() = localDataSource.observeAll().map { it.map(LocalPerson::toPerson) }

    override suspend fun addPerson(name: String, age: Int) {
        val person = LocalPerson(name, age)
        withContext(dispatcher) {
            localDataSource.add(person)
        }
    }

    override suspend fun deleteAllPersons() {
        withContext(dispatcher) {
            localDataSource.deleteAll()
        }
    }
}


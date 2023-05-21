@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.l3m4rk.namesapp.domain

import dev.l3m4rk.namesapp.data.PersonsRepository
import dev.l3m4rk.namesapp.ui.main.PersonItem
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(
    private val repository: PersonsRepository
) {

    operator fun invoke(): Flow<List<PersonItem>> =
        repository.persons
            .mapLatest { it.map(PersonItem.Companion::fromPerson) }
}
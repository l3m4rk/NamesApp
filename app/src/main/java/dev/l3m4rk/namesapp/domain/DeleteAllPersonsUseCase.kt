package dev.l3m4rk.namesapp.domain

import dev.l3m4rk.namesapp.data.PersonsRepository
import javax.inject.Inject

class DeleteAllPersonsUseCase @Inject constructor(
    private val repository: PersonsRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllPersons()
    }
}
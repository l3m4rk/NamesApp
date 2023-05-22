package dev.l3m4rk.namesapp.domain

import dev.l3m4rk.namesapp.data.PersonsRepository
import javax.inject.Inject

class AddPersonUseCase @Inject constructor(
    private val computeRandomAge: ComputeRandomAgeUseCase,
    private val repository: PersonsRepository
) {

    suspend operator fun invoke(name: String) {
        repository.addPerson(name, computeRandomAge())
    }
}
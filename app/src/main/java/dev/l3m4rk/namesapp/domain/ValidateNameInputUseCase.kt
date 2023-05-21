package dev.l3m4rk.namesapp.domain

import javax.inject.Inject

/**
 * Contains rules for name input validation.
 * For now it's only that name has not be empty string
 */
class ValidateNameInputUseCase @Inject constructor() {
    operator fun invoke(name: String): Boolean {
        return name.isNotEmpty()
    }
}
package dev.l3m4rk.namesapp.domain

import javax.inject.Inject

class SanitizeInputUseCase @Inject constructor() {
    operator fun invoke(input: String): String {
        return input.trim()
    }
}
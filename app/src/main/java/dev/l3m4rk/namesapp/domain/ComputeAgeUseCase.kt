package dev.l3m4rk.namesapp.domain

import javax.inject.Inject
import kotlin.random.Random

class ComputeRandomAgeUseCase @Inject constructor() {

    operator fun invoke(): Int {
        return Random.nextInt(FROM_AGE, UNTIL_AGE)
    }

    companion object {
        private const val FROM_AGE = 0
        private const val UNTIL_AGE = 100
    }
}
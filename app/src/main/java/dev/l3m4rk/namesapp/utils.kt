package dev.l3m4rk.namesapp

import dev.l3m4rk.namesapp.ui.main.PersonItem
import kotlin.random.Random

fun getFakePersonItems(amount: Int = 30): List<PersonItem> =
    List(amount) { index ->
        PersonItem(
            id = index,
            name = "Name #$index",
            age = Random.nextInt(1, 100)
        )
    }

package dev.l3m4rk.namesapp.ui.persons

import dev.l3m4rk.namesapp.data.Person

data class PersonItem(
    val id: Int,
    val name: String,
    val age: Int
) {
    companion object {
        fun fromPerson(person: Person): PersonItem {
            return PersonItem(
                id = person.id,
                name = person.name,
                age = person.age
            )
        }
    }
}

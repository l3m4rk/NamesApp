package dev.l3m4rk.namesapp.data.source.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import dev.l3m4rk.namesapp.data.Person

@Entity(
    tableName = "person"
)
data class LocalPerson(
    val name: String,
    val age: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
)

fun LocalPerson.toPerson(): Person {
    return Person(id = id, name = name, age = age)
}

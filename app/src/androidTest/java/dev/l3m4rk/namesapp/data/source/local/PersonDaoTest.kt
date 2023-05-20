@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.l3m4rk.namesapp.data.source.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import dev.l3m4rk.namesapp.computeAge
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PersonDaoTest {

    private lateinit var db: PersonDatabase

    @Before
    fun setUp() {
        val context: Context = ApplicationProvider.getApplicationContext()
        db = Room
            .inMemoryDatabaseBuilder(context, PersonDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }


    @Test
    fun insertPersonAndGetPerson() = runTest {
        val person = LocalPerson(
            id = 1,
            name = "John",
            age = 33,
        )

        db.personDao().add(person)
        val persons = db.personDao().observeAll().first()

        assertThat(persons.size).isEqualTo(1)
        assertThat(persons.first()).isEqualTo(person)
    }

    @Test
    fun insertPersonDeleteAllNoPersons() = runTest {
        val person = LocalPerson(
            id = 1,
            name = "John",
            age = 33,
        )

        val personDao = db.personDao()
        personDao.add(person)
        personDao.deleteAll()
        val persons = personDao.observeAll().first()

        assertThat(persons.size).isEqualTo(0)
    }

    @Test
    fun insertSeveralPersonsGetSeveralPersons() = runTest {
        val persons = getFakePeople()
        persons.forEach { p ->
            db.personDao().add(p)
        }

        val addedPersons = db.personDao().observeAll().first()

        addedPersons.forEachIndexed { index, localPerson ->
            assertThat(localPerson.age).isEqualTo(persons[index].age)
            assertThat(localPerson.name).isEqualTo(persons[index].name)
        }
    }

    private fun getFakePeople() = List(4) { LocalPerson(name = "Name $it", age = computeAge()) }

    @Throws(IOException::class)
    @After
    fun tearDown() {
        db.close()
    }
}
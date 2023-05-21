package dev.l3m4rk.namesapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.l3m4rk.namesapp.data.PersonsRepository
import dev.l3m4rk.namesapp.data.PersonsRepositoryImpl
import dev.l3m4rk.namesapp.data.source.local.PersonDao
import dev.l3m4rk.namesapp.data.source.local.PersonDatabase
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Suppress("unused")
@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): PersonDatabase {
        return Room.databaseBuilder(
            context,
            PersonDatabase::class.java,
            "Persons.db"
        ).build()
    }

    @Provides
    fun providePersonDao(database: PersonDatabase): PersonDao {
        return database.personDao()
    }

    @Provides
    fun provideRepository(
        dao: PersonDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): PersonsRepository {
        return PersonsRepositoryImpl(dao, dispatcher)
    }
}
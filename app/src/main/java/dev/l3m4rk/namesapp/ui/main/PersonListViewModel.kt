@file:OptIn(ExperimentalCoroutinesApi::class)

package dev.l3m4rk.namesapp.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.namesapp.computeAge
import dev.l3m4rk.namesapp.data.PersonsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PersonsRepository,
) : ViewModel() {

    private val _name = MutableStateFlow("")
    private val _clearInput = MutableStateFlow(false)
    private val _persons = repository.persons
        .mapLatest { it.map(PersonItem.Companion::fromPerson) }

    private val addNameButtonEnabled = _name.mapLatest { name ->
        name.isNotEmpty()
    }

    val uiState =
        combine(_persons, addNameButtonEnabled, _clearInput) { persons, enableAddButton, clearInput ->
            PersonListUiState(
                personItems = persons,
                addNameEnabled = enableAddButton,
                shouldClearInput = clearInput
            )
        }.stateIn(
            initialValue = PersonListUiState(),
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000)
        )

    fun onNameChange(name: String) {
        // TODO: trim
        _clearInput.value = false
        _name.update { name.trim() }
    }

    fun addName() {
        val name: String = _name.value
        _clearInput.value = true

        viewModelScope.launch {
            repository.addPerson(name, computeAge())
        }
    }

    fun clearNames() {
        viewModelScope.launch {
            repository.deleteAllPersons()
        }
    }
}

package dev.l3m4rk.namesapp.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.namesapp.computeAge
import dev.l3m4rk.namesapp.data.PersonsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: PersonsRepository,
) : ViewModel() {

    private val _name = MutableStateFlow("")
    private val _clearInput = MutableStateFlow(false)
    private val _persons = repository.persons.map { it.map(PersonItem.Companion::fromPerson) }

    val uiState = combine(_name, _persons, _clearInput) { name, items, clearInput ->
        PersonListUiState(
            addNameEnabled = name.isNotEmpty(),
            personItems = items,
            shouldClearInput = clearInput
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = PersonListUiState()
        )

    fun onNameChange(name: String) {
        // TODO: trim
        _clearInput.value = false
        _name.value = name.trim()
    }

    fun addName() {
        val name: String = _name.value
        if (name.isEmpty()) {
            // TODO: handle error?
            return
        }
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


package dev.l3m4rk.namesapp.ui.main

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.l3m4rk.namesapp.computeAge
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class PersonListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _personItems = MutableStateFlow<List<PersonItem>>(emptyList())
    private val _name = MutableStateFlow("")
    private val _clearInput = MutableStateFlow(false)

    val uiState = combine(_name, _personItems, _clearInput) { name, items, clearInput ->
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

        _personItems.update { list ->
            list + PersonItem(id = Random.nextInt(), name = name, age = computeAge())
        }
    }

    fun clearNames() {
        // TODO: implement clear names
    }
}


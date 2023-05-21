package dev.l3m4rk.namesapp.ui.main

data class PersonListUiState(
    val personItems: List<PersonItem> = emptyList(),
    val addNameEnabled: Boolean = false,
    val shouldClearInput: Boolean = false
) {
    val showEmptyView: Boolean = personItems.isEmpty()
    val clearNamesEnabled: Boolean = personItems.isNotEmpty()
}

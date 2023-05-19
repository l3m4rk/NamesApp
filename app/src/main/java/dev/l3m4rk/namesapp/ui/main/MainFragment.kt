package dev.l3m4rk.namesapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.l3m4rk.namesapp.databinding.FragmentMainBinding
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _ui: FragmentMainBinding? = null
    private val ui get() = _ui!!

    @Inject
    lateinit var adapter: PersonListAdapter

    private val viewModel: PersonListViewModel by viewModels()

    init {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { uiState ->
                    handleState(uiState)
                }
            }
        }
    }

    private fun handleState(uiState: PersonListUiState) {
        ui.emptyStateView.isVisible = uiState.showEmptyView
        ui.namesList.isVisible = uiState.showPersonList
        ui.clearNamesButton.isEnabled = uiState.clearNamesEnabled
        ui.addNameButton.isEnabled = uiState.addNameEnabled
        adapter.submitList(uiState.personItems)
        if (uiState.shouldClearInput) {
            ui.nameInputField.setText("")
        }
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _ui = FragmentMainBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupNameInput()
        setupButtons()
        setupNamesList()
    }

    private fun setupNameInput() {
        ui.nameInputField.doAfterTextChanged {
            viewModel.onNameChange(it!!.toString())
        }
    }

    private fun setupButtons() {
        ui.addNameButton.setOnClickListener {
            viewModel.addName()
        }
        ui.clearNamesButton.setOnClickListener {
            viewModel.clearNames()
        }
    }

    private fun setupNamesList() {
        ui.namesList.layoutManager = LinearLayoutManager(context)
        ui.namesList.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }
}
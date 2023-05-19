package dev.l3m4rk.namesapp.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import dev.l3m4rk.namesapp.databinding.FragmentMainBinding
import dev.l3m4rk.namesapp.getFakePersonItems
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private var _ui: FragmentMainBinding? = null
    private val ui get() = _ui!!

    @Inject
    lateinit var adapter: PersonListAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _ui = FragmentMainBinding.inflate(inflater, container, false)
        return ui.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ui.emptyStateView.isVisible = false
        // TODO: setup user input
        ui.nameInputField.doAfterTextChanged { }

        ui.addNameButton.setOnClickListener {
            Toast.makeText(context, "Add name clicked", Toast.LENGTH_SHORT).show()
        }
        ui.clearNamesButton.setOnClickListener {
            Toast.makeText(context, "Clear names clicked", Toast.LENGTH_SHORT).show()
        }

        setupNamesList()
    }

    private fun setupNamesList() {
        ui.namesList.layoutManager = LinearLayoutManager(context)
        ui.namesList.adapter = adapter

        adapter.submitList(getFakePersonItems())
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _ui = null
    }
}
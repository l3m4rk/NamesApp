package dev.l3m4rk.namesapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import dagger.hilt.android.scopes.FragmentScoped
import dev.l3m4rk.namesapp.R
import dev.l3m4rk.namesapp.databinding.ItemPersonBinding
import javax.inject.Inject

@FragmentScoped
class PersonListAdapter @Inject constructor() : ListAdapter<PersonItem, PersonViewHolder>(PersonDiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val ui = ItemPersonBinding.inflate(inflater, parent, false)
        return PersonViewHolder(ui)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class PersonViewHolder(private val ui: ItemPersonBinding) : ViewHolder(ui.root) {

    fun bind(item: PersonItem) {
        ui.root.setOnClickListener { }
        val resources = ui.root.context.resources
        ui.personName.text = item.name
        ui.personAge.text = resources.getQuantityString(R.plurals.person_age, item.age, item.age)
    }
}

object PersonDiffCallback : DiffUtil.ItemCallback<PersonItem>() {
    override fun areItemsTheSame(
        oldItem: PersonItem,
        newItem: PersonItem): Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(
        oldItem: PersonItem,
        newItem: PersonItem): Boolean = oldItem == newItem
}

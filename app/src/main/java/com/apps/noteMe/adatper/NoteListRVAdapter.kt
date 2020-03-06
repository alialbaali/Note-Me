package com.apps.noteMe.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apps.noteMe.databinding.NoteListItemBinding
import com.apps.noteMe.model.NoteList

class NoteListRVAdapter : ListAdapter<NoteList, NoteListItemViewHolder>(NoteListItemCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteListItemViewHolder {
        return NoteListItemViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NoteListItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.id = item.id
    }
}

class NoteListItemViewHolder(private val binding: NoteListItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    var id = 0L

    init {
        binding.root.setOnClickListener {

        }
    }

    companion object {
        fun create(parent: ViewGroup): NoteListItemViewHolder {
            return NoteListItemViewHolder(
                NoteListItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    fun bind(noteList: NoteList) {
        binding.tv.text = noteList.title
    }

}

private class NoteListItemCallback() : DiffUtil.ItemCallback<NoteList>() {
    override fun areItemsTheSame(oldItem: NoteList, newItem: NoteList): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteList, newItem: NoteList): Boolean {
        return oldItem.id == newItem.id
    }

}
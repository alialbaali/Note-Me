package com.apps.noteMe.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apps.noteMe.R
import com.apps.noteMe.databinding.NoteItemBinding
import com.apps.noteMe.model.Note


// RecyclerView Adapter
class RVAdapter(private val noteListener: NoteListener) :
    ListAdapter<Note, ItemViewHolder>(ItemDiffCallback()) {

    // function for creating ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        return ItemViewHolder.createViewHolder(parent, noteListener)
    }

    // function for binding ViewHolder
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.id = item.id
    }
}

// ViewHolder class for list_item.xml
class ItemViewHolder private constructor(
    private val binding: NoteItemBinding,
    private val noteListener: NoteListener
) : RecyclerView.ViewHolder(binding.root) {

    // value to keep track of the note id
    var id = 0L

    init {
        binding.root.setOnClickListener {
            noteListener.onNoteClick(id)
        }
    }

    companion object {
        fun createViewHolder(parent: ViewGroup, noteListener: NoteListener): ItemViewHolder {
            return ItemViewHolder(
                NoteItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                ), noteListener
            )
        }
    }

    fun bind(item: Note?) {
        binding.titleTextView.text = item?.title
        binding.contentTextView.text = item?.content
    }
}

interface NoteListener {
    fun onNoteClick(id: Long)
}


private class ItemDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}
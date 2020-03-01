package com.apps.noteMe.adatper

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.apps.noteMe.R
import com.apps.noteMe.model.Note
import com.apps.noteMe.databinding.NoteItemBinding


// RecyclerView Adapter
class RVAdapter(private val noteListener: NoteListener) : ListAdapter<Note, ItemViewHolder>(ItemDiffCallback()) {

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

    fun onItemMove(fromPosition: Int, toPosition: Int) {
       noteListener.onNoteMove(fromPosition, toPosition)
        notifyItemMoved(fromPosition, toPosition)
    }

    fun onItemSwipe(position: Int) {
        noteListener.onNoteSwipe(getItem(position))
        notifyItemRemoved(position)
    }
}

// ViewHolder class for list_item.xml
class ItemViewHolder private constructor(private val binding: NoteItemBinding, private val noteListener: NoteListener) : RecyclerView.ViewHolder(binding.root) {

    // value to keep track of the note id
    var id = 0L
    init {
        binding.root.setOnClickListener {
            noteListener.onNoteClick(id)
        }
    }

    companion object {
        fun createViewHolder(parent: ViewGroup, noteListener: NoteListener): ItemViewHolder {
            return ItemViewHolder(NoteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false), noteListener)
        }
    }

    fun bind(item: Note?) {
        binding.titleTextView.text = item?.title
        binding.contentTextView.text = item?.content
    }
}

interface NoteListener {
    fun onNoteClick(id: Long)
    fun onNoteMove(fromPosition: Int, toPosition: Int)
    fun onNoteSwipe(note: Note)
}


private class ItemDiffCallback : DiffUtil.ItemCallback<Note>() {

    override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
        return oldItem == newItem
    }
}

class ItemTouchHelperAdapter(private val RVAdapter: RVAdapter) : ItemTouchHelper.Callback(){

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
        val swipeFlags= ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        RVAdapter.onItemMove(viewHolder.adapterPosition, target.adapterPosition)
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        RVAdapter.onItemSwipe(viewHolder.adapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)
        viewHolder?.itemView?.setBackgroundResource(R.drawable.note_shape_selected)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        super.clearView(recyclerView, viewHolder)
        viewHolder.itemView.setBackgroundResource(R.drawable.note_shape)
    }


}


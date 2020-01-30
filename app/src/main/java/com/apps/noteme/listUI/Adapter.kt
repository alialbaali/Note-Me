package com.apps.noteme.listUI

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.apps.noteme.R
import com.apps.noteme.database.Note

class Adapter(val list: List<Note>) : RecyclerView.Adapter<Adapter.AdapterViewHolder>() {
    class AdapterViewHolder(listItem: View) : RecyclerView.ViewHolder(listItem) {
        val title = listItem.findViewById<TextView?>(R.id.title_textView)
        val content = listItem.findViewById<TextView?>(R.id.content_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter.AdapterViewHolder {
        return AdapterViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.note_item,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        holder.title?.text = list[position].title
        holder.content?.text = list[position].content
    }

}
package com.example.myapplication.aAdapters
import com.example.myapplication.Model.note
import com.example.myapplication.R
import com.example.myapplication.Database.NoteRoomDatabase

import kotlinx.android.synthetic.main.note_item.view.*  // chu y thu vien
import androidx.recyclerview.widget.RecyclerView


import kotlinx.android.synthetic.main.activity_new_note.*
import kotlinx.android.synthetic.main.note_item.view.*


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class NoteListAdapter internal constructor(context: Context, val noteDB: NoteRoomDatabase) : RecyclerView.Adapter<NoteListAdapter.NoteViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var notes = emptyList<note>()

    private val job = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + job)

    internal fun setNotes(notes: List<note>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val itemView = inflater.inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentNote = notes[position]

        holder.titleItemView.text = currentNote.user
        holder.contentItemView.text = currentNote.email
        holder.deleteItemView.setOnClickListener {
            uiScope.launch {
                // Delete currentNote
                noteDB?.noteDao()?.delete(currentNote)

                // Get all noted again
                notes = noteDB?.noteDao()?.getAllNotes()
                notifyDataSetChanged()
            }
        }
    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleItemView = itemView.note_title
        val contentItemView = itemView.note_content
        val deleteItemView = itemView.button_delete
    }
}

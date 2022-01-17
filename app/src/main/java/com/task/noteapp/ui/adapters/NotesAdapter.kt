package com.task.noteapp.ui.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.task.noteapp.databinding.ItemNoteBinding
import com.task.noteapp.models.Note
import com.task.noteapp.ui.notes_list.NotesListFragmentDirections
import com.task.noteapp.util.NotesDiffUtil
import com.task.noteapp.util.TimeUtil
import javax.inject.Inject

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    var listOfNotes = emptyList<Note>()

    @Inject
    lateinit var glideRequestManager: RequestManager

    class ViewHolder(private val itemNoteBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(itemNoteBinding.root) {
        fun bind(note: Note, context: Context) {
            itemNoteBinding.apply {
                titleTv.text = note.title
                contentTv.text = note.content
                dateTv.text = TimeUtil.getDateFormat(note.date)
                noteItemLayout.setOnClickListener {
                    val action =
                        NotesListFragmentDirections.toUpdateNoteFragment(note)
                    it.findNavController().navigate(action)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemNoteBinding =
            ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemNoteBinding)
    }

    override fun getItemCount(): Int = listOfNotes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = listOfNotes[position]
        holder.bind(note, holder.itemView.context)
    }

    fun setNotesList(newListOfNotes: List<Note>) {
        val notesDiffResult = DiffUtil.calculateDiff(
            NotesDiffUtil(
                oldNotesList = listOfNotes,
                newNotesList = newListOfNotes
            )
        )
        this.listOfNotes = newListOfNotes
        notesDiffResult.dispatchUpdatesTo(this)
    }
}
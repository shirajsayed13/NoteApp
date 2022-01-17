package com.task.noteapp.util

import androidx.recyclerview.widget.DiffUtil
import com.task.noteapp.models.Note

class NotesDiffUtil(
    private val oldNotesList: List<Note>,
    private val newNotesList: List<Note>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldNotesList.size

    override fun getNewListSize(): Int = newNotesList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotesList[oldItemPosition].id == newNotesList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNotesList[oldItemPosition] == newNotesList[newItemPosition]
    }

}
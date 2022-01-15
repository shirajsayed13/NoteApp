package com.task.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.task.noteapp.models.Note

@Database(
    entities = [Note::class],
    version = 1
)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao
}
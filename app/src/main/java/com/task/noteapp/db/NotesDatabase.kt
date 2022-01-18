package com.task.noteapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.noteapp.models.Note

@Database(
    entities = [Note::class],
    version = 3
)
abstract class NotesDatabase : RoomDatabase() {

    abstract fun getNotesDao(): NotesDao

}
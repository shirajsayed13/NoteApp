package com.task.noteapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.noteapp.models.Note

@Dao
interface NotesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNote(note: Note)

    @Update
    suspend fun updateNote(note: Note)

    @Delete
    suspend fun deleteNote(note: Note)

    @Query("SELECT * FROM notes ORDER by date DESC")
    fun getAllNotes(): LiveData<List<Note>>

}
package com.task.noteapp.di

import android.content.Context
import androidx.room.Room
import com.task.noteapp.db.NoteDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context.applicationContext,
        NoteDatabase::class.java,
        "notes.db"
    ).build()

    @Singleton
    @Provides
    fun provideDao(
        database: NoteDatabase
    ) = database.getNotesDao()
}
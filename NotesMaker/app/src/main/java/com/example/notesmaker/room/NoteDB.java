package com.example.notesmaker.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.notesmaker.Converters;
import com.example.notesmaker.Note;

@Database(entities = {Note.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class NoteDB extends RoomDatabase {
    public abstract NoteDao getNoteDao();
}

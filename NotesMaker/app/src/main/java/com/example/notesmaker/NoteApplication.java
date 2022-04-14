package com.example.notesmaker;

import android.app.Application;

import androidx.room.Room;

import com.example.notesmaker.room.NoteDB;
import com.example.notesmaker.room.NoteDao;

public class NoteApplication extends Application {
    public static NoteDB noteDB;
    private static NoteDao noteDao;
    @Override
    public void onCreate() {
        super.onCreate();
        noteDB = Room.databaseBuilder(getApplicationContext(),
                NoteDB.class, "NoteDB").build();
        noteDao = noteDB.getNoteDao();
    }
}

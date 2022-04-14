package com.example.notesmaker.room;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.notesmaker.Note;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

@Dao
public interface NoteDao {
    @Insert
    void addNote(Note note);

    @Query("SELECT * FROM Note")
    List<Note> getNotes();

    @Query("UPDATE NOTE SET title=:title, notes=:text, date=:date WHERE noteId = :noteId")
    void updateNote(String title, String text, int noteId,Date date);

    @Query("DELETE FROM NOTE WHERE noteId = :noteId")
    void delete(int noteId);
}

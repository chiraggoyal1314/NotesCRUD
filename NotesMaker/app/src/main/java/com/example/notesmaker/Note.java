package com.example.notesmaker;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.time.LocalTime;
import java.util.Date;

@Entity(tableName = "Note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    public int noteId;
//    @ColumnInfo(name = "date")
    public Date date;
//    @ColumnInfo(name = "title")
    public String title;
//    @ColumnInfo(name = "notes")
    public String notes;

    public Note(Date date, String title, String notes){
        this.date = date;
        this.title = title;
        this.notes = notes;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
package com.example.notesmaker;

import static com.example.notesmaker.NoteApplication.noteDB;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.notesmaker.databinding.ActivityNoteEditorBinding;
import com.example.notesmaker.room.NoteDB;
import com.example.notesmaker.room.NoteDao;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

public class NoteEditorActivity extends AppCompatActivity {

    int noteID;
//    static ArrayList<String> notes = new ArrayList<String>();
    Note pairr;
    private ActivityNoteEditorBinding binding;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        binding = ActivityNoteEditorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Intent intent = getIntent();
        noteID = intent.getIntExtra("noteID", -1);
        if(noteID != -1) {
            binding.editText.setText(MainActivity.notes.get(noteID).notes);
            binding.title.setText(MainActivity.notes.get(noteID).title);
            pairr = MainActivity.notes.get(noteID);
        } else {
            pairr = null;
        }
        binding.editText.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(pairr==null){
                    pairr = new Note(new Date(),binding.title.getText().toString(),
                            binding.editText.getText().toString());
                } else {
                    pairr.setDate(new Date());
                    pairr.setTitle(binding.title.getText().toString());
                    pairr.setNotes(binding.editText.getText().toString());
                }
//                MainActivity.notes.get(noteID).setNotes(String.valueOf(s));
//                MainActivity.notes.get(noteID).setDate(new Date());
//                MainActivity.arrayAdapter.notifyDataSetChanged();
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesmaker", Context.MODE_PRIVATE);
//                HashSet<Pairr> set = new HashSet<Pairr>(MainActivity.notes);
//                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
        binding.title.addTextChangedListener(new TextWatcher()
        {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after)
            {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {
                if(pairr==null){
                    pairr = new Note(new Date(),binding.title.getText().toString(),
                            binding.editText.getText().toString());
                } else {
                    pairr.setDate(new Date());
                    pairr.setTitle(binding.title.getText().toString());
                    pairr.setNotes(binding.editText.getText().toString());
                }
//                MainActivity.notes.get(noteID).title = String.valueOf(s);
//                MainActivity.notes.get(noteID).date = new Date();
//                MainActivity.arrayAdapter.notifyDataSetChanged();
//                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesmaker", Context.MODE_PRIVATE);
//                HashSet<Pairr> set = new HashSet<Pairr>(MainActivity.notes);
//                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s)
            {

            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //Save data in Room
        saveData();
//        MainActivity.arrayAdapter.notifyDataSetChanged();
        MainActivity.arrayAdapter.updateNotes(MainActivity.notes);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    private void saveData(){
        if(noteID != -1) {
//            binding.editText.setText(MainActivity.notes.get(noteID).notes);
//            binding.title.setText(MainActivity.notes.get(noteID).title);
            if(pairr.getTitle().isEmpty() && pairr.getNotes().isEmpty()) {
                MainActivity.notes.remove(pairr);
                delete(pairr.noteId);
            }
            else{
                MainActivity.notes.set(noteID,pairr);
                updateNote();
            }
        } else if(!binding.editText.getText().toString().isEmpty()
                ||!binding.title.getText().toString().isEmpty()) {
            // as initially, the note is empty
                pairr.setDate(new Date());
                MainActivity.notes.add(pairr);
                addNote();
//                MainActivity.arrayAdapter.notifyDataSetChanged();
//            MainActivity.arrayAdapter.notifyDataSetChanged();
        }

    }
    private void addNote() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                noteDB.getNoteDao().addNote(pairr);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
//                MainActivity.arrayAdapter.updateNotes(MainActivity.notes);
//                Collections.sort(MainActivity.notes, (a,b)->{
//                    return b.getDate().compareTo(a.getDate());
//                });
            }
        }.execute();
    }
    private void updateNote() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
//                pairr.date = new Date();
                noteDB.getNoteDao().updateNote(pairr.title, pairr.notes, pairr.noteId,pairr.date);
                return null;
            }

            @Override
            protected void onPostExecute(Void unused) {
                super.onPostExecute(unused);
//                MainActivity.arrayAdapter.updateNotes(MainActivity.notes);
//                Collections.sort(MainActivity.notes, (a, b)->{
//                    return b.getDate().compareTo(a.getDate());
//                });
            }
        }.execute();
    }

    private void delete(int id) {
        new AsyncTask<Void, Void, NoteDB>() {
            @Override
            protected NoteDB doInBackground(Void... voids) {
                noteDB.getNoteDao().delete(id);
                return null;
            }

            @Override
            protected void onPostExecute(NoteDB db) {
                super.onPostExecute(db);
//                Collections.sort(notes, (a,b)->{
//                    return b.getDate().compareTo(a.getDate());
//                });
                MainActivity.arrayAdapter.updateNotes(MainActivity.notes);
//                arrayAdapter = new CustomAdapter(notes,getApplicationContext());
//                listView.setAdapter(arrayAdapter);

            }
        }.execute();
    }
}

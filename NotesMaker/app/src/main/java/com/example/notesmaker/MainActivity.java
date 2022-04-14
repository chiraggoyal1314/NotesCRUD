package com.example.notesmaker;

import static com.example.notesmaker.NoteApplication.noteDB;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.notesmaker.room.NoteDB;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    static ArrayList<Note> notes = new ArrayList<Note>();
    @SuppressLint("StaticFieldLeak")
    static CustomAdapter arrayAdapter;
    ListView listView;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);
        ImageView add_note = (ImageView)findViewById(R.id.add_note);

//        notes.add(new Pairr(new Date(),"Heading Note 1", "Example Note 1"));
        Collections.sort(notes, (a,b)->{
            return b.getDate().compareTo(a.getDate());
        });
//        List<String> headings = notes.stream().map(x-> x.title).collect(Collectors.toList());
//        List<String> subheadings = notes.stream().map(x-> x.notes).collect(Collectors.toList());
//        arrayAdapter = new CustomAdapter(this, headings, subheadings);
        getNotes();
        arrayAdapter = new CustomAdapter(notes,getApplicationContext());
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("noteID", position);
                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id)
            {
                new AlertDialog.Builder(MainActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle("Delete?")
                        .setMessage("Are you sure you want to delete this note?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which)
                            {
                                notes.remove(position);
                                delete(position);
                                arrayAdapter.notifyDataSetChanged();
                                arrayAdapter.updateNotes(notes);
                            }
                        })

                        .setNegativeButton("No", null)
                        .show();

                return true;
            }
        });
        add_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
        // put your code here...
        Collections.sort(notes, (a,b)->{
            return b.getDate().compareTo(a.getDate());
        });
        arrayAdapter.setNotifyOnChange(true);
        arrayAdapter.updateNotes(notes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        super.onOptionsItemSelected(item);

        if(item.getItemId() == R.id.add_note)
        {
            Intent intent = new Intent(MainActivity.this, NoteEditorActivity.class);
            startActivity(intent);
            return true;
        }

        return false;
    }
    private void getNotes() {
        new AsyncTask<Void, Void, NoteDB>() {
            @Override
            protected NoteDB doInBackground(Void... voids) {
                notes = new ArrayList<>(noteDB.getNoteDao().getNotes());
                return null;
            }

            @Override
            protected void onPostExecute(NoteDB db) {
                super.onPostExecute(db);
                Collections.sort(notes, (a,b)->{
                    return b.getDate().compareTo(a.getDate());
                });
                arrayAdapter.updateNotes(notes);
//                arrayAdapter = new CustomAdapter(notes,getApplicationContext());
//                listView.setAdapter(arrayAdapter);

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
                arrayAdapter.updateNotes(notes);
//                arrayAdapter = new CustomAdapter(notes,getApplicationContext());
//                listView.setAdapter(arrayAdapter);

            }
        }.execute();
    }
}
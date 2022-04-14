package com.example.notesmaker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.notesmaker.MainActivity;

public class StartScreen extends AppCompatActivity {

    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);
        intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
        findViewById(R.id.notes_logo)
                .postDelayed(() -> startActivity(intent), 1000);
    }

    @Override
    protected void onResume() {
        super.onResume();
//        findViewById(R.id.notes_logo).postDelayed(() -> startActivity(intent), 1000);
    }
}

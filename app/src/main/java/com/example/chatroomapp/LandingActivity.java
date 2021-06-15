package com.example.chatroomapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LandingActivity extends AppCompatActivity {
    private EditText input_username;
    private Button begin_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        input_username = findViewById(R.id.input_username);
        begin_btn = findViewById(R.id.enter);
        getSupportActionBar().hide();

        begin_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendToMain = new Intent(getApplicationContext(),MainActivity.class);
                sendToMain.putExtra("user_name", input_username.getText().toString());
                startActivity(sendToMain);
            }
        });
    }
}
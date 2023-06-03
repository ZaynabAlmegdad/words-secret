package com.example.hangman;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;





public class Profile extends AppCompatActivity {
    public static String email;
    TextView txtemail ,score , level;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        GameActivity.score = Integer.parseInt(SaveData.loadData(this,"score"));
        GameActivity.level = Integer.parseInt(SaveData.loadData(this,"level"));


        txtemail =  findViewById(R.id.email);
        score =  findViewById(R.id.score);
        level =  findViewById(R.id.level);
        txtemail.setText(email);
        Button back = (Button)findViewById(R.id.Prof_backButton);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        score.setText(""+GameActivity.score);
        level.setText(""+GameActivity.level);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }



}

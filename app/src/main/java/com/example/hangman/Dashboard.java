package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class Dashboard extends AppCompatActivity {

    ImageView bgapp;
    LinearLayout splashtext, hometext, menu;
    ImageView start, profile, guide ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard_activity);


        bgapp = (ImageView) findViewById(R.id.bgapp);
        splashtext =(LinearLayout) findViewById(R.id.splashtext);
        hometext =(LinearLayout) findViewById(R.id.hometext);
        menu =(LinearLayout) findViewById(R.id.menu);


        bgapp.animate().translationY(-1500).setDuration(800).setStartDelay(300);
        splashtext.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);

        start = findViewById(R.id.start_game);
        profile = findViewById(R.id.profile);
        guide = findViewById(R.id.guide);


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,GameActivity.class );
                startActivity(intent);
            }
        });

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this,Profile.class );
                startActivity(intent);
            }
        });

        guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Dashboard.this, guide.class );
                startActivity(intent);
            }
        });

    }
}

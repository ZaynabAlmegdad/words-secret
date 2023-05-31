package com.example.hangman;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.example.hangman.DatabaseHelper;

import android.content.Intent;
import android.os.Bundle;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    public  static int score=0, level=1;
    private List<Question> wordList;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        GameFragment gameFragment = new GameFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout, gameFragment);
        fragmentTransaction.commit();

    }


}
package com.example.hangman;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    public  static int score=1, level=1;
    private List<Question> wordList;

    private int BEGINNER_THRESHOLD = 1;
    private static int INTERMEDIATE_THRESHOLD = 5;
    private static int ADVANCED_THRESHOLD = 10;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Secret Word");
        GameFragment gameFragment = new GameFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.layout, gameFragment);
        fragmentTransaction.commit();

    }
    public static int getLevelBasedOnScore(int score) {
        if (score < INTERMEDIATE_THRESHOLD) {
            return 1; // BEGINNER
        } else if (score < ADVANCED_THRESHOLD) {
            return 2; // INTERMEDIATE
        } else {
            return 3; // ADVANCED
        }
    }
    public static void updateLevel() {
        int newLevel = getLevelBasedOnScore(GameActivity.score);
        if (newLevel != GameActivity.level) {
            GameActivity.level = newLevel;
            // You can also update the UI to reflect the new level
        }
    }


}

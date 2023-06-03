package com.example.hangman;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Locale;

public class AchievementsPopup extends PopupWindow {
    private Context mContext;
    private View mView;
    private TextView mBeginnerText, mIntermediateText, mAdvancedText;
    private ProgressBar mBeginnerProgress, mIntermediateProgress, mAdvancedProgress;
    private DatabaseHelper dbHelper;

    public AchievementsPopup(Context context, DatabaseHelper databaseHelper) {
        super(context);
        mContext = context;
        mView = LayoutInflater.from(context).inflate(R.layout.achievements, null);
        setContentView(mView);
        dbHelper = databaseHelper;

        //mBeginnerText = mView.findViewById(R.id.beginner_text);
       // mIntermediateText = mView.findViewById(R.id.intermediate_text);
        //mAdvancedText = mView.findViewById(R.id.advanced_text);

        mBeginnerProgress = mView.findViewById(R.id.beginner_progress);
        mIntermediateProgress = mView.findViewById(R.id.intermediate_progress);
        mAdvancedProgress = mView.findViewById(R.id.advanced_progress);

        updateProgressBars();
    }

    void updateProgressBars() {
        int beginnerScore= 0;
        int intermediateScore= 10;
        int advancedScore= 30;

        mBeginnerProgress.setProgress(beginnerScore);
        mIntermediateProgress.setProgress(intermediateScore);
        mAdvancedProgress.setProgress(advancedScore);

        mBeginnerText.setText(String.format(Locale.getDefault(), "%d/10", beginnerScore));
        mIntermediateText.setText(String.format(Locale.getDefault(), "%d/20", intermediateScore));
        mAdvancedText.setText(String.format(Locale.getDefault(), "%d/40", advancedScore));
    }
}



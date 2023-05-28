package com.example.hangman;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameFragment extends Fragment {
    private final static String TAG = "MainActivity";
    private DatabaseHelper dbHelper;
    private String word;
    private String hint;
    private List<Question> wordList = new ArrayList<>();
    public GameFragment() {
        // Required empty public constructor
    }
    private TextView txtWord;
    private TextView high;
    private TextView txtScore, txtLevel;
    private TextView txtQuestion;
    private Button play, reset, solve;
    public String result = " ";
    public EditText editAnswer;
    private EditText letter;
    private ImageView imagHang;
    public Question currentWord;
    private int count = 0;


    boolean isStart = false;
    View view = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_game, container, false);
        dbHelper = new DatabaseHelper(getActivity());
        initView();
        String[] wordAndHint = getRandomWordAndHint(GameActivity.level);
        word = wordAndHint[0];
        hint = wordAndHint[1];

        // Set up the game with the fetched word and hint
        // ...

        return view;
    }

    private void initView() {
        getWordsFromDatabase();
        wordList = getWordsFromDatabase();
        dbHelper = new DatabaseHelper(requireContext());
        editAnswer = view.findViewById(R.id.letter);
        txtWord = view.findViewById(R.id.word);
        imagHang = view.findViewById(R.id.hang);
        play = view.findViewById(R.id.play);
        reset = view.findViewById(R.id.reset);
        solve = view.findViewById(R.id.solve);
        editAnswer.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
        solve.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        txtQuestion = view.findViewById(R.id.txtQuestion);
        editAnswer.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editAnswer.setVisibility(View.GONE);
        editAnswer.setImeOptions(EditorInfo.IME_ACTION_GO);
        txtScore = view.findViewById(R.id.score);
        txtLevel = view.findViewById(R.id.high);
        GameActivity.score = Integer.parseInt(SaveData.loadData(getActivity(), "score"));
        GameActivity.level = String.valueOf(Integer.parseInt(SaveData.loadData(getActivity(), "level")));
        txtScore.setText("" + GameActivity.score);
        txtLevel.setText("" + GameActivity.level);
        editAnswer.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_GO) {
                    checkLetter();
                }
                return true;
            }
        });
        editAnswer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() != 0)
                    checkLetter();
            }
        });

        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                int r_id = getResources().getIdentifier("hang_0", "drawable", getActivity().getApplication().getPackageName());
                imagHang.setImageDrawable(getResources().getDrawable(r_id));
                currentWord = null;
                currentWord = pickGoodStarterWord();
                txtQuestion.setText(currentWord.question);
                result = "";
                txtWord.setText("");
                resrWord();
                editAnswer.setEnabled(true);
                editAnswer.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editAnswer, InputMethodManager.SHOW_IMPLICIT);
                play.setVisibility(View.GONE);
                solve.setVisibility(View.VISIBLE);
                editAnswer.setVisibility(View.VISIBLE);
            }
        });
        solve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtWord.setText(currentWord.answer);
                txtQuestion.setText("");
                editAnswer.setVisibility(View.GONE);
                solve.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);

            }
        });
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count = 0;
                int r_id = getResources().getIdentifier("hang_0", "drawable", getActivity().getApplication().getPackageName());
                imagHang.setImageDrawable(getResources().getDrawable(r_id));
                currentWord = null;
                currentWord = pickGoodStarterWord();
                txtQuestion.setText(currentWord.question);
                result = "";
                txtWord.setText("");
                resrWord();
                editAnswer.setEnabled(true);
                editAnswer.requestFocus();
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editAnswer, InputMethodManager.SHOW_IMPLICIT);
                play.setVisibility(View.GONE);
                reset.setVisibility(View.VISIBLE);
                solve.setVisibility(View.VISIBLE);
                editAnswer.setVisibility(View.VISIBLE);
            }
        });
    }


    private void resrWord() {
        for (int i = 0; i < currentWord.answer.length(); i ++) {
            result += "_ ";
        }
        txtWord.setText(currentWord.getHint());
    }

    private void checkLetter() {
        if (currentWord == null) return;
        int i = 0, flag = 0, k = 0;
        String letter = editAnswer.getText().toString().trim().toLowerCase();
        currentWord.answer = currentWord.answer.toString().trim().toLowerCase();
        Log.i(TAG, "checkLetter : letter " + letter);
        for (i = 0; i < currentWord.answer.length(); ++i) {
            Log.i(TAG, "checkLetter : currentWord.answer.indexOf(letter,i) " + currentWord.answer.indexOf(letter, i));
            if (currentWord.answer.indexOf(letter, i) != -1) {
                k = currentWord.answer.indexOf(letter, i);
                result = result.substring(0, 2 * k) + letter + " " + result.substring(2 * k + 2);
                Log.i(TAG, "checkLetter : result.substring(2 * k + 2)  " + result.substring(2 * k + 2));
                Log.i(TAG, "checkLetter : result.substring(0, 2 * k)  " + result.substring(0, 2 * k));
                Log.i(TAG, "checkLetter :result " + result);
                txtWord.setText(result);
                i = k;
                flag = 1;
                if (result.indexOf("_") == -1) {
                    isStart = false;
                    txtWord.setText("You won !!");
                    GameActivity.score += 1;
                    GameActivity.level = String.valueOf(GameActivity.score / 2);
                    SaveData.saveData(getActivity(), "score", "" + GameActivity.score);
                    SaveData.saveData(getActivity(), "level", "" + GameActivity.level);
                    txtScore.setText("" + GameActivity.score);
                    txtLevel.setText("" + GameActivity.level);
                    InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.showSoftInput(editAnswer, InputMethodManager.HIDE_IMPLICIT_ONLY);
                    editAnswer.setVisibility(View.GONE);
                    reset.setVisibility(View.GONE);
                    solve.setVisibility(View.GONE);
                    play.setVisibility(View.VISIBLE);
                    txtQuestion.setText("");
                }
            }
        }
        if (flag == 0) {
            count = count + 1;
            if (count >= 6) {
                isStart = false;
                txtWord.setText("You lost !!");
                if (GameActivity.score > 0)
                    GameActivity.score -= 1;
                SaveData.saveData(getActivity(), "score", "" + GameActivity.score);
                solve.setVisibility(View.GONE);
                txtScore.setText("" + GameActivity.score);
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editAnswer, InputMethodManager.HIDE_IMPLICIT_ONLY);
                editAnswer.setVisibility(View.GONE);
                reset.setVisibility(View.GONE);
                solve.setVisibility(View.GONE);
                play.setVisibility(View.VISIBLE);
                txtQuestion.setText("");
            }
            Log.i(TAG, "count : " + count);
            if (count <= 6) {
                int r_id = getResources().getIdentifier("hang_" + count, "drawable", getActivity().getPackageName());
                imagHang.setImageDrawable(getResources().getDrawable(r_id));
            }
        }
        editAnswer.setText("");
    }
    private String[] getRandomWordAndHint(String level) {
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery("SELECT word, hint FROM Words WHERE level=? ORDER BY RANDOM() LIMIT 1", new String[]{level});
            String[] wordAndHint = new String[2];

            if (cursor.moveToFirst()) {
                int wordColumnIndex = cursor.getColumnIndex("word");
                int hintColumnIndex = cursor.getColumnIndex("hint");

                if (wordColumnIndex != -1) {
                    wordAndHint[0] = cursor.getString(wordColumnIndex);
                }

                if (hintColumnIndex != -1) {
                    wordAndHint[1] = cursor.getString(hintColumnIndex);
                }
            }

            cursor.close();
            db.close();
            return wordAndHint;
        }



    private List<Question> getWordsFromDatabase() {
        List<Question> words = new ArrayList<>();
        DatabaseHelper dbHelper = new DatabaseHelper(getActivity());
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String level = "BEGINNER"; // Or "INTERMEDIATE" or "ADVANCE", depending on the desired difficulty
        wordList = dbHelper.loadWordsByLevel(level);

        String[] columns = new String[]{"hint", "word"};
        String selection = "level=?";
        String[] selectionArgs = new String[]{String.valueOf(GameActivity.level)};

        Cursor cursor = db.query("Words", columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int wordIndex = cursor.getColumnIndex("hint");
                int hintIndex = cursor.getColumnIndex("word");

                // Check if both indices are valid
                if (wordIndex >= 0 && hintIndex >= 0) {
                    String hint = cursor.getString(hintIndex);
                    String word = cursor.getString(wordIndex);
                    words.add(new Question(hint, word));
                }
            }
            cursor.close();
        }
        db.close();
        dbHelper.close();
        return words;
    }


    public Question pickGoodStarterWord() {
        Random random = new Random();
        String level = "BEGINNER"; // Or "INTERMEDIATE" or "ADVANCE", depending on the desired difficulty
        wordList = dbHelper.loadWordsByLevel(level);

        // No need for an infinite loop, just select a random word from the list
        int index = random.nextInt(wordList.size());
        return wordList.get(index);
    }
}

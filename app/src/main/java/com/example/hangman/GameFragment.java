package com.example.hangman;


import android.content.Context;
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
import java.util.Random;

public class GameFragment  extends Fragment {
    private final static String TAG = "MainActivity";
    private TextView txtWord;
    private TextView high;
    private TextView txtScore,txtLevel;
    private TextView txtQuestion;
    private Button play,reset,solve;
    public String result = " ";
    public EditText editAnswer ;
    private EditText letter;
    private ImageView imagHang;
    public Question currentWord;
    private int count=0;
    private ArrayList<Question> wordList  = new ArrayList();

    boolean isStart = false;
    View view = null;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.fragment_game, container, false);
        initView();
        return view;
    }

    private void initView() {
        addWord();
        editAnswer =  view.findViewById(R.id.letter);
        txtWord =  view.findViewById(R.id.word);
        imagHang =  view.findViewById(R.id.hang);
        play =  view.findViewById(R.id.play);
        reset =  view.findViewById(R.id.reset);
        solve =  view.findViewById(R.id.solve);
        editAnswer.setVisibility(View.GONE);
        reset.setVisibility(View.GONE);
        solve.setVisibility(View.GONE);
        play.setVisibility(View.VISIBLE);
        txtQuestion=  view.findViewById(R.id.txtQuestion);
        editAnswer.setRawInputType(InputType.TYPE_CLASS_TEXT);
        editAnswer.setVisibility(View.GONE);
        editAnswer.setImeOptions(EditorInfo.IME_ACTION_GO);
        txtScore = view.findViewById(R.id.score);
        txtLevel = view.findViewById(R.id.high);
        GameActivity.score = Integer.parseInt(SaveData.loadData(getActivity(),"score"));
        GameActivity.level = Integer.parseInt(SaveData.loadData(getActivity(),"level"));
        txtScore.setText(""+GameActivity.score);
        txtLevel.setText(""+GameActivity.level);
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
                if (editable.length()!=0)
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
        for (int i = 0; i < currentWord.answer.length(); i += 1) {
            result += "_ ";
        }
        txtWord.setText(result);
    }
    private void checkLetter() {
        if (currentWord==null)return;
        int i=0,flag=0,k=0;
        String letter = editAnswer.getText().toString().trim().toLowerCase();
        currentWord.answer = currentWord.answer.toString().trim().toLowerCase();
        Log.i(TAG, "checkLetter : letter "+letter);
        for(i=0;i<currentWord.answer.length();++i){
            Log.i(TAG, "checkLetter : currentWord.answer.indexOf(letter,i) "+currentWord.answer.indexOf(letter,i));
            if (currentWord.answer.indexOf(letter,i)!=-1) {
                k = currentWord.answer.indexOf(letter,i);
                result = result.substring(0, 2 * k) + letter+" "+ result.substring(2 * k + 2);
                Log.i(TAG, "checkLetter : result.substring(2 * k + 2)  "+result.substring(2 * k + 2));
                Log.i(TAG, "checkLetter : result.substring(0, 2 * k)  "+result.substring(0, 2 * k));
                Log.i(TAG, "checkLetter :result "+result);
                txtWord.setText(result);
                i=k;
                flag=1;
                if(result.indexOf("_")==-1) {
                    isStart = false;
                    txtWord.setText("You won !!");
                    GameActivity.score+=1;
                    GameActivity.level=GameActivity.score/2;
                    SaveData.saveData(getActivity(),"score",""+GameActivity.score);
                    SaveData.saveData(getActivity(),"level",""+GameActivity.level);
                    txtScore.setText(""+GameActivity.score);
                    txtLevel.setText(""+GameActivity.level);
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
        if(flag==0) {
            count = count + 1;
            if (count >= 6) {
                isStart = false;
                txtWord.setText("You lost !!");
                if (GameActivity.score > 0)
                    GameActivity.score -=1;
                SaveData.saveData(getActivity(),"score",""+GameActivity.score);
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
            if (count <= 6){
                int r_id = getResources().getIdentifier("hang_" + count, "drawable", getActivity().getPackageName());
                imagHang.setImageDrawable(getResources().getDrawable(r_id));
            }
        }
        editAnswer.setText("");
    }

    public void addWord() {
        ArrayList<Question> wordList  = new ArrayList();
        wordList.add(new Question("animal ? ","lion"));
        wordList.add(new Question("fruit ? ","Apple"));
        wordList.add(new Question("Country in Africa ? ","Egypt"));
        wordList.add(new Question("Country in Asia ? ","Saudi"));
        this.wordList = wordList;
    }
    public Question pickGoodStarterWord() {
        Random random = new Random();
        int index = random.nextInt(wordList.size());
        Question t=wordList.get(index);
        if(t.answer.length()<=6)
            return wordList.get(index);
        else return pickGoodStarterWord();
    }
}

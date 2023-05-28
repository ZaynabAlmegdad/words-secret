package com.example.hangman;



public class Question {
    public String question = "";
    public String answer = "";
    public String hint = "";



        // Getter and setter methods for hint (if required)
    public Question(String question, String answer, String hint) {
            this.question = question;
            this.answer = answer;
            this.hint = hint;
        }

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getHint() {
        return hint;
    }
    public String setHint() {
        return hint;
    }
}


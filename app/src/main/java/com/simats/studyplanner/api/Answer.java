package com.simats.studyplanner.api;

public class Answer {
    private int question_id;
    private String selected_option;

    public Answer(int question_id, String selected_option) {
        this.question_id = question_id;
        this.selected_option = selected_option;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public String getSelected_option() {
        return selected_option;
    }

    public void setSelected_option(String selected_option) {
        this.selected_option = selected_option;
    }

    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
    }
}

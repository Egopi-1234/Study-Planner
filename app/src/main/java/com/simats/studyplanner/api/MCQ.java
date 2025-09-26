package com.simats.studyplanner.api;

import com.google.gson.annotations.SerializedName;

public class MCQ {

    @SerializedName("question_id")
    private int questionId;

    @SerializedName("course_name")
    private String courseName;

    @SerializedName("question")
    private String question;

    @SerializedName("option_a")
    private String optionA;

    @SerializedName("option_b")
    private String optionB;

    @SerializedName("option_c")
    private String optionC;

    @SerializedName("option_d")
    private String optionD;

    @SerializedName("correct_option")
    private String correctOption;

    // Getters
    public int getQuestionId() { return questionId; }
    public String getCourseName() { return courseName; }
    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getCorrectOption() { return correctOption; }
}

package com.simats.studyplanner;

public class MCQResult {
    private String question;
    private String optionA, optionB, optionC, optionD;
    private String correctAnswer;
    private String selectedAnswer;

    public MCQResult(String question, String optionA, String optionB, String optionC, String optionD,
                     String correctAnswer, String selectedAnswer) {
        this.question = question;
        this.optionA = optionA;
        this.optionB = optionB;
        this.optionC = optionC;
        this.optionD = optionD;
        this.correctAnswer = correctAnswer;
        this.selectedAnswer = selectedAnswer;
    }

    // Getters
    public String getQuestion() { return question; }
    public String getOptionA() { return optionA; }
    public String getOptionB() { return optionB; }
    public String getOptionC() { return optionC; }
    public String getOptionD() { return optionD; }
    public String getCorrectAnswer() { return correctAnswer; }
    public String getSelectedAnswer() { return selectedAnswer; }
}

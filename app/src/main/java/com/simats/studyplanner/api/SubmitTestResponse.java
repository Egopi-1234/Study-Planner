package com.simats.studyplanner.api;

/**
 * Response expected from server:
 * {
 *   "success": true,
 *   "attempt_id": 6,
 *   "score": 7,
 *   "total_questions": 10
 * }
 */
public class SubmitTestResponse {
    private boolean success;
    private int attempt_id;
    private int score;
    private int total_questions;

    public SubmitTestResponse() { }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public int getAttemptId() {
        return attempt_id;
    }

    public int getScore() {
        return score;
    }

    public int getTotalQuestions() {
        return total_questions;
    }

    // Setters
    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setAttempt_id(int attempt_id) {
        this.attempt_id = attempt_id;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setTotal_questions(int total_questions) {
        this.total_questions = total_questions;
    }
}

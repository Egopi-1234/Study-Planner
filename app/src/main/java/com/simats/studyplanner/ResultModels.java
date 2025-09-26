package com.simats.studyplanner;

import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class ResultModels {

    public static class ResultResponse {
        @SerializedName("success")
        public boolean success;

        @SerializedName("attempt_id")
        public int attemptId;

        @SerializedName("score")
        public int score;

        @SerializedName("total_questions")
        public int totalQuestions;

        @SerializedName("questions")
        public List<QuestionItem> questions;

        // Convert QuestionItem list to MCQResult list for adapter
        public List<MCQResult> getData() {
            List<MCQResult> mcqList = new ArrayList<>();
            if (questions != null) {
                for (QuestionItem q : questions) {
                    mcqList.add(new MCQResult(
                            q.question,
                            q.options.A, q.options.B, q.options.C, q.options.D,
                            q.correctOption,
                            q.selectedOption
                    ));
                }
            }
            return mcqList;
        }
    }

    public static class QuestionItem {
        @SerializedName("question_id")
        public int questionId;

        @SerializedName("question")
        public String question;

        @SerializedName("options")
        public Options options;

        @SerializedName("selected_option")
        public String selectedOption;

        @SerializedName("correct_option")
        public String correctOption;

        @SerializedName("is_correct")
        public int isCorrect;
    }

    public static class Options {
        @SerializedName("A")
        public String A;

        @SerializedName("B")
        public String B;

        @SerializedName("C")
        public String C;

        @SerializedName("D")
        public String D;
    }
}

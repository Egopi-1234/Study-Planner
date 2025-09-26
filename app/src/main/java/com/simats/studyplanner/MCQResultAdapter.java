package com.simats.studyplanner;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MCQResultAdapter extends RecyclerView.Adapter<MCQResultAdapter.ViewHolder> {

    private List<MCQResult> mcqList;

    public MCQResultAdapter(List<MCQResult> mcqList) {
        this.mcqList = mcqList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_mcq_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MCQResult mcq = mcqList.get(position);

        holder.tvQuestion.setText(mcq.getQuestion());
        holder.rbOptionA.setText("A) " + mcq.getOptionA());
        holder.rbOptionB.setText("B) " + mcq.getOptionB());
        holder.rbOptionC.setText("C) " + mcq.getOptionC());
        holder.rbOptionD.setText("D) " + mcq.getOptionD());

        // Reset colors to avoid RecyclerView bleed
        resetColors(holder);

        // Select the correct RadioButton for user's answer
        holder.radioGroup.clearCheck();
        if (mcq.getSelectedAnswer() != null && !mcq.getSelectedAnswer().isEmpty()) {
            char selected = mcq.getSelectedAnswer().charAt(0);
            switch (selected) {
                case 'A': holder.rbOptionA.setChecked(true); break;
                case 'B': holder.rbOptionB.setChecked(true); break;
                case 'C': holder.rbOptionC.setChecked(true); break;
                case 'D': holder.rbOptionD.setChecked(true); break;
            }
        }

        // Handle correct/wrong answer display
        if (mcq.getSelectedAnswer() != null && !mcq.getSelectedAnswer().isEmpty()) {
            char selected = mcq.getSelectedAnswer().charAt(0);
            char correct = mcq.getCorrectAnswer().charAt(0);

            if (selected == correct) {
                // If correct: show success message in green
                holder.tvCorrectAnswer.setText("Yes, the answer is correct.");
                holder.tvCorrectAnswer.setTextColor(Color.parseColor("#4CAF50")); // Green
                holder.tvSelectedAnswer.setText("");
                highlightOption(holder, correct, Color.parseColor("#4CAF50")); // Green
            } else {
                // If wrong: show incorrect message in red + correct answer in green
                holder.tvCorrectAnswer.setText("No, the answer is incorrect");
                holder.tvCorrectAnswer.setTextColor(Color.parseColor("#F44336")); // Red

                holder.tvSelectedAnswer.setText("Correct Answer: " + mcq.getCorrectAnswer());
                holder.tvSelectedAnswer.setTextColor(Color.parseColor("#4CAF50")); // Green

                highlightOption(holder, selected, Color.parseColor("#F44336")); // Red wrong

                highlightOption(holder, correct, Color.parseColor("#4CAF50")); // Green
            }
        } else {
            // If no answer selected
            holder.tvCorrectAnswer.setText("Correct Answer: " + mcq.getCorrectAnswer());
            holder.tvCorrectAnswer.setTextColor(Color.BLACK);
            holder.tvSelectedAnswer.setText("Your Answer: Not Answered");
            holder.tvSelectedAnswer.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return mcqList.size();
    }

    private void resetColors(ViewHolder holder) {
        holder.rbOptionA.setTextColor(Color.BLACK);
        holder.rbOptionB.setTextColor(Color.BLACK);
        holder.rbOptionC.setTextColor(Color.BLACK);
        holder.rbOptionD.setTextColor(Color.BLACK);
        holder.tvCorrectAnswer.setTextColor(Color.BLACK);
        holder.tvSelectedAnswer.setTextColor(Color.BLACK);
    }

    private void highlightOption(ViewHolder holder, char option, int color) {
        switch (option) {
            case 'A': holder.rbOptionA.setTextColor(color); break;
            case 'B': holder.rbOptionB.setTextColor(color); break;
            case 'C': holder.rbOptionC.setTextColor(color); break;
            case 'D': holder.rbOptionD.setTextColor(color); break;
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvQuestion, tvCorrectAnswer, tvSelectedAnswer;
        RadioGroup radioGroup;
        RadioButton rbOptionA, rbOptionB, rbOptionC, rbOptionD;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            tvCorrectAnswer = itemView.findViewById(R.id.tvCorrectAnswer);
            tvSelectedAnswer = itemView.findViewById(R.id.tvSelectedAnswer);
            radioGroup = itemView.findViewById(R.id.radioGroupOptions);
            rbOptionA = itemView.findViewById(R.id.rbOptionA);
            rbOptionB = itemView.findViewById(R.id.rbOptionB);
            rbOptionC = itemView.findViewById(R.id.rbOptionC);
            rbOptionD = itemView.findViewById(R.id.rbOptionD);
        }
    }
}

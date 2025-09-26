package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class TasksFragment extends Fragment {

    private Button btnPythonDeveloper, btnDataAnalyst, btnMachineLearning;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        // Initialize buttons
        btnPythonDeveloper = view.findViewById(R.id.btnPythonDeveloper);
        btnDataAnalyst = view.findViewById(R.id.btnDataAnalyst);
        btnMachineLearning = view.findViewById(R.id.btnMachineLearning);

        // Button click listeners
        btnPythonDeveloper.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), role1Activity.class);
            startActivity(intent);
        });

        btnDataAnalyst.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), role2Activity.class);
            startActivity(intent);
        });

        btnMachineLearning.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), role3Activity.class);
            startActivity(intent);
        });

        return view;
    }
}

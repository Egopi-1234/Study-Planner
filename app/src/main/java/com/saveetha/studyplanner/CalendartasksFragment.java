package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendartasksFragment extends Fragment {

    private TextView selectedDateText;
    private RecyclerView taskRecyclerView;
    private FloatingActionButton fabAddTask;
    private List<CalendarTaskModel> taskList;
    private CalendarTaskAdapter adapter;

    public CalendartasksFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_calendartasks, container, false);

        // Bind views
        selectedDateText = view.findViewById(R.id.selectedDateText);
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        fabAddTask = view.findViewById(R.id.fabAddTask);

        // Set current date
        String currentDate = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(new Date());
        selectedDateText.setText(currentDate);

        // Set up sample task list
        taskList = new ArrayList<>();
        taskList.add(new CalendarTaskModel("Biology", "Prepare for exam", "10:00 AM"));
        taskList.add(new CalendarTaskModel("Blockchain", "Complete Assignment 2", "12:00 PM"));

        // Set up RecyclerView
        adapter = new CalendarTaskAdapter(taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.setAdapter(adapter);

        // FAB click to open task creation
        fabAddTask.setOnClickListener(v -> {
            Intent intent = new Intent(requireActivity(), CreatetaskActivity.class);
            startActivity(intent);
        });

        return view;
    }
}

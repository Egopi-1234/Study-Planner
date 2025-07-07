package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CalendartasksFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FloatingActionButton fabAddTask;


        View view = inflater.inflate(R.layout.fragment_calendartasks, container, false);



        fabAddTask = view.findViewById(R.id.fabAddTask);

        fabAddTask.setOnClickListener(v -> {
            startActivity(new Intent(requireActivity(), CreatetaskActivity.class));});

        return view;



    }
}
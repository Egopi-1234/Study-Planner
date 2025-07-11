package com.saveetha.studyplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class TasksFragment extends Fragment {

    RecyclerView taskRecycler;
    MyTaskAdapter adapter;

    List<Task> list;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        taskRecycler = view.findViewById(R.id.taskRecyclerView);


        list = new ArrayList<>();
        list.add(new Task("Pom assignment","High"));
        list.add(new Task("Pom assignment","Medium"));
        list.add(new Task("Pom assignment","Low"));

        taskRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter =  new MyTaskAdapter(list);
        taskRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        return view;
    }
}
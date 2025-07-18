package com.saveetha.studyplanner;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    Button addtask;
    ImageView notificationbtn;

    Button viewfulltask;

    RecyclerView taskRecycler;
    MyTaskAdapter adapter;

    List<Task> list;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View v = inflater.inflate(R.layout.fragment_home, container, false);


        taskRecycler = v.findViewById(R.id.recyclerView);


        list = new ArrayList<>();
//        list.add(new Task("Pom assignment", "High"));
//        list.add(new Task("Pom assignment", "Medium"));
//        list.add(new Task("Pom assignment", "Low"));

        taskRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyTaskAdapter(requireContext(),list);
        taskRecycler.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        RecyclerView recyclerView = v.findViewById(R.id.recyclerView);

        addtask = v.findViewById(R.id.addtask);

        addtask.setOnClickListener(v1 -> {
            startActivity(new Intent(requireActivity(), CreatetaskActivity.class));


        });

        viewfulltask = v.findViewById(R.id.viewfulltask);

        viewfulltask.setOnClickListener(v1 -> {
            HomepageActivity homepageActivity = (HomepageActivity) getActivity();
            if (homepageActivity != null) {
                homepageActivity.navigateToIndex(1);
            }
        });

        notificationbtn = v.findViewById(R.id.notificationbtn);

        notificationbtn.setOnClickListener(v1 -> {
            getParentFragmentManager().beginTransaction().replace(R.id.frameContainer, new NotificationsFragment()).addToBackStack("").commit();
        });


//        List<TaskModel> taskList = new ArrayList<>();
//        taskList.add(new TaskModel("POM Assignment", "6.00 pm", "Apr 29", "High"));
//        taskList.add(new TaskModel("math Assignment", "7.00 pm", "Apr 29", "Medium"));
//        taskList.add(new TaskModel("Read Physics Assignment", "8.00 pm", "Apr 29", "High"));
//
//        TaskAdapter adapter = new TaskAdapter(requireContext(), taskList);
//        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
//        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return v;
    }

}
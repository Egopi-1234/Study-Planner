package com.saveetha.studyplanner;

import android.annotation.SuppressLint;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    RecyclerView recyclerView;
    NotificationAdapter adapter;

    ImageView backarrowbtn;
    List<NotificationModel> notificationList;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_notifications, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewNotifications);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        notificationList = new ArrayList<>();
        notificationList.add(new NotificationModel("Study Planner", "1 day Due Date", "Assignment 2 - Block Chain", ""));
        notificationList.add(new NotificationModel("Study Planner", "12.00 PM - 1.00 PM", "Assignment 2 - Block Chain", "Due Apr 29"));
        notificationList.add(new NotificationModel("Study Planner", "", "Physics Notes PDF Read Unit-1", "Due Apr 30"));

        adapter = new NotificationAdapter(notificationList);
        recyclerView.setAdapter(adapter);

        backarrowbtn = view.findViewById(R.id.backarrbt1);

        backarrowbtn.setOnClickListener(v -> {
            requireActivity().onBackPressed();
        });

        return view;
    }
}

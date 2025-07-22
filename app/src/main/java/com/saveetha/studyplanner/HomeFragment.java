package com.saveetha.studyplanner;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.CalendarTaskResponse;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    Button addtask;
    ImageView notificationbtn;
    Button viewfulltask;
    RecyclerView taskRecycler;
    CalendarTaskAdapter adapter;
    List<CalendarTaskModel> list;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        addtask = v.findViewById(R.id.addtask);
        viewfulltask = v.findViewById(R.id.viewfulltask);
        notificationbtn = v.findViewById(R.id.notificationbtn);
        taskRecycler = v.findViewById(R.id.recyclerView);

        list = new ArrayList<>();
        adapter = new CalendarTaskAdapter(list);
        taskRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecycler.setAdapter(adapter);

        loadTodayTasks();

        addtask.setOnClickListener(v1 -> {
            if (isAdded()) {
                startActivity(new Intent(getActivity(), CreatetaskActivity.class));
            }
        });

        viewfulltask.setOnClickListener(v1 -> {
            if (isAdded()) {
                HomepageActivity homepageActivity = (HomepageActivity) getActivity();
                if (homepageActivity != null) {
                    homepageActivity.navigateToIndex(1); // All Tasks tab
                }
            }
        });

        notificationbtn.setOnClickListener(v1 -> {
            if (isAdded()) {
                getParentFragmentManager().beginTransaction()
                        .replace(R.id.frameContainer, new NotificationsFragment())
                        .addToBackStack("")
                        .commit();
            }
        });

        return v;
    }

    private void loadTodayTasks() {
        if (!isAdded() || getContext() == null) return;

        Context context = getContext();
        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        if (userId == -1) {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        String todayDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), todayDate);

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<CalendarTaskResponse> call = apiService.getTasksByDate(userIdBody, dateBody);

        call.enqueue(new Callback<CalendarTaskResponse>() {
            @Override
            public void onResponse(@NonNull Call<CalendarTaskResponse> call, @NonNull Response<CalendarTaskResponse> response) {
                if (!isAdded() || getContext() == null) return;

                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    List<CalendarTaskModel> tasks = response.body().getData();
                    list.clear();
                    list.addAll(tasks);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "No tasks for today", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<CalendarTaskResponse> call, @NonNull Throwable t) {
                if (!isAdded() || getContext() == null) return;

                Toast.makeText(getContext(), "API Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

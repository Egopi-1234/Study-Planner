package com.saveetha.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.CalendarTaskResponse;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CalendartasksFragment extends Fragment {

    private TextView selectedDateText;
    private RecyclerView taskRecyclerView;
    private CalendarView calendarView;
    private FloatingActionButton fabAddTask;

    private List<CalendarTaskModel> taskList = new ArrayList<>();
    private CalendarTaskAdapter adapter;

    private String selectedApiDate = null; // ⬅️ Store the selected date in yyyy-MM-dd format

    public CalendartasksFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendartasks, container, false);

        selectedDateText = view.findViewById(R.id.selectedDateText);
        calendarView = view.findViewById(R.id.calendarView);
        taskRecyclerView = view.findViewById(R.id.taskRecyclerView);
        fabAddTask = view.findViewById(R.id.fabAddTask);

        adapter = new CalendarTaskAdapter(taskList);
        taskRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        taskRecyclerView.setAdapter(adapter);

        // Load today's date
        long currentMillis = System.currentTimeMillis();
        updateDateUI(currentMillis);

        calendarView.setOnDateChangeListener((view1, year, month, dayOfMonth) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, dayOfMonth);
            long selectedMillis = calendar.getTimeInMillis();
            updateDateUI(selectedMillis);
        });

        fabAddTask.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), CreatetaskActivity.class));
        });

        return view;
    }

    private void updateDateUI(long millis) {
        selectedApiDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(millis)); // ⬅️ Store selected date
        String displayDate = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(new Date(millis));

        selectedDateText.setText(displayDate);
        fetchTasks(selectedApiDate);
    }

    private int getLoggedInUserId() {
        SharedPreferences sharedPreferences = requireContext().getSharedPreferences("UserSession", getContext().MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1); // -1 if not found
    }

    private void fetchTasks(String date) {
        int userId = getLoggedInUserId();
        if (userId == -1) {
            Toast.makeText(getContext(), "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), date);

        Call<CalendarTaskResponse> call = apiService.getTasksByDate(userIdBody, dateBody);

        call.enqueue(new Callback<CalendarTaskResponse>() {
            @Override
            public void onResponse(Call<CalendarTaskResponse> call, Response<CalendarTaskResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    taskList.clear();
                    taskList.addAll(response.body().getData());
                    adapter.notifyDataSetChanged();
                } else {
                    taskList.clear();
                    adapter.notifyDataSetChanged();

                    String errorMessage = "No tasks available for this date";
                    if (response.errorBody() != null) {
                        try {
                            errorMessage += ": " + response.errorBody().string();
                        } catch (IOException e) {
                            errorMessage += " (unable to read error body)";
                        }
                    }

                    Toast.makeText(getContext(), errorMessage, Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<CalendarTaskResponse> call, Throwable t) {
                Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedApiDate != null) {
            fetchTasks(selectedApiDate); // ⬅️ Re-fetch tasks for the currently selected date
        }
    }
}

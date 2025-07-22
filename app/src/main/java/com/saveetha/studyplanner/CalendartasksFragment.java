package com.saveetha.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.CalendarTaskResponse;

import java.io.IOException;
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

    private String selectedApiDate = null;

    private Context safeContext;

    public CalendartasksFragment() {}

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        safeContext = context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        safeContext = null;
    }

    @Nullable
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
            updateDateUI(calendar.getTimeInMillis());
        });

        fabAddTask.setOnClickListener(v -> {
            if (safeContext != null) {
                startActivity(new Intent(safeContext, CreatetaskActivity.class));
            }
        });

        return view;
    }

    private void updateDateUI(long millis) {
        selectedApiDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date(millis));
        String displayDate = new SimpleDateFormat("EEEE, MMMM d", Locale.getDefault()).format(new Date(millis));

        selectedDateText.setText(displayDate);
        fetchTasks(selectedApiDate);
    }

    private int getLoggedInUserId() {
        if (safeContext == null) return -1;

        SharedPreferences sharedPreferences = safeContext.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("user_id", -1);
    }

    private void fetchTasks(String date) {
        int userId = getLoggedInUserId();
        if (userId == -1) {
            showToast("User not logged in");
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userId));
        RequestBody dateBody = RequestBody.create(MediaType.parse("text/plain"), date);

        Call<CalendarTaskResponse> call = apiService.getTasksByDate(userIdBody, dateBody);

        call.enqueue(new Callback<CalendarTaskResponse>() {
            @Override
            public void onResponse(@NonNull Call<CalendarTaskResponse> call, @NonNull Response<CalendarTaskResponse> response) {
                if (!isAdded()) return; // Prevent update if fragment not attached

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

                    showToast(errorMessage);
                }
            }

            @Override
            public void onFailure(@NonNull Call<CalendarTaskResponse> call, @NonNull Throwable t) {
                if (!isAdded()) return;
                showToast("Error: " + t.getMessage());
            }
        });
    }

    private void showToast(String message) {
        if (safeContext != null) {
            Toast.makeText(safeContext, message, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (selectedApiDate != null) {
            fetchTasks(selectedApiDate);
        }
    }
}

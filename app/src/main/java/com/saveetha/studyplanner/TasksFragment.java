package com.saveetha.studyplanner;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.TaskResponse;

import java.util.ArrayList;
import java.util.List;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TasksFragment extends Fragment {

    private RecyclerView recyclerTasks;
    private MyTaskAdapter adapter;
    private List<Task> fullTaskList = new ArrayList<>();
    private List<Task> filteredTaskList = new ArrayList<>();

    private ChipGroup chipGroupFilters;
    private Chip chipAll, chipPending, chipCompleted, chipPriority;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tasks, container, false);

        recyclerTasks = view.findViewById(R.id.recycler_tasks);
        chipGroupFilters = view.findViewById(R.id.chip_group_filters);

        chipAll = view.findViewById(R.id.chip_all);
        chipPending = view.findViewById(R.id.chip_pending);
        chipCompleted = view.findViewById(R.id.chip_completed);
        chipPriority = view.findViewById(R.id.chip_priority);

        recyclerTasks.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MyTaskAdapter(requireContext(), filteredTaskList);
        recyclerTasks.setAdapter(adapter);

        // Fetch tasks initially
        fetchTasks();

        // Chip selection listener
        chipGroupFilters.setOnCheckedChangeListener((group, checkedId) -> {
            updateChipColors(checkedId);

            if (checkedId == R.id.chip_all) {
                showAllTasks();
            } else if (checkedId == R.id.chip_pending) {
                filterTasksByStatus("Pending");
            } else if (checkedId == R.id.chip_completed) {
                filterTasksByStatus("Complete");
            } else if (checkedId == R.id.chip_priority) {
                sortTasksByPriority();
            }
        });

        // Set "All" selected by default
        chipAll.setChecked(true);
        updateChipColors(chipAll.getId());

        return view;
    }

    private void updateChipColors(int selectedChipId) {
        if (!isAdded()) return;

        int selectedText = getResources().getColor(R.color.chip_text_selected);
        int unselectedText = Color.BLACK;

        for (int i = 0; i < chipGroupFilters.getChildCount(); i++) {
            Chip chip = (Chip) chipGroupFilters.getChildAt(i);
            if (chip.getId() == selectedChipId) {
                chip.setChipBackgroundColorResource(R.color.chip_selected_color);
                chip.setTextColor(selectedText);
            } else {
                chip.setChipBackgroundColorResource(R.color.chip_unselected_color);
                chip.setTextColor(unselectedText);
            }
        }
    }

    private void fetchTasks() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Context context = getContext();
        if (context == null) return;

        SharedPreferences sharedPreferences = context.getSharedPreferences("UserSession", MODE_PRIVATE);
        int userId = sharedPreferences.getInt("user_id", -1);

        RequestBody userIdBody = RequestBody.create(okhttp3.MultipartBody.FORM, String.valueOf(userId));

        Call<TaskResponse> call = apiService.getTasks(userIdBody);
        call.enqueue(new Callback<TaskResponse>() {
            @Override
            public void onResponse(Call<TaskResponse> call, Response<TaskResponse> response) {
                if (!isAdded()) return;

                if (response.isSuccessful() && response.body() != null && response.body().isStatus()) {
                    fullTaskList.clear();
                    fullTaskList.addAll(response.body().getData());
                    showAllTasks();
                } else {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "No tasks found", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<TaskResponse> call, Throwable t) {
                if (!isAdded()) return;

                if (getContext() != null) {
                    Toast.makeText(getContext(), "Failed to load tasks: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showAllTasks() {
        filteredTaskList.clear();
        filteredTaskList.addAll(fullTaskList);
        adapter.notifyDataSetChanged();
    }

    private void filterTasksByStatus(String status) {
        filteredTaskList.clear();
        for (Task task : fullTaskList) {
            if (task.getStatus() != null && task.getStatus().equalsIgnoreCase(status)) {
                filteredTaskList.add(task);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void sortTasksByPriority() {
        filteredTaskList.clear();
        filteredTaskList.addAll(fullTaskList);

        filteredTaskList.sort((task1, task2) ->
                getPriorityValue(task1.getPriority()) - getPriorityValue(task2.getPriority()));

        adapter.notifyDataSetChanged();
    }

    private int getPriorityValue(String priority) {
        if (priority == null) return 4;
        switch (priority.toLowerCase()) {
            case "high":
                return 1;
            case "medium":
                return 2;
            case "low":
                return 3;
            default:
                return 4;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchTasks();
    }
}

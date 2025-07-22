package com.saveetha.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.MaterialResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemStudyMaterialFragment extends Fragment {

    private RecyclerView recyclerView;
    private StudyMaterialAdapter adapter;
    private Button btnAddMaterial;

    private Context safeContext;

    public ItemStudyMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        safeContext = context; // Save context for safe usage
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_study_material, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMaterials);
        recyclerView.setLayoutManager(new LinearLayoutManager(safeContext));
        btnAddMaterial = view.findViewById(R.id.btnAddMaterial);

        btnAddMaterial.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(),addstudymaterialActivity.class));
        });

        fetchStudyMaterials();

        return view;
    }

    private void fetchStudyMaterials() {
        if (safeContext == null || !isAdded()) return;

        SharedPreferences prefs = safeContext.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);
        if (userId == -1) {
            Toast.makeText(safeContext, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MaterialResponse> call = apiService.getStudyMaterials(userId);

        call.enqueue(new Callback<MaterialResponse>() {
            @Override
            public void onResponse(@NonNull Call<MaterialResponse> call, @NonNull Response<MaterialResponse> response) {
                if (!isAdded() || safeContext == null) return;

                if (response.isSuccessful() && response.body() != null) {
                    List<StudyMaterial> materials = response.body().getData();
                    adapter = new StudyMaterialAdapter(safeContext, materials);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(safeContext, "No materials found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<MaterialResponse> call, @NonNull Throwable t) {
                if (isAdded() && safeContext != null) {
                    Toast.makeText(safeContext, "Failed to fetch materials: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        safeContext = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchStudyMaterials();
    }
}

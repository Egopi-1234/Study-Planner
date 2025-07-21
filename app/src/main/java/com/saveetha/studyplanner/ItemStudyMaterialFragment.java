package com.saveetha.studyplanner;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    public ItemStudyMaterialFragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_study_material, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewMaterials);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        fetchStudyMaterials();

        return view;
    }

    private void fetchStudyMaterials() {
        Context context = getContext();
        if (context == null) return; // Don't continue if fragment isn't attached

        SharedPreferences prefs = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE);
        int userId = prefs.getInt("user_id", -1);
        if (userId == -1) {
            Toast.makeText(context, "User not logged in", Toast.LENGTH_SHORT).show();
            return;
        }

        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        Call<MaterialResponse> call = apiService.getStudyMaterials(userId); // Make sure this API is defined

        call.enqueue(new Callback<MaterialResponse>() {
            @Override
            public void onResponse(Call<MaterialResponse> call, Response<MaterialResponse> response) {
                Context context = getContext();
                if (context == null) return;

                if (response.isSuccessful() && response.body() != null) {
                    List<StudyMaterial> materials = response.body().getData();
                    adapter = new StudyMaterialAdapter(context, materials);
                    recyclerView.setAdapter(adapter);
                } else {
                    Toast.makeText(context, "No materials found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MaterialResponse> call, Throwable t) {
                Context context = getContext();
                if (context != null) {
                    Toast.makeText(context, "Failed to fetch materials: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

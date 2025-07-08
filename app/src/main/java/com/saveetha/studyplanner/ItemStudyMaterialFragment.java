package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.saveetha.studyplanner.adapters.StudyMaterialAdapter;
import com.saveetha.studyplanner.models.StudyMaterial;

import java.util.ArrayList;
import java.util.List;

public class ItemStudyMaterialFragment extends Fragment {

    private Button btnAddMaterial;
    private RecyclerView recyclerViewMaterials;
    private StudyMaterialAdapter adapter;
    private List<StudyMaterial> materialList;

    public ItemStudyMaterialFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout
        View v = inflater.inflate(R.layout.fragment_item_study_material, container, false);

        // Initialize views
        btnAddMaterial = v.findViewById(R.id.btnAddMaterial);
        recyclerViewMaterials = v.findViewById(R.id.recyclerViewMaterials);

        // Set up RecyclerView
        materialList = new ArrayList<>();
        materialList.add(new StudyMaterial("Unit 1-Physics Notes", "Apr 30, 6.00 PM"));
        materialList.add(new StudyMaterial("Math Unit 2 Formulas Pdf", "May 1, 7.00 PM"));
        materialList.add(new StudyMaterial("Chemistry unit 5 Formulas Pdf", "May 2, 5.00 PM"));

        adapter = new StudyMaterialAdapter(getContext(), materialList);
        recyclerViewMaterials.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewMaterials.setAdapter(adapter);

        // Add Materials Button Click
        btnAddMaterial.setOnClickListener(view -> {
            Intent intent = new Intent(requireActivity(), addstudymaterialActivity.class);
            startActivity(intent);
        });

        return v;
    }
}

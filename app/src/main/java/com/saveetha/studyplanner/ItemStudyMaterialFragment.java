package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ItemStudyMaterialFragment extends Fragment {

    Button btnAddMaterial;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout and assign it to a View variable
        View v = inflater.inflate(R.layout.fragment_item_study_material, container, false);

        // Initialize the button
        btnAddMaterial = v.findViewById(R.id.btnAddMaterial);

        // Set the click listener
        btnAddMaterial.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), addstudymaterialActivity.class));
        });

        // Return the view after setting everything up
        return v;
    }
}

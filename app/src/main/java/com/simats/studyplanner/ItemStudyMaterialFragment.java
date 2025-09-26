package com.simats.studyplanner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ItemStudyMaterialFragment extends Fragment {

    private LinearLayout materialPythonCard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_study_material, container, false);

        materialPythonCard = rootView.findViewById(R.id.materialPythonCard);

        materialPythonCard.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ViewStudyMaterialActivity.class);
            // Pass sample data (replace with dynamic later if needed)
            intent.putExtra("title", "python - course");
            intent.putExtra("date", "2025-08-05 10:41:00");
            intent.putExtra("status", "completed");
            startActivity(intent);
        });

        return rootView;
    }
}

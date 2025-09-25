package com.saveetha.studyplanner;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class HomeFragment extends Fragment {

    Button btnEnroll;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        btnEnroll = v.findViewById(R.id.btnEnroll);

        // Enroll button click → open Course Details page
        btnEnroll.setOnClickListener(v12 -> {
            if (isAdded()) {
                Intent intent = new Intent(getActivity(), coursedetailspageActivity.class);
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateEnrollButton();
    }

    private void updateEnrollButton() {
        boolean isEnrolled = requireActivity()
                .getSharedPreferences("studyplanner", requireContext().MODE_PRIVATE)
                .getBoolean("isEnrolled", false);

        if (isEnrolled) {
            btnEnroll.setText("Enrolled");
            btnEnroll.setBackgroundColor(Color.parseColor("#4CAF50")); // Green
        } else {
            btnEnroll.setText("Enroll");
            btnEnroll.setBackgroundColor(Color.parseColor("#FF6200EE")); // Original color
        }
    }
}

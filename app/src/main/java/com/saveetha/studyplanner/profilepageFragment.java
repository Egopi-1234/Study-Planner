package com.saveetha.studyplanner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;  // Correct import for TextView

public class profilepageFragment extends Fragment {

    private ImageView conarrow1;
    private ImageView conarrow2;
    private ImageView conarrow3;
    private ImageView conarrow4;
    private TextView editprofile;  // Changed from ImageView to TextView

    public profilepageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilepage, container, false);

        // Initialize ImageViews
        conarrow1 = v.findViewById(R.id.conarrow8);
        conarrow2 = v.findViewById(R.id.conarrow2);
        conarrow3 = v.findViewById(R.id.conarrow3);
        conarrow4 = v.findViewById(R.id.conarrow4);

        // Initialize TextView (was ImageView before)
        editprofile = v.findViewById(R.id.editprofile);

        // Set click listeners
        conarrow1.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), ChangepasswordpageActivity.class));
        });

        conarrow2.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), settingspageActivity.class));
        });

        conarrow3.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), PrivacypolicyPageActivity.class));
        });

        conarrow4.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), TermsandconditionpageActivity.class));
        });

        editprofile.setOnClickListener(view -> {
            startActivity(new Intent(requireActivity(), EditprofiledetailspageActivity.class));
        });

        return v;
    }
}

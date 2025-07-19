package com.saveetha.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class profilepageFragment extends Fragment {

    private ImageView conarrow1, conarrow2, conarrow3, conarrow4;
    private TextView editprofile, nameLabel, emailLabel;
    private Button logout_button;

    public profilepageFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_profilepage, container, false);

        // Initialize views
        conarrow1 = v.findViewById(R.id.conarrow8);
        conarrow2 = v.findViewById(R.id.conarrow2);
        conarrow3 = v.findViewById(R.id.conarrow3);
        conarrow4 = v.findViewById(R.id.conarrow4);
        editprofile = v.findViewById(R.id.editprofile);
        logout_button = v.findViewById(R.id.logout_button);
        nameLabel = v.findViewById(R.id.name_label);
        emailLabel = v.findViewById(R.id.email_label);

        // Load user data from SharedPreferences (adjust name of prefs and keys accordingly)
        SharedPreferences prefs = requireActivity().getSharedPreferences("user_prefs", 0);
        String username = prefs.getString("username", "Username");   // default fallback
        String email = prefs.getString("email", "user@example.com"); // default fallback

        // Set user info in TextViews
        nameLabel.setText("Name: " + username);
        emailLabel.setText("Mail ID: " + email);

        // Set click listeners
        conarrow1.setOnClickListener(view -> startActivity(new Intent(requireActivity(), ChangepasswordpageActivity.class)));
        conarrow2.setOnClickListener(view -> startActivity(new Intent(requireActivity(), settingspageActivity.class)));
        conarrow3.setOnClickListener(view -> startActivity(new Intent(requireActivity(), PrivacypolicyPageActivity.class)));
        conarrow4.setOnClickListener(view -> startActivity(new Intent(requireActivity(), TermsandconditionpageActivity.class)));
        editprofile.setOnClickListener(view -> startActivity(new Intent(requireActivity(), EditprofiledetailspageActivity.class)));

        logout_button.setOnClickListener(view -> {
            // Clear user session if needed, then logout
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivity(new Intent(requireActivity(), LoginPage.class));
            requireActivity().finish();
        });

        return v;
    }
}

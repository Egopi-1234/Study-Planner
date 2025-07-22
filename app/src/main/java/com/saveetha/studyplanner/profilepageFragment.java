package com.saveetha.studyplanner;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

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

        // Check if fragment is attached before accessing context
        if (!isAdded()) {
            // Fragment not attached, return view without setup to avoid crashes
            return v;
        }

        // Safe to get context and activity now
        final var context = requireContext();
        final var activity = requireActivity();

        // Load user data from SharedPreferences
        SharedPreferences prefs = context.getSharedPreferences("user_prefs", 0);
        String username = prefs.getString("username", "Username");
        String email = prefs.getString("email", "user@example.com");

        // Set user info in TextViews
        nameLabel.setText("Name: " + username);
        emailLabel.setText("Mail ID: " + email);

        // Set click listeners for arrows and edit profile
        conarrow1.setOnClickListener(view ->
                activity.startActivity(new Intent(context, ChangepasswordpageActivity.class)));

        conarrow2.setOnClickListener(view ->
                activity.startActivity(new Intent(context, settingspageActivity.class)));

        conarrow3.setOnClickListener(view ->
                activity.startActivity(new Intent(context, PrivacypolicyPageActivity.class)));

        conarrow4.setOnClickListener(view ->
                activity.startActivity(new Intent(context, TermsandconditionpageActivity.class)));

        editprofile.setOnClickListener(view ->
                activity.startActivity(new Intent(context, EditprofiledetailspageActivity.class)));

        logout_button.setOnClickListener(view -> {
            // Clear user session and logout
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            activity.startActivity(new Intent(context, LoginPage.class));
            activity.finish();
        });

        return v;
    }
}

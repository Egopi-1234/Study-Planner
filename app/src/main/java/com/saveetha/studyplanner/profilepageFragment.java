package com.saveetha.studyplanner;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.saveetha.studyplanner.api.ApiClient;
import com.saveetha.studyplanner.api.ApiService;
import com.saveetha.studyplanner.api.ProfileResponse;

import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class profilepageFragment extends Fragment {

    private ImageView conarrow1, conarrow2, conarrow3, conarrow4, profileImageView;
    private TextView editprofile, nameLabel, emailLabel;
    private Button logout_button;
    private int userIdStr;

    public profilepageFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profilepage, container, false);

        conarrow1 = v.findViewById(R.id.conarrow8);
        conarrow2 = v.findViewById(R.id.conarrow2);
        conarrow3 = v.findViewById(R.id.conarrow3);
        conarrow4 = v.findViewById(R.id.conarrow4);
        editprofile = v.findViewById(R.id.editprofile);
        logout_button = v.findViewById(R.id.logout_button);
        nameLabel = v.findViewById(R.id.name_label);
        emailLabel = v.findViewById(R.id.email_label);
        profileImageView = v.findViewById(R.id.profile_image);

        Context context = getContext();
        if (context == null) return v;

        SharedPreferences prefs = context.getSharedPreferences("UserSession", MODE_PRIVATE);
        userIdStr = prefs.getInt("user_id", -1);

        fetchProfile(userIdStr);

        conarrow1.setOnClickListener(view ->
                startActivitySafe(new Intent(context, ChangepasswordpageActivity.class)));

        conarrow2.setOnClickListener(view ->
                startActivitySafe(new Intent(context, settingspageActivity.class)));

        conarrow3.setOnClickListener(view ->
                startActivitySafe(new Intent(context, PrivacypolicyPageActivity.class)));

        conarrow4.setOnClickListener(view ->
                startActivitySafe(new Intent(context, TermsandconditionpageActivity.class)));

        editprofile.setOnClickListener(view ->
                startActivitySafe(new Intent(context, EditprofiledetailspageActivity.class)));

        logout_button.setOnClickListener(view -> {
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            startActivitySafe(new Intent(context, LoginPage.class));
            requireActivity().finish();
        });

        return v;
    }

    private void startActivitySafe(Intent intent) {
        if (getActivity() != null && isAdded()) {
            startActivity(intent);
        }
    }

    private void fetchProfile(int userIdStr) {
        if (userIdStr != -1) {
            RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userIdStr));
            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ProfileResponse> call = apiService.viewProfile(userIdBody);

            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (isAdded() && response.isSuccessful() && response.body() != null && response.body().status) {
                        ProfileResponse.UserData userData = response.body().data.get(0);

                        String fullName = userData.name;
                        String email = userData.email;
                        String profileImage = userData.profile_image;
                        String imageUrl = ApiClient.getBaseUrl() + profileImage;

                        nameLabel.setText("Name: " + fullName);
                        emailLabel.setText("Mail ID: " + email);

                        Glide.with(requireContext())
                                .load(imageUrl)
                                .placeholder(R.drawable.user_profile)
                                .error(R.drawable.user_profile)
                                .circleCrop()
                                .into(profileImageView);
                    } else if (getContext() != null) {
                        Toast.makeText(getContext(), "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    if (getContext() != null) {
                        Toast.makeText(getContext(), "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        fetchProfile(userIdStr);
    }
}

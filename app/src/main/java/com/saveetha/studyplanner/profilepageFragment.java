package com.saveetha.studyplanner;

import static android.content.Context.MODE_PRIVATE;

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
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class profilepageFragment extends Fragment {

    private ImageView conarrow1, conarrow2, conarrow3, conarrow4, profileImageView;
    private TextView editprofile, nameLabel, emailLabel;
    private Button logout_button;

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

        if (!isAdded()) return v;

        final var context = requireContext();
        final var activity = requireActivity();

        SharedPreferences prefs = context.getSharedPreferences("UserSession", MODE_PRIVATE);
        int userIdStr = prefs.getInt("user_id", -1);

        if (userIdStr != -1) {
            RequestBody userIdBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(userIdStr));



            ApiService apiService = ApiClient.getClient().create(ApiService.class);
            Call<ProfileResponse> call = apiService.viewProfile(userIdBody);

            call.enqueue(new Callback<ProfileResponse>() {
                @Override
                public void onResponse(Call<ProfileResponse> call, Response<ProfileResponse> response) {
                    if (response.isSuccessful() && response.body() != null && response.body().status) {
                        ProfileResponse.UserData userData = response.body().data.get(0);

                        String fullName = userData.name;
                        String email = userData.email;
                        String profileImage = userData.profile_image;
                        String imageUrl = ApiClient.getBaseUrl() + profileImage;

                        nameLabel.setText("Name: " + fullName);
                        emailLabel.setText("Mail ID: " + email);

                        Glide.with(context)
                                .load(imageUrl)
                                .placeholder(R.drawable.user_profile)
                                .error(R.drawable.user_profile)
                                .circleCrop() // <-- This makes the image circular
                                .into(profileImageView);

                    } else {
                        Toast.makeText(context, "Failed to load profile", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ProfileResponse> call, Throwable t) {
                    Toast.makeText(context, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }

        // Arrows and buttons
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
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply();
            activity.startActivity(new Intent(context, LoginPage.class));
            activity.finish();
        });

        return v;
    }
}

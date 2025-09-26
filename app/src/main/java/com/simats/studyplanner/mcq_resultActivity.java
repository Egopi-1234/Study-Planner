package com.simats.studyplanner;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.simats.studyplanner.api.ApiClient;
import com.simats.studyplanner.api.ApiService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class mcq_resultActivity extends AppCompatActivity {

    private RecyclerView rvQuestions;
    private MCQResultAdapter adapter;
    private TextView tvScore;
    private int userId;
    private Button btnRetry10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mcq_result);

        tvScore = findViewById(R.id.tvScore);
        rvQuestions = findViewById(R.id.rvQuestions);
        rvQuestions.setLayoutManager(new LinearLayoutManager(this));

        btnRetry10 = findViewById(R.id.btnRetry10);
        ImageView backBtn = findViewById(R.id.backarrbt9);

        btnRetry10.setOnClickListener(v -> {
            startActivity(new Intent(mcq_resultActivity.this, mcqtestActivity.class));
            finish();
        });

        backBtn.setOnClickListener(v -> {
            startActivity(new Intent(mcq_resultActivity.this, ViewStudyMaterialActivity.class));
            finish();
        });

        // Get user_id
        userId = getIntent().getIntExtra("user_id", -1);
        if (userId == -1) {
            SharedPreferences prefs = getSharedPreferences("UserSession", MODE_PRIVATE);
            userId = prefs.getInt("user_id", -1);
        }

        if (userId == -1) {
            Toast.makeText(this, "User ID not found. Please login again.", Toast.LENGTH_LONG).show();
            finish();
            return;
        }

        fetchResults();
    }

    private void fetchResults() {
        ApiService apiService = ApiClient.getClient().create(ApiService.class);
        String json = "{\"user_id\":" + userId + "}";
        RequestBody requestBody =
                RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json);

        apiService.getResult(requestBody).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful() && response.body() != null) {
                    try {
                        String jsonResponse = response.body().string();
                        Log.d("API_RESPONSE", jsonResponse);

                        JSONObject jsonObject = new JSONObject(jsonResponse);
                        if (jsonObject.getBoolean("success")) {
                            int score = jsonObject.getInt("score");
                            int total = jsonObject.getInt("total_questions");
                            tvScore.setText("Score: " + score + "/" + total);

                            JSONArray arr = jsonObject.getJSONArray("questions");
                            List<MCQResult> list = new ArrayList<>();
                            for (int i = 0; i < arr.length(); i++) {
                                JSONObject q = arr.getJSONObject(i);
                                JSONObject opt = q.getJSONObject("options");
                                list.add(new MCQResult(
                                        q.getString("question"),
                                        opt.getString("A"),
                                        opt.getString("B"),
                                        opt.getString("C"),
                                        opt.getString("D"),
                                        q.getString("correct_option"),
                                        q.getString("selected_option")
                                ));
                            }
                            adapter = new MCQResultAdapter(list);
                            rvQuestions.setAdapter(adapter);
                        } else {
                            Toast.makeText(mcq_resultActivity.this,
                                    jsonObject.optString("message", "Error"),
                                    Toast.LENGTH_SHORT).show();
                        }
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Toast.makeText(mcq_resultActivity.this,
                            "No data available", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(mcq_resultActivity.this,
                        "Failed to fetch results", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

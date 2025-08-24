package com.saveetha.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class StudyMaterialAdapter extends RecyclerView.Adapter<StudyMaterialAdapter.MaterialViewHolder> {

    private Context context;
    private List<StudyMaterial> materialList;

    public StudyMaterialAdapter(Context context, List<StudyMaterial> materialList) {
        this.context = context;
        this.materialList = materialList;
    }

    @NonNull
    @Override
    public MaterialViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_study_material, parent, false);
        return new MaterialViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MaterialViewHolder holder, int position) {
        StudyMaterial material = materialList.get(position);

        holder.textTitle.setText(material.getName() + " - " + material.getSubject());
        holder.textDateTime.setText(material.getDueDate() + " " + material.getDueTime());

        // Set status
        String status = material.getStatus();
        holder.textStatus.setText("Status: " + status);

        // Optional: change status color
        switch (status.toLowerCase()) {
            case "completed":
                holder.textStatus.setTextColor(Color.parseColor("#4CAF50")); // Green
                break;
            case "pending":
                holder.textStatus.setTextColor(Color.parseColor("#FF9800")); // Orange
                break;
            case "uploaded":
                holder.textStatus.setTextColor(Color.parseColor("#2196F3")); // Blue
                break;
            default:
                holder.textStatus.setTextColor(Color.GRAY);
        }

        // Open details screen
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewStudyMaterialActivity.class);
            intent.putExtra("id", material.getId());
            intent.putExtra("name", material.getName());
            intent.putExtra("subject", material.getSubject());
            intent.putExtra("due_date", material.getDueDate());
            intent.putExtra("due_time", material.getDueTime());
            intent.putExtra("file_path", material.getFilePath());
            intent.putExtra("status", material.getStatus());
            context.startActivity(intent);
        });

        // Download logic (if needed)
        holder.imgDownload.setOnClickListener(v -> {
            // TODO: Implement download logic here
        });
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public static class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDateTime, textStatus;
        ImageView imgDownload;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            textStatus = itemView.findViewById(R.id.textStatus); // New status field
            imgDownload = itemView.findViewById(R.id.imgDownload);
        }
    }
}

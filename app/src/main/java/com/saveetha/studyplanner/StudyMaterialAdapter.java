package com.saveetha.studyplanner;

import android.content.Context;
import android.content.Intent;
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

        // Open details screen on click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewstudymaterialdetailsActivity.class);
            intent.putExtra("id", material.getId());
            intent.putExtra("name", material.getName());
            intent.putExtra("subject", material.getSubject());
            intent.putExtra("due_date", material.getDueDate());
            intent.putExtra("due_time", material.getDueTime());
            intent.putExtra("file_path", material.getFilePath());
            context.startActivity(intent);
        });

        // TODO: Download logic if needed
        holder.imgDownload.setOnClickListener(v -> {
            // Handle download logic
        });
    }

    @Override
    public int getItemCount() {
        return materialList.size();
    }

    public static class MaterialViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textDateTime;
        ImageView imgDownload;

        public MaterialViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDateTime = itemView.findViewById(R.id.textDateTime);
            imgDownload = itemView.findViewById(R.id.imgDownload);
        }
    }
}

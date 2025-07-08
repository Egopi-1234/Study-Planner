package com.saveetha.studyplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saveetha.studyplanner.R;
import com.saveetha.studyplanner.models.StudyMaterial;

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
        holder.textTitle.setText(material.getTitle());
        holder.textDateTime.setText(material.getDateTime());

        holder.imgDownload.setOnClickListener(v -> {
            // TODO: Implement download logic (open PDF or trigger download)
            // You can show a Toast or start a download task here
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

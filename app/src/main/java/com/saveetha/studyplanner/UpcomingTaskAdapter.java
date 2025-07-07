package com.saveetha.studyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class UpcomingTaskAdapter extends RecyclerView.Adapter<UpcomingTaskAdapter.ViewHolder> {

    private List<UpcomingTaskModel> taskList;

    public UpcomingTaskAdapter(List<UpcomingTaskModel> taskList) {
        this.taskList = taskList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView titleText, timeText;

        public ViewHolder(View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.task_title);
            timeText = itemView.findViewById(R.id.task_time);
        }
    }

    @NonNull
    @Override
    public UpcomingTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_upcoming_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        UpcomingTaskModel task = taskList.get(position);
        holder.titleText.setText(task.getTitle());
        holder.timeText.setText(task.getTime());
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}

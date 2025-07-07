package com.saveetha.studyplanner;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    List<TaskModel> tasks;
    Context context;

    public TaskAdapter(Context context, List<TaskModel> tasks) {
        this.context = context;
        this.tasks = tasks;
    }

    @NonNull
    @Override
    public TaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_task, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.ViewHolder holder, int position) {
        TaskModel task = tasks.get(position);
        holder.title.setText(task.getTitle());
        holder.time.setText(task.getTime());
        holder.date.setText(task.getDate());
        holder.priority.setText(task.getPriority());

        // Change color based on priority
        switch (task.getPriority()) {
            case "High":
                holder.priority.setBackgroundColor(Color.BLUE);
                break;
            case "Medium":
                holder.priority.setBackgroundColor(Color.GREEN);
                break;
            default:
                holder.priority.setBackgroundColor(Color.GRAY);
        }
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView title, time, date, priority;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.taskTitle);
            time = itemView.findViewById(R.id.taskTime);
            date = itemView.findViewById(R.id.taskDate);
            priority = itemView.findViewById(R.id.taskPriority);
        }
    }
}

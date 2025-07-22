package com.saveetha.studyplanner;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.TaskViewHolder> {

    private List<Task> taskList;
    private Context context;

    public MyTaskAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task, parent, false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        Task task = taskList.get(position);
        holder.taskName.setText(task.getTask());
        holder.taskDescription.setText(task.getDescription());
        holder.taskDatetime.setText(task.getDate() + " • " + task.getTime());
        holder.taskPriority.setText("Priority: " + task.getPriority());
        holder.taskStatus.setText("Status: " + task.getStatus());

        // On item click → Open detail activity
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, TaskdetailsviewpageActivity.class);
            intent.putExtra("task_name", task.getTask());
            intent.putExtra("description", task.getDescription());
            intent.putExtra("date", task.getDate());
            intent.putExtra("time", task.getTime());
            intent.putExtra("priority", task.getPriority());
            intent.putExtra("status", task.getStatus());
            intent.putExtra("id", task.getId()+"");
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }

    public static class TaskViewHolder extends RecyclerView.ViewHolder {
        TextView taskName, taskDescription, taskDatetime, taskPriority, taskStatus;

        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            taskDescription = itemView.findViewById(R.id.task_description);
            taskDatetime = itemView.findViewById(R.id.task_datetime);
            taskPriority = itemView.findViewById(R.id.task_priority);
            taskStatus = itemView.findViewById(R.id.task_status);
        }
    }
}

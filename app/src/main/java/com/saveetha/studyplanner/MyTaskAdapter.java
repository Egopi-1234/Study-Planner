package com.saveetha.studyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MyTaskAdapter extends RecyclerView.Adapter<MyTaskAdapter.ViewHolder> {

    private List<Task> list;

    public MyTaskAdapter(List<Task> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public MyTaskAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_task,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTaskAdapter.ViewHolder holder, int position) {

        Task task = list.get(position);

        holder.taskTitle.setText(task.getTitle());

        holder.priority.setText(task.getPriority());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskTitle ,priority;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskTitle = itemView.findViewById(R.id.taskTitle);
            priority = itemView.findViewById(R.id.textView9);
        }
    }
}

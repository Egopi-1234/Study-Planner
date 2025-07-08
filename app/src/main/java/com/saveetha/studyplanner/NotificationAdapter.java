package com.saveetha.studyplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.ViewHolder> {

    private List<NotificationModel> notificationList;

    public NotificationAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textTitle, textSub, textContent, textDue;
        ImageView imageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTitle = itemView.findViewById(R.id.textViewTitle);
            textSub = itemView.findViewById(R.id.textViewSub);
            textContent = itemView.findViewById(R.id.textViewContent);
            textDue = itemView.findViewById(R.id.textViewDue);
            imageView = itemView.findViewById(R.id.imageViewIcon);
        }
    }

    @NonNull
    @Override
    public NotificationAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.ViewHolder holder, int position) {
        NotificationModel model = notificationList.get(position);
        holder.textTitle.setText(model.getTitle());
        holder.textSub.setText(model.getSubtitle());
        holder.textContent.setText(model.getContent());
        holder.textDue.setText(model.getDueDate());
        holder.imageView.setImageResource(R.drawable.notification);
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }
}

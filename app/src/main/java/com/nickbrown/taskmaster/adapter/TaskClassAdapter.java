package com.nickbrown.taskmaster.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.amplifyframework.datastore.generated.model.TaskClass;
import com.nickbrown.taskmaster.activity.AllTasksActivity;
import com.nickbrown.taskmaster.activity.MainActivity;
import com.nickbrown.taskmaster.R;


import java.util.List;

public class TaskClassAdapter extends RecyclerView.Adapter<TaskClassAdapter.TaskViewHolder> {
    List<TaskClass> tasklist;
    Context callingActivity;

    public TaskClassAdapter(List<TaskClass> tasklist, Context callingActivity) {
        this.tasklist = tasklist;
        this.callingActivity = callingActivity;
    }

    public List<TaskClass> getTasklist() {
        return tasklist;
    }

    public void setTasklist(List<TaskClass> tasklist) {
        this.tasklist = tasklist;
    }

    public Context getCallingActivity() {
        return callingActivity;
    }

    public void setCallingActivity(Context callingActivity) {
        this.callingActivity = callingActivity;
    }

    @NonNull
    @Override
    public TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View taskFragment = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_task_list, parent, false);
        return new TaskViewHolder(taskFragment);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskViewHolder holder, int position) {
        TextView TaskListFragmentTextView = (TextView) holder.itemView.findViewById(R.id.TaskListFragmentTextView);
        String taskFragmentText = (position+1) + ". " + tasklist.get(position).getTitle() + "\n" + tasklist.get(position).getBody();
        TaskListFragmentTextView.setText(taskFragmentText);

        View taskViewHolder = holder.itemView;
        taskViewHolder.setOnClickListener(v -> {
            Intent intent = new Intent(callingActivity, AllTasksActivity.class);
            intent.putExtra(MainActivity.TASK_TITLE_EXTRA_TAG, tasklist.get(position).getTitle());
            intent.putExtra("TASK_DESCRIPTION", tasklist.get(position).getBody());
            intent.putExtra("TASK_CATEGORY", tasklist.get(position).getState().toString());
            intent.putExtra(MainActivity.TASK_ID_EXTRA_TAG, tasklist.get(position).getId());

            // Pass the S3 image key
            if (tasklist.get(position).getTaskImageS3Key() != null) {
                intent.putExtra("S3ImageKey", tasklist.get(position).getTaskImageS3Key());
            }
            callingActivity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return tasklist.size();
    }

    static class TaskViewHolder extends RecyclerView.ViewHolder {
        public TaskViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

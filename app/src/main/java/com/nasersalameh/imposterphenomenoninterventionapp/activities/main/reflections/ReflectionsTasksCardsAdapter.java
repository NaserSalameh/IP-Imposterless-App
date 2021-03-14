package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.reflections;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.ArrayList;

public class ReflectionsTasksCardsAdapter extends RecyclerView.Adapter<ReflectionsTasksCardsAdapter.ViewHolder> {

    private final Activity mainActivity;

    private LayoutInflater layoutInflater;
    private ArrayList<Task> tasksList;

    private Context context;

    public ReflectionsTasksCardsAdapter(Context context, ArrayList<Task> tasksList, Activity mainActivity){
        this.layoutInflater = LayoutInflater.from(context);
        this.tasksList = tasksList;

        this.context = context;
        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_reflections_activity_task_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //Bind card view
        Task currentTask = tasksList.get(index);

        viewHolder.taskNameTextView.setText(currentTask.getName());
    }

    public ArrayList<Task> getTasksList() {
        return tasksList;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return tasksList.size();
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView taskNameTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            taskNameTextView = itemView.findViewById(R.id.reflectionActivityTaskCardNameTextView);

        }
    }



}

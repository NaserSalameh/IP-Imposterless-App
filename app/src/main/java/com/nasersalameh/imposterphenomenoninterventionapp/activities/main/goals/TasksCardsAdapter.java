package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementCardPopup;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.ArrayList;

public class TasksCardsAdapter extends RecyclerView.Adapter<TasksCardsAdapter.ViewHolder> {

    private final Activity mainActivity;
    private RecyclerView tasksRecyclerView;

    private GoalsCardsAdapter goalsCardsAdapter;
    private int goalsPosition;

    private LayoutInflater layoutInflater;
    private ArrayList<Task> tasksList;

    private Context context;

    public TasksCardsAdapter(Context context, ArrayList<Task> tasksList, Activity mainActivity,RecyclerView tasksRecyclerView, GoalsCardsAdapter goalsCardsAdapter, int goalsPosition){
        this.layoutInflater = LayoutInflater.from(context);
        this.tasksList = tasksList;

        this.context = context;
        this.mainActivity = mainActivity;
        this.tasksRecyclerView = tasksRecyclerView;

        this.goalsCardsAdapter = goalsCardsAdapter;
        this.goalsPosition = goalsPosition;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_goals_task_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //Bind card view
        Task currentTask = tasksList.get(index);

        viewHolder.taskNameTextView.setText(currentTask.getName());

        //set up button based on completion state
        if(currentTask.isCompleted()){
            viewHolder.taskNameTextView.setPaintFlags(viewHolder.taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.taskCompletedButton.setBackgroundResource(R.drawable.ic_baseline_check_box_checked);
        }
        else{
            viewHolder.taskNameTextView.setPaintFlags(viewHolder.taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            viewHolder.taskCompletedButton.setBackgroundResource(R.drawable.ic_baseline_check_box_unchecked);
        }

        //set button to be checked + text to be strike through
        viewHolder.taskCompletedButton.setOnClickListener(v -> {
            if(currentTask.isCompleted()) {
                //set task to be incomplete
                currentTask.markAsIncomplete();
                viewHolder.taskNameTextView.setPaintFlags(viewHolder.taskNameTextView.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                viewHolder.taskCompletedButton.setBackgroundResource(R.drawable.ic_baseline_check_box_unchecked);

                goalsCardsAdapter.notifyItemChanged(goalsPosition);
            }
            else{
                //set task to be complete
                currentTask.markAsComplete();
                viewHolder.taskNameTextView.setPaintFlags(viewHolder.taskNameTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                viewHolder.taskCompletedButton.setBackgroundResource(R.drawable.ic_baseline_check_box_checked);

                goalsCardsAdapter.notifyItemChanged(goalsPosition);
            }
        });

        TasksCardsAdapter thisAdapter = this;
        viewHolder.cardView.setOnLongClickListener(v -> {
            System.out.println("Long Pressed Task  " + currentTask.getName());
            TaskCardPopup taskCardPopup = new TaskCardPopup(context, mainActivity,tasksRecyclerView, thisAdapter, index,currentTask,goalsCardsAdapter, goalsPosition, currentTask.getParentGoal());
            taskCardPopup.createPopUpWindow(viewHolder);
            return false;
        });

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

        MaterialCardView cardView;

        TextView taskNameTextView;

        Button taskCompletedButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.taskCardView);

            taskNameTextView = itemView.findViewById(R.id.taskCardTaskTextView);

            taskCompletedButton = itemView.findViewById(R.id.taskCardButton);
        }
    }



}

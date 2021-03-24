package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information.InformationCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.ArrayList;

public class GoalsCardsAdapter extends RecyclerView.Adapter<GoalsCardsAdapter.ViewHolder> {

    private final Activity mainActivity;

    private LayoutInflater layoutInflater;
    private ArrayList<Goal> goalsList;

    private Context context;

    private RecyclerView tasksRecyclerView;
    private RecyclerView goalsRecyclerView;

    //Local variables to update from different classes
    private ViewHolder viewHolder;

    private Goal activeGoal;

    public GoalsCardsAdapter(Context context, ArrayList<Goal> goalsList, Activity mainActivity, RecyclerView tasksRecyclerView, RecyclerView goalsRecyclerView){
        this.layoutInflater = LayoutInflater.from(context);
        this.goalsList = goalsList;

        this.context = context;
        this.mainActivity = mainActivity;

        this.tasksRecyclerView = tasksRecyclerView;
        this.goalsRecyclerView = goalsRecyclerView;

        //First goal is first in list
        if(!goalsList.isEmpty())
            activeGoal = goalsList.get(0);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_goals_goal_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        Goal currentGoal = goalsList.get(index);

        viewHolder.nameTextView.setText(currentGoal.getName());

        //save viewHolder as local variable
        this.viewHolder = viewHolder;

        //Set Goal card details
        String tasksRemaining = "Tasks: " + currentGoal.getNumberOfCompletedTasks() + "/" + currentGoal.getTasks().size();
        viewHolder.tasksTextView.setText(tasksRemaining);

        //set reflection all all tasks completed
        if(currentGoal.getTasksProgress() == 100 && currentGoal.getReflection() == null){
            viewHolder.reflectionTextView.setText("Goal Complete - Reflection Needed.");
            //If goal is complete, set completion Date
            currentGoal.setCompletionUnixDate(System.currentTimeMillis()/1000);
            System.out.println("Completed!: " + currentGoal.getCompletionUnixDate());
        }
        else
            viewHolder.reflectionTextView.setText("");


        viewHolder.tasksProgressBar.setProgress(currentGoal.getTasksProgress());

        //Set up Tasks recycler View
        updateTaskRecyclerView(currentGoal,index);

        viewHolder.cardView.setOnClickListener(v -> {
            //Set active goal
            activeGoal = currentGoal;
            updateTaskRecyclerView(currentGoal, index);
        });

        viewHolder.cardView.setOnLongClickListener(v -> {
            GoalCardPopup goalCardPopup = new GoalCardPopup(context, mainActivity,goalsRecyclerView,index, goalsList.get(index), goalsList);
            goalCardPopup.createPopUpWindow(viewHolder);
            return false;
        });
    }

    public void updateTaskRecyclerView(Goal currentGoal, int goalPosition){
        //Set up recycler adapter with information from usage database
        TasksCardsAdapter adapter = new TasksCardsAdapter(mainActivity, currentGoal.getTasks(),mainActivity,tasksRecyclerView,this, goalPosition);
        tasksRecyclerView.setAdapter(adapter);
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return goalsList.size();
    }

    public Goal getActiveGoal() {
        return this.activeGoal;
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardView;

        TextView nameTextView;
        TextView tasksTextView;
        TextView reflectionTextView;

        ProgressBar tasksProgressBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.goalCardView);

            nameTextView = itemView.findViewById(R.id.goalCardNameTextView);
            tasksTextView = itemView.findViewById(R.id.goalCardTasksTextView);
            reflectionTextView = itemView.findViewById(R.id.goalCardReflectionTextView);;
            tasksProgressBar = itemView.findViewById(R.id.goalCardProgressBar);
        }
    }

    public ArrayList<Goal> getGoalsList() {
        return goalsList;
    }
}

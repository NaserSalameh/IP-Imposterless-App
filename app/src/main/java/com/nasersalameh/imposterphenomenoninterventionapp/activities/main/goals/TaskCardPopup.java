package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.GoalData;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.Date;

public class TaskCardPopup {

    private final Context context;
    private final Activity mainActivity;

    private RecyclerView taskRecyclerView;

    private TasksCardsAdapter taskCardsAdapter;
    private int taskPosition;
    private final Task task;

    private GoalsCardsAdapter goalsCardsAdapter;
    private final int goalPosition;
    private final Goal goal;


    private PopupWindow popupWindow;

    public TaskCardPopup(Context context, Activity mainActivity, RecyclerView taskRecyclerView, TasksCardsAdapter taskCardsAdapter, int taskPosition, Task task, GoalsCardsAdapter goalsCardsAdapter, int goalPosition, Goal goal){
        this.context = context;
        this.mainActivity = mainActivity;

        this.taskRecyclerView = taskRecyclerView;

        this.taskCardsAdapter = taskCardsAdapter;
        this.taskPosition= taskPosition;
        this.task = task;

        this.goalsCardsAdapter = goalsCardsAdapter;
        this.goalPosition = goalPosition;
        this.goal = goal;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createPopUpWindow(TasksCardsAdapter.ViewHolder viewHolder) {
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_goals_task_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View view = taskRecyclerView.getLayoutManager().findViewByPosition(0);

        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);
        //what ever you do here will be done after 3 seconds delay.

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 500);

        //Handler to thread sleep and slow down process
        r = () -> setUpTaskPopup(container);
        handler.postDelayed(r, 500);

        this.popupWindow = popupWindow;
    }

    private void setUpTaskPopup(ViewGroup container) {

        TextView taskNameTextView = container.findViewById(R.id.taskPopupNameTextView);

        taskNameTextView.setText(task.getName());

        Button closeButton = container.findViewById(R.id.taskPopupTaskCloseButton);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        Button editTaskButton = container.findViewById(R.id.taskPopupEditTaskButton);
        editTaskButton.setOnClickListener(v -> {
            editTask(taskNameTextView);
        });

        Button deleteTaskButton = container.findViewById(R.id.taskPopupDeleteTaskButton);

        deleteTaskButton.setOnClickListener(v -> {
            deleteTask();
        });
    }

    private void editTask(TextView taskNameTextView) {
        task.setName(taskNameTextView.getText().toString());
        taskCardsAdapter.notifyItemChanged(taskPosition);

        writeToDb();
        popupWindow.dismiss();
    }

    private void deleteTask() {
        goal.removeTask(task);
        taskCardsAdapter.getTasksList().remove(task);

        goalsCardsAdapter.notifyItemChanged(goalPosition);

        writeToDb();
        popupWindow.dismiss();
    }

    private void writeToDb() {
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        AbilityData abilityData = new AbilityData(databaseHelper);
        GoalData goalData = new GoalData(databaseHelper, abilityData.getAbilitiesList());

        //Replace all goals with new goalsList
        goalData.replaceGoalsInDB(goalsCardsAdapter.getGoalsList());
    }

}


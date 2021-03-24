package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.GoalData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;
import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;

    private Activity mainActivity;

    private View root;

    private ArrayList<Goal> goalsList;
    private GoalsCardsAdapter goalsAdapter;

    private DatabaseHelper databaseHelper;
    private LogData logData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                new ViewModelProvider(this).get(GoalsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goals, container, false);

        mainActivity = getActivity();

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);

        this.root = root;

        setUpRecyclerView();

        setUpFloatingButton();
        setUpTitleFloatingButton();
        return root;
    }

    private ArrayList<Goal> loadGoalsFromDatabase() {
        //get Achievement from Usage Database
        AbilityData abilityData = new AbilityData(databaseHelper);
        GoalData goalData = new GoalData(databaseHelper, abilityData.getAbilitiesList());

        ArrayList<Goal> goalsListFromDB = goalData.getGoalsList();

        return goalsListFromDB;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(){
        //Get (updated) Goals List
        goalsList = loadGoalsFromDatabase();

        //Set up Tasks Recycler View
        RecyclerView tasksRecyclerView = root.findViewById(R.id.tasksRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        //get Goals Recycler View:
        RecyclerView goalsRecyclerView = root.findViewById(R.id.goalsRecyclerView);
        goalsRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity, LinearLayoutManager.HORIZONTAL, false));

        //Set up Goals recycler adapter with goals from usage database
        goalsAdapter = new GoalsCardsAdapter(mainActivity, goalsList,mainActivity, tasksRecyclerView, goalsRecyclerView);
        goalsRecyclerView.setAdapter(goalsAdapter);

        //if no goals, empty tasks view
        if(goalsList.isEmpty()){
            ArrayList<Task> emptyTasks = new ArrayList<>();
            TasksCardsAdapter adapter = new TasksCardsAdapter(mainActivity, emptyTasks,mainActivity,tasksRecyclerView,goalsAdapter, 0);
            tasksRecyclerView.setAdapter(adapter);        }
    }

    private void setUpFloatingButton() {
        FloatingActionButton addGoalButton = root.findViewById(R.id.goalsFloatingActionButton);
        addGoalButton.setOnClickListener(v -> {
            createChoicePopup();
        });
    }

    private void createChoicePopup(){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_goals_add_choice_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = root.findViewById(R.id.goalsConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 1000);

        Button closeButton = container.findViewById(R.id.goalsChoicePopupTaskCloseButton);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        Button goalChoiceButton = container.findViewById(R.id.goalsChoicePopupGoalButton);
        goalChoiceButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Clicked Add Goal Button"));

            popupWindow.dismiss();

            //Start Add Goal Activity
            Intent startAddGoalActivity = new Intent(mainActivity, GoalAddActivity.class);

            mainActivity.startActivity(startAddGoalActivity);
        });

        Button taskChoiceButton = container.findViewById(R.id.goalsChoicePopupTaskButton);

        //If there are no active goals
        if(goalsAdapter == null || goalsAdapter.getActiveGoal() == null)
            taskChoiceButton.setEnabled(false);
        else
            taskChoiceButton.setEnabled(true);

        taskChoiceButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Clicked Add Task Button"));

            popupWindow.dismiss();

            //Create Add Task Popup
            createAddTaskPopup();
        });
    }

    private void createAddTaskPopup() {
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.fragment_goals_add_task_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = root.findViewById(R.id.goalsConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 1000);

        EditText taskNameText = container.findViewById(R.id.goalsAddTaskPopupNameEditText);

        Button closeButton = container.findViewById(R.id.goalsAddTaskPopupCloseButton);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        Button addTaskButton = container.findViewById(R.id.goalsAddTaskPopupAddButton);
        addTaskButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Added New Task"));

            Goal activeGoal = goalsAdapter.getActiveGoal();

            Task newTask = new Task(taskNameText.getText().toString());

            newTask.setParentGoal(activeGoal);
            activeGoal.addTask(newTask);

            goalsAdapter.notifyItemChanged(goalsList.indexOf(activeGoal));
            writeToDb();
            popupWindow.dismiss();
        });
    }

    private void setUpTitleFloatingButton() {
        ContentData contentData = new ContentData(databaseHelper);
        Content content = contentData.getContentById("GOAL_TAB");

        FloatingActionButton tabGuide = root.findViewById(R.id.goalsTitleFloatingButton);

        tabGuide.setOnClickListener(v -> {
            createPopup(content.getName(),content.getContent());
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = getActivity().findViewById(R.id.goalsConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 1000);

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);

        Button helpPopupCloseButton = container.findViewById(R.id.helpPopupCloseButton);
        helpPopupCloseButton.setOnClickListener(v -> popupWindow.dismiss());
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        logData.insertNewLog(new Log("Goal","Switched to Goal Tab"));

        setUpRecyclerView();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onPause() {
        logData.insertNewLog(new Log("Goal","Left Goal Tab"));

        writeToDb();
        super.onPause();
    }

    private void writeToDb() {
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        AbilityData abilityData = new AbilityData(databaseHelper);
        GoalData goalData = new GoalData(databaseHelper, abilityData.getAbilitiesList());

        //Replace all goals with new goalsList
        goalData.replaceGoalsInDB(goalsList);
    }
}
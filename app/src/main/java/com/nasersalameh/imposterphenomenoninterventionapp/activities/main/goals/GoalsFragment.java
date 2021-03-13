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
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.GoalData;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;
import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;

    private Activity mainActivity;

    private View root;

    private ArrayList<Goal> goalsList;
    private GoalsCardsAdapter goalsAdapter;
    private boolean suppressWriteToDB;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                new ViewModelProvider(this).get(GoalsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goals, container, false);

        mainActivity = getActivity();

        this.root = root;

        setUpRecyclerView();

        setUpFloatingButton();

        return root;
    }

    private ArrayList<Goal> loadGoalsFromDatabase() {
        //get Achievement from Usage Database
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
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
        suppressWriteToDB = true;
        goalsAdapter = new GoalsCardsAdapter(mainActivity, goalsList,mainActivity, tasksRecyclerView, goalsRecyclerView, suppressWriteToDB);
        goalsRecyclerView.setAdapter(goalsAdapter);
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
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        Button goalChoiceButton = container.findViewById(R.id.goalsChoicePopupGoalButton);
        goalChoiceButton.setOnClickListener(v -> {
            suppressWriteToDB = true;
            popupWindow.dismiss();

            //Start Add Goal Activity
            Intent startAddGoalActivity = new Intent(mainActivity, GoalAddActivity.class);

            //add suppress boolean check
            startAddGoalActivity.putExtra("Suppress Check",suppressWriteToDB);

            mainActivity.startActivity(startAddGoalActivity);
        });

        Button taskChoiceButton = container.findViewById(R.id.goalsChoicePopupTaskButton);
        taskChoiceButton.setOnClickListener(v -> {
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
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        EditText taskNameText = container.findViewById(R.id.goalsAddTaskPopupNameEditText);

        Button addTaskButton = container.findViewById(R.id.goalsAddTaskPopupAddButton);
        addTaskButton.setOnClickListener(v -> {
            Goal activeGoal = goalsAdapter.getActiveGoal();

            Task newTask = new Task(taskNameText.getText().toString());

            newTask.setParentGoal(activeGoal);
            activeGoal.addTask(newTask);

            goalsAdapter.notifyItemChanged(goalsList.indexOf(activeGoal));

            popupWindow.dismiss();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        super.onResume();
        setUpRecyclerView();

    }

    @Override
    public void onPause() {
        super.onPause();
        if(!suppressWriteToDB)
            writeToDb();
    }

    private void writeToDb() {

        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        AbilityData abilityData = new AbilityData(databaseHelper);
        GoalData goalData = new GoalData(databaseHelper, abilityData.getAbilitiesList());

        //Replace all goals with new goalsList
        goalData.replaceGoalsInDB(goalsList);
    }
}
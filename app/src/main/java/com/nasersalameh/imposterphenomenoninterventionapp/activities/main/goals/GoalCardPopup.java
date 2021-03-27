package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;

public class GoalCardPopup {

    private final Context context;
    private final Activity mainActivity;

    private RecyclerView goalRecyclerView;
    private int goalPosition;

    private Goal goal;
    private final ArrayList<Goal> goalsList;

    private PopupWindow popupWindow;

    private DatabaseHelper databaseHelper;
    private LogData logData;

    public GoalCardPopup(Context context, Activity mainActivity, RecyclerView goalRecyclerView, int goalPosition, Goal goal, ArrayList<Goal> goalsList){
        this.context = context;
        this.mainActivity = mainActivity;
        this.goalRecyclerView = goalRecyclerView;

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);

        this.goal = goal;
        this.goalPosition = goalPosition;

        this.goalsList = goalsList;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createPopUpWindow(GoalsCardsAdapter.ViewHolder viewHolder) {
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_goals_goal_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
//        View view = goalRecyclerView.getLayoutManager().findViewByPosition(0);
        View view = (View) goalRecyclerView.getParent();

        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 250);

        //Handler to thread sleep and slow down process
        r = () -> setUpGoalPopup(container);
        handler.postDelayed(r, 250);

        this.popupWindow = popupWindow;
    }

    private void setUpGoalPopup(ViewGroup container) {
        //Set-up UI
        TextView nameTextView = container.findViewById(R.id.goalsPopupNameTextView);
        TextView typeTextView = container.findViewById(R.id.goalsPopupTypeTextView);
        TextView detailsTextView = container.findViewById(R.id.goalsPopupDetailsTextView);
        TextView dateTextView = container.findViewById(R.id.goalsPopupDateTextView);

        nameTextView.setText(goal.getName());
        typeTextView.setText(goal.getType());
        detailsTextView.setText(goal.getDetails());
        dateTextView.setText("Deadline: " + DateConverter.getDateFromUnixTime(goal.getDeadlineUnixDate()));

        //Chip group
        ChipGroup abilitiesChipGroup = container.findViewById(R.id.goalsPopupChipGroup);
        //remove previous chips
        abilitiesChipGroup.removeAllViews();

        //add all abilities
        for(Ability ability: goal.getAbilities()){
            Chip chip = (Chip) LayoutInflater.from(context)
                    .inflate(R.layout.fragment_abilities_card_improve_chip,abilitiesChipGroup, false);
            chip.setText(ability.getName());
            abilitiesChipGroup.addView(chip);
        }

        Button deleteButton = container.findViewById(R.id.goalPopupDeleteButton);
        deleteButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Deleted Goal " + goal.getName()));
            deleteGoal();
        });

        Button closeButton = container.findViewById(R.id.goalPopupCloseButton);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        Button reflectButton = container.findViewById(R.id.goalPopupReflectButton);
        //enable reflect button if needed
        if(goal.getTasksProgress() == 100 && goal.getReflection() == null){
            reflectButton.setEnabled(true);
        }
        else{
            reflectButton.setEnabled(false);
            reflectButton.setBackgroundTintList(context.getResources().getColorStateList(R.color.std_text));
            reflectButton.setTextColor(context.getResources().getColorStateList(R.color.std_background));
        }

        reflectButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Reflected on Goal " + goal.getName()));

            reflectGoal();
            popupWindow.dismiss();
        });
    }

    //Reflect Activity
    private void reflectGoal() {
        Intent startReflectionActivity = new Intent(mainActivity,GoalReflectionActivity.class);
        startReflectionActivity.putExtra("Goal", this.goal);

        //start Activity
        mainActivity.startActivity(startReflectionActivity);
    }

    private void deleteGoal() {
        //remove goal from ArrayList
        goalsList.remove(goal);
        //Remove goal
        goal = null;

        goalRecyclerView.removeViewAt(goalPosition);

        popupWindow.dismiss();

        mainActivity.recreate();
    }

}


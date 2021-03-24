package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.GoalData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GoalAddActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    private DatabaseHelper databaseHelper;
    private LogData logData;

    //UI
    Spinner goalTypeSpinner;

    EditText goalNameEditText;

    EditText goalDetailsEditText;

    Button selectDateButton;

    TextView selectedDateTextView;

    Button saveGoalButton;

    //Helper Floating buttons
    FloatingActionButton goalTypeFloatingButton;
    FloatingActionButton goalNameFloatingButton;
    FloatingActionButton goalDetailsFloatingButton;
    FloatingActionButton goalDateFloatingButton;
    FloatingActionButton goalAbilitiesChipFloatingButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_goals_add_activity);

        databaseHelper = new DatabaseHelper(this);
        logData = new LogData(databaseHelper);

        //Inject UI:
        goalTypeSpinner = findViewById(R.id.goalTypeSpinner);

        setUpSpinner();

        goalNameEditText = findViewById(R.id.goalNameEditText);

        goalDetailsEditText = findViewById(R.id.goalDetailsMultilineText);

        selectDateButton = findViewById(R.id.selectGoalDateButton);
        selectDateButton.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        selectedDateTextView = findViewById(R.id.selectedGoalDateTextView);
        Long currentUnix = System.currentTimeMillis();
        String currentDate = DateConverter.getDateFromUnixTime(currentUnix/1000);
        selectedDateTextView.setText("Selected Date: " + currentDate);

        //Set up Floating Buttons
        setUpFloatingButtons();

        Button closeButton = findViewById(R.id.cancelGoalButton);
        closeButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Cancelled Adding New Goal."));

            finish();
        });

        saveGoalButton = findViewById(R.id.saveGoalButton);

        saveGoalButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Added New Goal."));
            addGoal();

            //End Activity
            finish();
        });

        setUpChipGroup();
    }

    private void setUpChipGroup() {
        //set up ChipGroup
        ChipGroup chipGroup = findViewById(R.id.goalsAbilitiesChipGroup);
        //remove previous chips
        chipGroup.removeAllViews();

        //add all improvements
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        AbilityData abilityData = new AbilityData(databaseHelper);

        ArrayList<Ability> abilities = abilityData.getAbilitiesList();

        //Add ability Chips
        for(Ability ability: abilities){
            Chip chip = (Chip) getLayoutInflater().inflate(R.layout.fragment_goals_goal_abilities_chip,chipGroup, false);
            chip.setText(ability.getName());
            chip.isCheckable();
            chipGroup.addView(chip);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpFloatingButtons() {
        ContentData contentData = new ContentData(databaseHelper);

        goalTypeFloatingButton = findViewById(R.id.goalTypeFloatingButton);
        goalNameFloatingButton = findViewById(R.id.goalNameFloatingButton);
        goalDetailsFloatingButton = findViewById(R.id.goalDetailsFloatingButton);
        goalDateFloatingButton = findViewById(R.id.goalDateFloatingButton);
        goalAbilitiesChipFloatingButton = findViewById(R.id.goalAbilitiesFloatingButton);

        goalTypeFloatingButton.setOnClickListener(v -> {
            Content goalType = contentData.getContentById("GOAL_GUIDE_TYPE");
            String popupTitle = goalType.getName();
            String popupText = goalType.getContent();
            createPopup(popupTitle,popupText);
        });

        goalNameFloatingButton.setOnClickListener(v -> {
            Content goalName = contentData.getContentById("GOAL_GUIDE_NAME");
            String popupTitle = goalName.getName();
            String popupText = goalName.getContent();
            createPopup(popupTitle,popupText);
        });

        goalDetailsFloatingButton.setOnClickListener(v -> {
            Content goalDetails = contentData.getContentById("GOAL_GUIDE_DETAILS");
            String popupTitle = goalDetails.getName();
            String popupText = goalDetails.getContent();
            createPopup(popupTitle,popupText);
        });

        goalDateFloatingButton.setOnClickListener(v -> {
            Content goalDeadline = contentData.getContentById("GOAL_GUIDE_DEADLINE");
            String popupTitle = goalDeadline.getName();
            String popupText = goalDeadline.getContent();
            createPopup(popupTitle,popupText);
        });

        goalAbilitiesChipFloatingButton.setOnClickListener(v -> {
            Content goalAbility = contentData.getContentById("GOAL_GUIDE_ABILITY");
            String popupTitle = goalAbility.getName();
            String popupText = goalAbility.getContent();
            createPopup(popupTitle,popupText);
        });

    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(GoalAddActivity.this).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.addGoalConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 1000);

        logData.insertNewLog(new Log("Goal","Clicked Help Button: " + popupTitle));

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);

        Button helpPopupCloseButton = container.findViewById(R.id.helpPopupCloseButton);
        helpPopupCloseButton.setOnClickListener(v -> popupWindow.dismiss());
    }

    private void setUpSpinner() {

        ArrayList<String> goalTypeList = new ArrayList<>();
        goalTypeList.add("Small Goal");
        goalTypeList.add("Medium Goal");
        goalTypeList.add("Big Goal");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, goalTypeList);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        goalTypeSpinner.setAdapter(spinnerAdapter);
    }

    private void showDatePickerDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        String date = dayOfMonth + "/" + (month+1) + "/" + year;
        selectedDateTextView.setText("Selected Date: " + date);
    }

    private void addGoal() {
        //Get all Goal Data
        String goalName = goalNameEditText.getText().toString();
        String goalType = goalTypeSpinner.getSelectedItem().toString();
        String goalDetails = goalDetailsEditText.getText().toString();

        //only get the date
        String date = selectedDateTextView.getText().toString().split(" ")[2];
        Long goalDate = DateConverter.getUnixTimeFromData(date);

        Goal newGoal = new Goal(goalName, goalDetails,goalType, goalDate);

        //Get Selected Chips
        ChipGroup chipGroup = findViewById(R.id.goalsAbilitiesChipGroup);
        List<Integer> checkedChipIds = chipGroup.getCheckedChipIds();
        for (Integer chipId : checkedChipIds) {
            Chip chip = chipGroup.findViewById(chipId);

            //will only write the names to table, when it reads the dbHelper will get the persistent ability object
            Ability newTempAbility = new Ability(chip.getText().toString());
            newGoal.addAbility(newTempAbility);
        }

        //Prep database writers
        AbilityData abilityData = new AbilityData(databaseHelper);
        GoalData goalData = new GoalData(databaseHelper, abilityData.getAbilitiesList());

        //write new Goal
        goalData.insertNewGoal(newGoal);
    }
}

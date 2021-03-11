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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;

import java.util.ArrayList;
import java.util.Calendar;

public class GoalAddActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

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

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_goals_add_activity);

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
        String currentDate = DateConverter.getDateFromUnixTime(currentUnix);
        selectedDateTextView.setText("Selected Date: " + currentDate);

        //Set up Floating Buttons
        setUpFloatingButtons();

        saveGoalButton = findViewById(R.id.saveGoalButton);

        saveGoalButton.setOnClickListener(v -> {
            addGoal();

            //End Activity
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpFloatingButtons() {
        goalTypeFloatingButton = findViewById(R.id.goalNameFloatingButton);
        goalNameFloatingButton = findViewById(R.id.goalDetailsFloatingButton);
        goalDetailsFloatingButton = findViewById(R.id.goalTypeFloatingButton);
        goalDateFloatingButton = findViewById(R.id.goalDateFloatingButton);

        goalTypeFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        goalNameFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        goalDetailsFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        goalDateFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(GoalAddActivity.this).inflate(R.layout.fragment_goals_task_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.addGoalConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        TextView popupTitleTextView = container.findViewById(R.id.addGoalHelpTitlePopupTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.addGoalHelpPopupTextView);
        popupTextView.setText(popupText);
    }

    private void setUpSpinner() {

        ArrayList<String> goalTypeList = new ArrayList<>();
        goalTypeList.add("Small");
        goalTypeList.add("Medium");
        goalTypeList.add("Big");

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
        String date = dayOfMonth + "/" + month + "/" + year;
        selectedDateTextView.setText("Selected Date: " + date);
    }

    private void addGoal() {

    }



}

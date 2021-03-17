package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class AchievementAddActivity extends FragmentActivity implements DatePickerDialog.OnDateSetListener {

    //UI
    Spinner achievementTypeSpinner;

    EditText achievementNameEditText;

    EditText achievementDetailsEditText;

    Button selectDateButton;

    TextView selectedDateTextView;

    Button saveAchievementButton;

    //Helper Floating buttons
    FloatingActionButton achievementTypeFloatingButton;
    FloatingActionButton achievementNameFloatingButton;
    FloatingActionButton achievementDetailsFloatingButton;
    FloatingActionButton achievementDateFloatingButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_achievements_add_activity);

        //Inject UI:
        achievementTypeSpinner = findViewById(R.id.achievementTypeSpinner);

        setUpSpinner();

        achievementNameEditText = findViewById(R.id.achievementNameEditText);

        achievementDetailsEditText = findViewById(R.id.achievementDetailsMultilineText);

        selectDateButton = findViewById(R.id.selectDateButton);
        selectDateButton.setOnClickListener(v -> {
            showDatePickerDialog();
        });

        selectedDateTextView = findViewById(R.id.selectedDateTextView);
        Long currentUnix = System.currentTimeMillis();
        String currentDate = DateConverter.getDateFromUnixTime(currentUnix/1000);
        selectedDateTextView.setText("Selected Date: " + currentDate);

        //Set up Floating Buttons
        setUpFloatingButtons();

        Button closeButton = findViewById(R.id.cancelAchievementButton);
        closeButton.setOnClickListener(v -> finish());

        saveAchievementButton = findViewById(R.id.saveAchievementButton);

        saveAchievementButton.setOnClickListener(v -> {
            saveAchievement();

           //End Activity
            finish();
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpFloatingButtons() {
        achievementTypeFloatingButton = findViewById(R.id.achievementTypeFloatingButton);
        achievementNameFloatingButton = findViewById(R.id.achievementNameFloatingButton);
        achievementDetailsFloatingButton = findViewById(R.id.achievementDetailsFloatingButton);
        achievementDateFloatingButton = findViewById(R.id.achievementDateFloatingButton);

        achievementTypeFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        achievementNameFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        achievementDetailsFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        achievementDateFloatingButton.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(AchievementAddActivity.this).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.addAchievementConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpPopupConstraintLayout);
        popupTextView.setText(popupText);
    }

    private void setUpSpinner() {

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);
        ArrayList<AchievementType> achievementTypes = achievementsTypeData.getAchievementsTypeList();

        ArrayList<String> achievementTypesString = new ArrayList<>();

        //Add User Addable Achievements ONLY
        for(AchievementType a: achievementTypes)
            if(a.isUserAddable())
                achievementTypesString.add(a.getAchievementType());


        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item,achievementTypesString );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        achievementTypeSpinner.setAdapter(spinnerAdapter);
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

    private void saveAchievement() {

        //Prep database writers
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);

        AchievementData achievementData = new AchievementData(databaseHelper,achievementsTypeData.getAchievementsTypeList());

        String achievementName = achievementNameEditText.getText().toString();

        //Get achievement Type
        AchievementType achievementType = achievementsTypeData.getAchievementTypeGivenName(achievementTypeSpinner.getSelectedItem().toString());

        String achievementDetails = achievementDetailsEditText.getText().toString();

        //only get the date
        String date = selectedDateTextView.getText().toString().split(" ")[2];
        Long achievementDate = DateConverter.getUnixTimeFromData(date);

        //Create and write new achievement
        Achievement newAchievement = new Achievement(achievementName,achievementDetails,achievementType,achievementDate);
        achievementData.insertNewAchievement(newAchievement);

    }



}

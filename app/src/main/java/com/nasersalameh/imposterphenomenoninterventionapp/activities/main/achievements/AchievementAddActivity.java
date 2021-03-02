package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.app.DatePickerDialog;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
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

       saveAchievementButton = findViewById(R.id.saveAchievementButton);

       saveAchievementButton.setOnClickListener(v -> {
           saveAchievement();

           //End Activity
           finish();
       });

    }

    private void setUpSpinner() {

        ArrayList<String> testTypes = new ArrayList<>();
        for(int i=0;i<5;i++)
            testTypes.add("type"+i);

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, testTypes);
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
        Long achievementDate = getUnixTimeFromData(date);

        //Create and write new achievement
        Achievement newAchievement = new Achievement(achievementName,achievementDetails,achievementType,achievementDate);
        achievementData.insertNewAchievement(newAchievement);

    }

    private Long getUnixTimeFromData(String Date){
         try {
             DateFormat dateFormat = new SimpleDateFormat("dd/M/yyyy");
             java.util.Date date = dateFormat.parse(Date);
             long unixTime = (long) date.getTime()/1000;
             System.out.println(unixTime );
             return unixTime;
         }
         catch (ParseException e) {
            e.printStackTrace();
        }
         return 0l;
    }

}

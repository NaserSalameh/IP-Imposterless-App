package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.DragEvent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;

import java.util.ArrayList;

public class GoalReflectionActivity extends FragmentActivity {

    //Goal passed from intent
    Goal goal;

    //UI:
    private EditText achievementEditText;
    private Spinner achievementTypeSpinner;

    private Spinner abilitySpinner;
    private RangeSlider abilityRangeSlider;

    private CheckBox blockerCheckBox;
    private EditText blockerEditText;
    private RangeSlider blockerRangeSlider;

    private TextView deadlineQuestionTextView;
    private TextView deadlineResultTextView;
    private EditText deadlineReasonEditText;
    private boolean deadlineMet;

    private RangeSlider successRangeSlider;
    private RangeSlider expectationRangeSlider;

    //Helper Floaters
    private FloatingActionButton blockerHelper;
    private FloatingActionButton expectationHelper;

    private Button seeAchievementsButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_goals_reflection_activity_information);

        setUpReflectionInformationLayout();

        goal = (Goal) getIntent().getSerializableExtra("Goal");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpReflectionInformationLayout() {

        TextView informationTextView = findViewById(R.id.reflectionInfoTextView);

        Button startReflectionButton = findViewById(R.id.startReflectionButton);
        startReflectionButton.setOnClickListener(v -> transitionToReflectionFormLayout());

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void transitionToReflectionFormLayout() {
        //Change view
        setContentView(R.layout.fragment_goals_reflection_activity_form);

        //Inject UI
        achievementEditText = findViewById(R.id.reflectionAchievementEditText);
        achievementTypeSpinner =  findViewById(R.id.reflectionAchievementTypeSpinner);

        abilitySpinner =  findViewById(R.id.reflectionAbilitySpinner);
        abilityRangeSlider =  findViewById(R.id.reflectionAbilityRangeSlider);

        blockerCheckBox =  findViewById(R.id.reflectionBlockerCheckBox);
        blockerEditText =  findViewById(R.id.reflectionBlockerEditText);
        blockerRangeSlider =  findViewById(R.id.reflectionBlockerRangeSlider);

        deadlineQuestionTextView =  findViewById(R.id.reflectionDeadlineQuestionTextView);
        deadlineResultTextView =  findViewById(R.id.reflectionDeadlineResultTextView);
        deadlineReasonEditText =  findViewById(R.id.reflectionDeadlineEditText);

        successRangeSlider =  findViewById(R.id.reflectionSuccessRangeSlider);
        expectationRangeSlider =  findViewById(R.id.reflectionExpectationRangeSlider);

        seeAchievementsButton =  findViewById(R.id.createReflectionAchievementsButton);

        setUpSpinners();
        setUpRangeSliders();
        setUpFloatingButtons();

        //Hide Constraint Layout if no abilities Selected for improvement
        if(goal.getAbilities().isEmpty()){
            ConstraintLayout abilitiesConstraintLayout = findViewById(R.id.reflectionAbilityConstraintLayout);
            abilitiesConstraintLayout.setVisibility(View.GONE);
        }

        //Hide Constraint Layout if no blockers
        if(!blockerCheckBox.isSelected()){
            ConstraintLayout blockerConstraintLayout= findViewById(R.id.reflectionBlockerConstraintLayout);
            blockerConstraintLayout.setVisibility(View.GONE);
        }

        setupDeadlineSection();

        seeAchievementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transitionToGoalAchievementsLayout();
            }
        });
    }

    private void transitionToGoalAchievementsLayout() {
        setContentView(R.layout.fragment_goals_reflection_activity_achievements);


        //At activity end remove suppression
        boolean suppressionCheck = (boolean) getIntent().getSerializableExtra("Suppress Check");
        suppressionCheck = false;
    }

    private void setupDeadlineSection() {
        if(goal.getCompletionUnixDate() > goal.getDeadlineUnixDate()){
            deadlineMet = false;
            deadlineResultTextView.setText("You have finished the goal past the set deadline.");
            deadlineQuestionTextView.setText("Any reflections on how to meet the deadline next time?");
        }
        else{
            deadlineMet = true;
            deadlineResultTextView.setText("Congrats on meeting the deadline!!");
            deadlineQuestionTextView.setText("What Helped You Complete The Goal Before The Deadline?");
        }


    }

    private void setUpRangeSliders() {
        LabelFormatter abilityLabelFormatter = value -> {
            switch ((int) value){
                case 1: return "Small Improvement";
                case 2: return "Notable Improvement";
                case 3: return "Great Improvement";
            }
            return "FAILURE";
        };
        abilityRangeSlider.setLabelFormatter(abilityLabelFormatter);

        LabelFormatter blockerLabelFormatter = value -> {
            switch ((int) value){
                case 5: return "Great Ease";
                case 4: return "Small Ease";
                case 3: return "No Difficulty";
                case 2: return "Small Difficulty";
                case 1: return "Great Difficulty";
            }
            return "FAILURE";
        };
        blockerRangeSlider.setLabelFormatter(blockerLabelFormatter);

        LabelFormatter successLabelFormatter = value -> {
            switch ((int) value){
                case 1: return "Not A Success";
                case 2: return "Small Success";
                case 3: return "Notable Success";
                case 4: return "Great Success";
            }
            return "FAILURE";
        };
        successRangeSlider.setLabelFormatter(successLabelFormatter);

        //Set Listener for low success
        successRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                ConstraintLayout lowSuccessConstraintLayout= findViewById(R.id.reflectionLowSuccessConstraintLayout);
                if(successRangeSlider.getValues().get(0) <= 2)
                    lowSuccessConstraintLayout.setVisibility(View.VISIBLE);
                else
                    lowSuccessConstraintLayout.setVisibility(View.GONE);
            }
        });

        LabelFormatter expectationLabelFormatter = value -> {
            switch ((int) value){
                case 1: return "Did Not Align With Expectation";
                case 2: return "Somewhat Aligned With Expectation";
                case 3: return "Aligned With Expectation";
                case 4: return "Exceeded Expectation";
            }
            return "FAILURE";
        };

        expectationRangeSlider.setLabelFormatter(expectationLabelFormatter);
        expectationRangeSlider.addOnSliderTouchListener(new RangeSlider.OnSliderTouchListener() {
            @Override
            public void onStartTrackingTouch(@NonNull RangeSlider slider) {

            }

            @Override
            public void onStopTrackingTouch(@NonNull RangeSlider slider) {
                ConstraintLayout lowExpectationConstraintLayout= findViewById(R.id.reflectionLowExpectationConstraintLayout);
                if(expectationRangeSlider.getValues().get(0) == 1)
                    lowExpectationConstraintLayout.setVisibility(View.VISIBLE);
                else
                    lowExpectationConstraintLayout.setVisibility(View.GONE);
            }
        });

        abilityRangeSlider.setValues(2f);
        blockerRangeSlider.setValues(3f);
        successRangeSlider.setValues(3f);
        expectationRangeSlider.setValues(3f);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpFloatingButtons() {

        blockerHelper = findViewById(R.id.reflectionsBlockerHelpButton);
        expectationHelper = findViewById(R.id.reflectionsExpectationHelpButton);

        blockerHelper.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });

        expectationHelper.setOnClickListener(v -> {
            String popupTitle = "TITLE";
            String popupText = "TESTSTSTESTST";
            createPopup(popupTitle,popupText);
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(GoalReflectionActivity.this).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.addGoalConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);
    }

    private void setUpSpinners() {

        //Get DB helpers
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);

        //Add goal Abilities
        ArrayList<String> abilityStrings = new ArrayList<>();
        for(Ability ability : goal.getAbilities())
            abilityStrings.add(ability.getName());

        ArrayAdapter<String> abilitySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, abilityStrings);
        abilitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        abilitySpinner.setAdapter(abilitySpinnerAdapter);

        ArrayList<String> achievementTypeStrings = new ArrayList<>();
        for(AchievementType achievementsType: achievementsTypeData.getAchievementsTypeList())
            achievementTypeStrings.add(achievementsType.getAchievementType());

        ArrayAdapter<String> achievementTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, achievementTypeStrings);
        achievementTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        abilitySpinner.setAdapter(achievementTypeSpinnerAdapter);
    }


}

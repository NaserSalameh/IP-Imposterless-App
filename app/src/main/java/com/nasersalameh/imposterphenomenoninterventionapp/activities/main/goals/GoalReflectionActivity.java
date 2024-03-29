package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.transition.AutoTransition;
import android.transition.Scene;
import android.transition.TransitionManager;
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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.GoalData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ReflectionData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

import java.util.ArrayList;

public class GoalReflectionActivity extends FragmentActivity {

    public static final int EXP_AMOUNT = 50;
    //Goal passed from intent
    Goal goal;

    private DatabaseHelper databaseHelper;
    private LogData logData;

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
    private EditText lowSuccessEditText;

    private RangeSlider expectationRangeSlider;
    private EditText lowExpectationEditText;


    //Helper Floaters
    private FloatingActionButton blockerHelper;
    private FloatingActionButton expectationHelper;

    private Button seeAchievementsButton;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_goals_reflection_activity_information);

        databaseHelper = new DatabaseHelper(this);
        logData = new LogData(databaseHelper);

        setUpReflectionInformationLayout();

        goal = (Goal) getIntent().getSerializableExtra("Goal");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUpReflectionInformationLayout() {
        ContentData contentData = new ContentData(databaseHelper);

        TextView informationTextView = findViewById(R.id.reflectionInfoTextView);
        informationTextView.setText(contentData.getContentById("REFLECTION_ACTIVITY").getContent());

        Button closeButton = findViewById(R.id.closeReflectionButton);
        closeButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Closed Goal Reflection " + goal.getName()));

            finish();
        });

        Button startReflectionButton = findViewById(R.id.startReflectionButton);
        startReflectionButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Proceeded To Reflection Form " + goal.getName()));

            transitionToReflectionFormLayout();
        });

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
        lowSuccessEditText = findViewById(R.id.reflectionLowSuccessEditText);

        expectationRangeSlider =  findViewById(R.id.reflectionExpectationRangeSlider);
        lowExpectationEditText = findViewById(R.id.reflectionLowExpectationEditText);

        seeAchievementsButton =  findViewById(R.id.createReflectionAchievementsButton);

        setUpSpinners();
        setUpRangeSliders();
        setUpFloatingButtons();

        //Hide Constraint Layout if no abilities Selected for improvement
        ConstraintLayout abilitiesConstraintLayout = findViewById(R.id.reflectionAbilityConstraintLayout);
        if(goal.getAbilities().isEmpty())
            abilitiesConstraintLayout.setVisibility(View.GONE);
        else
            abilitiesConstraintLayout.setVisibility(View.VISIBLE);


        blockerCheckBox.setOnClickListener(v -> {
            ConstraintLayout blockerConstraintLayout= findViewById(R.id.reflectionBlockerConstraintLayout);
            //Hide Constraint Layout if no blockers
            if(blockerConstraintLayout.getVisibility() == View.GONE){
                ViewGroup view = (ViewGroup) ((ViewGroup) findViewById(android.R.id.content)).getChildAt(0);
                TransitionManager.beginDelayedTransition(view, new AutoTransition());

                blockerConstraintLayout.setVisibility(View.VISIBLE);
            }
            else{
                blockerConstraintLayout.setVisibility(View.GONE);
            }
        });

        setupDeadlineSection();

        Button cancelButton = findViewById(R.id.closeReflectionAchievementsButton);
        cancelButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Cancelled Goal Reflection"));
            finish();
        });

        seeAchievementsButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("Goal","Saved Goal Reflection"));

            Reflection reflection = collectReflection();
            ArrayList<Achievement> achievementList = getAchievements(reflection);
            transitionToGoalAchievementsLayout(achievementList);
        });
    }

    private Reflection collectReflection() {

        Reflection reflection = new Reflection(goal);

        reflection.setGreatAchievement(achievementEditText.getText().toString());
        reflection.setAchievementType(achievementTypeSpinner.getSelectedItem().toString());

        if(!goal.getAbilities().isEmpty()){
            //Get ability Data to write changes
            AbilityData abilityData = new AbilityData(databaseHelper);

            //Get best ability
            reflection.setBestAbility(abilitySpinner.getSelectedItem().toString());
            for(Ability ability: goal.getAbilities()){
                if(ability.getName().equals(reflection.getBestAbility()))
                    //Add improvement to best ability
                    abilityData.addExpToAbility(ability, (int) (EXP_AMOUNT*abilityRangeSlider.getValues().get(0)));
                else
                    abilityData.addExpToAbility(ability, EXP_AMOUNT);
            }
        }

        if(blockerCheckBox.isSelected()){
            reflection.setBlocker(blockerEditText.getText().toString());
            reflection.setBlockerDifficulty((int) Math.floor(blockerRangeSlider.getValues().get(0)));
        }

        reflection.setDeadlineMet(deadlineMet);
        if(deadlineMet){
            reflection.setDeadlineReason(deadlineReasonEditText.getText().toString());
        }

        int successScore = (int) Math.floor(successRangeSlider.getValues().get(0));
        reflection.setSuccessScore(successScore);
        if(successScore <= 2){
            String reason = lowSuccessEditText.getText().toString() + "";
            reflection.setLowSuccessReason(reason);
        }

        int expectationScore = (int) Math.floor(expectationRangeSlider.getValues().get(0));
        reflection.setExpectationScore(expectationScore);
        if(expectationScore == 1){
            String reason = lowExpectationEditText.getText().toString() + "";
            reflection.setLowExpectationReason(reason);
        }

        AbilityData abilityData = new AbilityData(databaseHelper);

        //Delete Goal from goals Table
        GoalData goalData = new GoalData(databaseHelper,abilityData.getAbilitiesList());
        goalData.deleteGoalRow(goal);

        //Write Reflection to DB
        ReflectionData reflectionData = new ReflectionData(databaseHelper,abilityData.getAbilitiesList());
        reflectionData.insertNewReflection(reflection);
        return reflection;
    }

    private ArrayList<Achievement> getAchievements(Reflection reflection) {

        ArrayList<Achievement> achievements = new ArrayList<>();

        //Get Achievement Types
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);
        ArrayList<AchievementType> achievementTypes = achievementsTypeData.getAchievementsTypeList();

        //Finished Goal
        Achievement goalAchievement = new Achievement(
                goal.getName()+" Goal Achievement",
                "Awarded for completing the " + goal.getType() +" size goal: " + goal.getName() + "!",
                goal.getCompletionUnixDate());

        for(AchievementType type: achievementTypes){
            if(type.getAchievementType().equals(goal.getType()))
                goalAchievement.setAchievementType(type);
        }

        achievements.add(goalAchievement);

        //Large Task Achievement
        if(goal.getTasks().size()>6){
            Achievement taskSizeAchievement = new Achievement(
                    goal.getName()+" Goal - Task Size Achievement",
                    "Awarded for completing a goal with a considerable number of tasks (Goal: " + goal.getName() + ")!",
                    goal.getCompletionUnixDate());

            for(AchievementType type: achievementTypes)
                if(type.getAchievementType().equals("Task Size"))
                    taskSizeAchievement.setAchievementType(type);

            achievements.add(taskSizeAchievement);
        }

        //Greatest Achievement
        Achievement greatestAchievement = new Achievement(
                goal.getName()+" Goal - " + reflection.getGreatAchievement(),
                "Awarded as the greatest achievement during the " + goal.getName() + " Goal!",
                goal.getCompletionUnixDate());


        for(AchievementType type: achievementTypes)
            if(type.getAchievementType().equals(reflection.getAchievementType()))
                greatestAchievement.setAchievementType(type);

        achievements.add(greatestAchievement);

        //Ability Boost Achievement
        if(abilityRangeSlider.getValues().get(0)==3){
            Achievement abilityAchievement = new Achievement(
                    goal.getName()+" Goal - Ability Boost Achievement",
                    "Awarded for a large ability improvement during the " + goal.getName() + " Goal!",
                    goal.getCompletionUnixDate());

            for(AchievementType type: achievementTypes)
                if(type.getAchievementType().equals("Ability Boost"))
                    abilityAchievement.setAchievementType(type);

            achievements.add(abilityAchievement);
        }

        //Blocker Surpassing Achievement
        if(blockerCheckBox.isSelected()){
            Achievement blockerAchievement = new Achievement(
                    goal.getName()+" Goal - Overcoming Blocker Achievement",
                    "Awarded for overcoming a blocker during the " + goal.getName() + " Goal!",
                    goal.getCompletionUnixDate());

            for(AchievementType type: achievementTypes)
                if(type.getAchievementType().equals("Overcoming Blocker"))
                    blockerAchievement.setAchievementType(type);

            achievements.add(blockerAchievement);
        }

        //Deadline Met Achievement
        if(deadlineMet){
            Achievement deadlineMetAchievement = new Achievement(
                    goal.getName()+" Goal - Deadline Met Achievement",
                    "Awarded for meeting the deadline for the " + goal.getName() + " Goal!",
                    goal.getCompletionUnixDate());

            for(AchievementType type: achievementTypes)
                if(type.getAchievementType().equals("Meeting Deadline"))
                    deadlineMetAchievement.setAchievementType(type);

            achievements.add(deadlineMetAchievement);
        }

        //Aligned Expectation achievement
        if(expectationRangeSlider.getValues().get(0) >= 3){
            Achievement alignedExpectation = new Achievement(
                    goal.getName()+" Goal - Aligned Expectation Achievement",
                    "Awarded for the result aligning and/or exceeding expectation for the " + goal.getName() + " Goal!",
                    goal.getCompletionUnixDate());

            for(AchievementType type: achievementTypes)
                if(type.getAchievementType().equals("Aligned Expectation"))
                    alignedExpectation.setAchievementType(type);

            achievements.add(alignedExpectation);
        }

        //Write Achievements to DB
        AchievementData achievementData = new AchievementData(databaseHelper,achievementTypes);
        for(Achievement achievement:achievements){
            achievementData.insertNewAchievement(achievement);
        }

        return achievements;
    }

    private void transitionToGoalAchievementsLayout(ArrayList<Achievement> achievementList) {
        setContentView(R.layout.fragment_goals_reflection_activity_achievements);

        //Set Score
        int score = 0;
//        for(Achievement achievement: achievementList){
//            score+=achievement.getAchievementType().getAchievementScore();
//        }

//        TextView achievementScoreView = findViewById(R.id.reflectionsAchievementScoreTextView);
//        achievementScoreView.setText("Achievement Score Earned: " + score + "!");

        RecyclerView reflectionAchievementsRecyclerView = findViewById(R.id.reflectionAchievementsRecyclerView);
        reflectionAchievementsRecyclerView.setLayoutManager(new GridLayoutManager(this, 3));

        //Set up recycler adapter
        AchievementCardsAdapter adapter = new AchievementCardsAdapter(this, achievementList,this,reflectionAchievementsRecyclerView);
        reflectionAchievementsRecyclerView.setAdapter(adapter);

        //Finish activity
        Button saveAchievementButton = findViewById(R.id.saveReflectionButton);
        saveAchievementButton.setOnClickListener(v -> finish());
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
        ContentData contentData = new ContentData(databaseHelper);

        expectationHelper = findViewById(R.id.reflectionsExpectationHelpButton);

        expectationHelper.setOnClickListener(v -> {
            Content reflectionExpectation = contentData.getContentById("REFLECTION_GUIDE");
            String popupTitle = reflectionExpectation.getName();
            String popupText = reflectionExpectation.getContent();
            createPopup(popupTitle,popupText);
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(GoalReflectionActivity.this).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.goalReflectionConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 250);

        logData.insertNewLog(new Log("Goal","Clicked Help Button: " + popupTitle));

        Button closeButton = container.findViewById(R.id.helpPopupCloseButton);
        closeButton.setOnClickListener(v -> popupWindow.dismiss());

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);
    }

    private void setUpSpinners() {

        //Get DB helpers
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);

        //Add goal Abilities
        ArrayList<String> abilityStrings = new ArrayList<>();
        for(Ability ability : goal.getAbilities())
            abilityStrings.add(ability.getName());

        ArrayAdapter<String> abilitySpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, abilityStrings);
        abilitySpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        abilitySpinner.setAdapter(abilitySpinnerAdapter);

        //Only Add User Addable Achievements
        ArrayList<String> achievementTypeStrings = new ArrayList<>();
        for(AchievementType achievementsType: achievementsTypeData.getAchievementsTypeList())
            if(achievementsType.isUserAddable())
                achievementTypeStrings.add(achievementsType.getAchievementType());

        ArrayAdapter<String> achievementTypeSpinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, achievementTypeStrings);
        achievementTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        achievementTypeSpinner.setAdapter(achievementTypeSpinnerAdapter);
    }


}

package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.reflections;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

public class ReflectionCardActivity extends FragmentActivity {


    //UI:
    Button goalButton;
    Button taskButton;
    Button backButton;

    TextView titleTextView;

    TextView deadlineTextView;
    TextView completionTextView;
    TextView deadlineReasonTextView;

    TextView achievementTextView;
    TextView abilityTextView;

    TextView blockerTextView;

    TextView successTextView;
    TextView lowSuccessReasonTextView;

    TextView expectationTextView;
    TextView lowExpectationReasonTextView;

    //Tied Reflection
    Reflection reflection;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_reflection_activity);

        //Inject UI:
        injectUI();

        setUpReflectionTextViews();
        setUpReflectionButtons();

    }

    private void injectUI() {
        goalButton = findViewById(R.id.reflectionActivityGoalButton);
        taskButton = findViewById(R.id.reflectionActivityTaskButton);
        backButton = findViewById(R.id.reflectionActivityBackButton);

        titleTextView = findViewById(R.id.reflectionActivityReflectionName);

        deadlineTextView = findViewById(R.id.reflectionActivityDeadlineTextView);
        completionTextView = findViewById(R.id.reflectionActivityGoalCompletionTextView);
        deadlineReasonTextView = findViewById(R.id.reflectionActivityDeadlineReasonTextView);

        achievementTextView = findViewById(R.id.reflectionActivityGreatestAchievementTextView);
        abilityTextView = findViewById(R.id.reflectionActivityBestAbilityTextView);

        blockerTextView = findViewById(R.id.reflectionActivityBlockerTextView);

        successTextView= findViewById(R.id.reflectionActivitySuccessTextView);
        lowSuccessReasonTextView = findViewById(R.id.reflectionActivitySuccessReasonTextView);

        expectationTextView = findViewById(R.id.reflectionActivityExpectationTextView);
        lowExpectationReasonTextView = findViewById(R.id.reflectionActivityExpectationReasonTextView);
    }

    private void setUpReflectionTextViews(){
        //Get information from intent
        this.reflection = (Reflection) getIntent().getSerializableExtra("Reflection");

        //Set Name
        titleTextView.setText(reflection.getGoal().getName()+" Reflection");

        deadlineTextView.setText("Scheduled Date: " + DateConverter.getDateFromUnixTime(reflection.getGoal().getDeadlineUnixDate()/1000));
        completionTextView.setText("Completion Date: " + DateConverter.getDateFromUnixTime(reflection.getGoal().getCompletionUnixDate()/1000));

        String deadlineAdvice = "";
        if(reflection.isDeadlineMet()){
            deadlineAdvice+="What helped meeting the deadline?\n";
            deadlineAdvice+=reflection.getDeadlineReason();
        }
        else {
            deadlineAdvice+="What could help avoiding missing future deadlines?\n";
            deadlineAdvice+=reflection.getDeadlineReason();
        }
        deadlineReasonTextView.setText(deadlineAdvice);


        achievementTextView.setText("Greatest Achievement: " + reflection.getGreatAchievement());
        abilityTextView.setText("Best Ability: " + reflection.getBestAbility());

        if(reflection.getBlocker() != null)
            blockerTextView.setText("Blocker Faced: " + reflection.getBestAbility());
        else
            blockerTextView.setVisibility(View.GONE);

        int successScore = reflection.getSuccessScore();
        String successText = "";
        switch (successScore){
            case 1:  successText = "Not A Success";
                break;
            case 2: successText = "Small Success";
                break;
            case 3: successText = "Notable Success";
                break;
            case 4: successText = "Great Success";
                break;
        }
        successTextView.setText("Goal Success: " + successText);
        if(successScore<=2)
            lowSuccessReasonTextView.setText("Reason You Considered This: " + reflection.getLowSuccessReason());
        else
            lowSuccessReasonTextView.setVisibility(View.GONE);

        int expectationScore = reflection.getSuccessScore();
        String expectationText = "";
        switch (expectationScore){
            case 1: expectationText = "Did Not Align With Expectation";
                break;
            case 2: expectationText = "Somewhat Aligned With Expectation";
                break;
            case 3: expectationText = "Aligned With Expectation";
                break;
            case 4: expectationText ="Exceeded Expectation";
                break;
        }
        expectationTextView.setText("Result Matching Personal Expectation: " + expectationText);
        if(expectationScore==1)
            lowExpectationReasonTextView.setText("Reason for Expectation Mismatch: " + reflection.getLowExpectationReason());
        else
            lowExpectationReasonTextView.setVisibility(View.GONE);

    }


    private void setUpReflectionButtons() {

        goalButton.setOnClickListener(v -> createGoalPopup());

        taskButton.setOnClickListener(v -> createTaskPopup());

        //Set On Click Listener to button
        backButton.setOnClickListener(v -> {
            //Finish viewing activity
            finish();
        });
    }

    private void createGoalPopup(){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(ReflectionCardActivity.this).inflate(R.layout.fragment_reflections_goal_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.reflectionActivityConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 500);

        setUpGoalPopup(container);
    }

    private void setUpGoalPopup(ViewGroup container) {
        //get Goal:
        Goal goal = this.reflection.getGoal();

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
            Chip chip = (Chip) LayoutInflater.from(this)
                    .inflate(R.layout.fragment_abilities_card_improve_chip,abilitiesChipGroup, false);
            chip.setText(ability.getName());
            abilitiesChipGroup.addView(chip);
        }
    }

    private void createTaskPopup(){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(ReflectionCardActivity.this).inflate(R.layout.fragment_reflections_task_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = findViewById(R.id.reflectionActivityConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        //Set up Tasks Recycler View
        RecyclerView tasksRecyclerView = findViewById(R.id.reflectionsActivityTaskRecyclerView);
        tasksRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        ReflectionsTasksCardsAdapter adapter = new ReflectionsTasksCardsAdapter(this,reflection.getGoal().getTasks(),this);
        tasksRecyclerView.setAdapter(adapter);
    }


}

package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.ArrayList;

public class GoalsFragment extends Fragment {

    private GoalsViewModel goalsViewModel;

    private Activity mainActivity;

    private View root;

    private ArrayList<Goal> goalsList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        goalsViewModel =
                new ViewModelProvider(this).get(GoalsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_goals, container, false);

        mainActivity = getActivity();

        this.root = root;

        setUpRecyclerView();

        return root;
    }

    private ArrayList<Goal> loadGoalsFromDatabase() {
        //get Achievement from Usage Database
//        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
//        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);

//        ArrayList<AchievementType> achievementTypes = achievementsTypeData.getAchievementsTypeList();

//        AchievementData achievementData = new AchievementData(databaseHelper,achievementTypes);

        ArrayList<Goal> testGoals = new ArrayList<>();
        //Add Test Data
        for(int i=0;i<7;i++) {
            Goal testGoal = new Goal("TestGoal"+i,"TEST", "Short",System.currentTimeMillis());

            Task testTask1 = new Task("Task1");
            Task testTask2 = new Task("Task2");

            testGoal.addTask(testTask1);
            testGoal.addTask(testTask2);

            Ability testAbility1 = new Ability("Ability1","TEST",100);
            Ability testAbility2 = new Ability("Ability2","TEST", 100);

            testGoal.addAbility(testAbility1);
            testGoal.addAbility(testAbility2);

            AchievementType achievementType = new AchievementType("Ach. Type",200);
            Achievement testAchievement1 = new Achievement("Achievement1", "Test", achievementType,System.currentTimeMillis());
            Achievement testAchievement2 = new Achievement("Achievement2", "Test", achievementType,System.currentTimeMillis());

            testGoal.addAchievement(testAchievement1);
            testGoal.addAchievement(testAchievement2);

            testGoals.add(testGoal);
        }

        return testGoals;
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
        GoalsCardsAdapter adapter = new GoalsCardsAdapter(mainActivity, goalsList,mainActivity,tasksRecyclerView);
        goalsRecyclerView.setAdapter(adapter);
    }

}
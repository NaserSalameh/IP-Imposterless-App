package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    private AchievementsViewModel achievementsViewModel;

    private Activity mainActivity;

    private View root;

    private ArrayList<Achievement> achievementList;

    private TextView achievementScoresTextView;

    private FloatingActionButton addAchievementButton;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementsViewModel =
                new ViewModelProvider(this).get(AchievementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);

        mainActivity = getActivity();

        this.root = root;

        setAchievementsScore();

        setUpRecyclerView();

        setUpAddAchievementButtonOnClickListener();

        return root;
    }

    private void setAchievementsScore() {
        int achievementScores = 0;
        //Get (updated) information List
        achievementList = loadAchievementFromDatabase();

        for(Achievement achievement: achievementList)
            achievementScores+=achievement.getAchievementType().getAchievementScore();

        achievementScoresTextView = root.findViewById(R.id.achievementScoreTextView);
        achievementScoresTextView.setText("Achievement Scores: "+ achievementScores);
    }


    private ArrayList<Achievement> loadAchievementFromDatabase() {
        //get Achievement from Usage Database
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);

        ArrayList<AchievementType> achievementTypes = achievementsTypeData.getAchievementsTypeList();

        AchievementData achievementData = new AchievementData(databaseHelper,achievementTypes);

        //Add Test Data
//        for(int i=0;i<7;i++) {
//            AchievementType testType = achievementTypes.get(i);
//            achievementData.insertNewAchievement(new Achievement("TEST"+i,"JUST TESTING", testType, System.currentTimeMillis()));
//        }

        return achievementData.getAchievementList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(){
        //Get (updated) information List
        achievementList = loadAchievementFromDatabase();

        //get Recycler View:
        RecyclerView achievementRecyclerView = root.findViewById(R.id.achievementsRecyclerView);
        achievementRecyclerView.setLayoutManager(new GridLayoutManager(mainActivity, 3));

        //Set up recycler adapter with information from usage database
        AchievementCardsAdapter adapter = new AchievementCardsAdapter(mainActivity, achievementList,mainActivity,achievementRecyclerView);
        achievementRecyclerView.setAdapter(adapter);
    }


    private void setUpAddAchievementButtonOnClickListener() {
        //Inject UI
        addAchievementButton = root.findViewById(R.id.addAchievementButton);

        addAchievementButton.setOnClickListener(v -> {
            Intent startAddAchievementActivity = new Intent(mainActivity, AchievementAddActivity.class);

            mainActivity.startActivity(startAddAchievementActivity);
        });
    }

    //call when this view resumes (after adding new achievements)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> {
            //what ever you do here will be done after 3 seconds delay.
            setAchievementsScore();
            setUpRecyclerView();
        };
        handler.postDelayed(r, 1000);


        super.onResume();
    }
}
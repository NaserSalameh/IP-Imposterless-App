package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    private AchievementsViewModel achievementsViewModel;

    private Activity mainActivity;

    private View root;

    private ArrayList<Achievement> achievementList;

    private TextView achievementScoresTextView;

    private FloatingActionButton addAchievementButton;

    private DatabaseHelper databaseHelper;
    private LogData logData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementsViewModel =
                new ViewModelProvider(this).get(AchievementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);

        mainActivity = getActivity();

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);

        this.root = root;

        setAchievementsScore();

        setUpRecyclerView();

        setUpFloatingButton();

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
        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);

        ArrayList<AchievementType> achievementTypes = achievementsTypeData.getAchievementsTypeList();

        AchievementData achievementData = new AchievementData(databaseHelper,achievementTypes);

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
            logData.insertNewLog(new Log("Achievement","Added Achievement."));
            Intent startAddAchievementActivity = new Intent(mainActivity, AchievementAddActivity.class);

            mainActivity.startActivity(startAddAchievementActivity);
        });
    }

    private void setUpFloatingButton() {
        ContentData contentData = new ContentData(databaseHelper);
        Content content = contentData.getContentById("ACHIEVEMENT_TAB");

        FloatingActionButton tabGuide = root.findViewById(R.id.achievementsTitleFloatingButton);

        tabGuide.setOnClickListener(v -> {
            createPopup(content.getName(),content.getContent());
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = getActivity().findViewById(R.id.achievementsConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 1000);

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);

        Button helpPopupCloseButton = container.findViewById(R.id.helpPopupCloseButton);
        helpPopupCloseButton.setOnClickListener(v -> popupWindow.dismiss());
    }

    //call when this view resumes (after adding new achievements)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        logData.insertNewLog(new Log("Achievement","Switched To Achievement Tab"));

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

    @Override
    public void onPause() {
        logData.insertNewLog(new Log("Achievement","Left Achievement Tab"));
        super.onPause();
    }
}
package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities;

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
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementAddActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals.GoalAddActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;

public class AbilitiesFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private LogData logData;

    private AbilitiesViewModel abilitiesViewModel;

    private View root;

    private Activity mainActivity;

    private ArrayList<Ability> abilitiesList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        abilitiesViewModel =
                new ViewModelProvider(this).get(AbilitiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_abilities, container, false);

       this.root = root;

        mainActivity = getActivity();

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);

        setUpRecyclerView();
        setUpFloatingButton();
        return root;
    }


    private ArrayList<Ability> loadAbilitiesFromDatabase() {
        //get Abilities from Usage Database
        AbilityData abilityData = new AbilityData(databaseHelper);

        return abilityData.getAbilitiesList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(){
        //Get (updated) information List
        abilitiesList = loadAbilitiesFromDatabase();

        //get Recycler View:
        RecyclerView abilitiesRecyclerView = root.findViewById(R.id.abilitiesRecyclerView);
        abilitiesRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        //Set up recycler adapter with abilities from usage database
        AbilitiesCardsAdapter adapter = new AbilitiesCardsAdapter(mainActivity, abilitiesList,mainActivity,abilitiesRecyclerView);
        abilitiesRecyclerView.setAdapter(adapter);
    }

    private void setUpFloatingButton() {
        ContentData contentData = new ContentData(databaseHelper);
        Content content = contentData.getContentById("ABILITY_TAB");

        FloatingActionButton tabGuide = root.findViewById(R.id.abilitiesTitleFloatingButton);

        tabGuide.setOnClickListener(v -> {
            createPopup(content.getName(),content.getContent());
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = getActivity().findViewById(R.id.abilitiesConstraintLayout);
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

    @Override
    public void onResume() {
        logData.insertNewLog(new Log("Ability","Switched To Ability Tab"));
        super.onResume();
    }

    @Override
    public void onPause() {
        logData.insertNewLog(new Log("Ability","Left Ability Tab"));
        super.onPause();
    }
}

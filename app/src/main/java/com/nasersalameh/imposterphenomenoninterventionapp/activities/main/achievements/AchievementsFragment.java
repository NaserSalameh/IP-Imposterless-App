package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information.InformationCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.InformationData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;

public class AchievementsFragment extends Fragment {

    private AchievementsViewModel achievementsViewModel;

    private Activity mainActivity;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        achievementsViewModel =
                new ViewModelProvider(this).get(AchievementsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_achievements, container, false);

        mainActivity = getActivity();
        setUpRecyclerView(root);
        return root;
    }

    private ArrayList<Achievement> loadAchievementFromDatabase() {
        //get Achievement from Usage Database
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        AchievementData achievementData = new AchievementData(databaseHelper);

        ArrayList<Achievement> testArray = new ArrayList<>();
        AchievementType testType = new AchievementType("testType");
        for(int i=0;i<5;i++) {
            testArray.add(new Achievement("TEST"+i, testType, System.currentTimeMillis()));

        }
        return testArray;
//        return achievementData.getAchievementList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(View root){
        //Get (updated) information List
        ArrayList<Achievement> achievementList = loadAchievementFromDatabase();

        //get Recycler View:
        RecyclerView achievementRecyclerView = root.findViewById(R.id.achievementsRecyclerView);
        achievementRecyclerView.setLayoutManager(new GridLayoutManager(mainActivity, 3));

        //Set up recycler adapter with information from usage database
        AchievementCardsAdapter adapter = new AchievementCardsAdapter(mainActivity, achievementList,mainActivity);
        achievementRecyclerView.setAdapter(adapter);
    }

}
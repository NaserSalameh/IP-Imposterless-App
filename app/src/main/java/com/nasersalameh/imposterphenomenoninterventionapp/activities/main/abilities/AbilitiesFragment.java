package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities;

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
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;

import java.util.ArrayList;

public class AbilitiesFragment extends Fragment {

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

        setUpRecyclerView();

        return root;
    }


    private ArrayList<Ability> loadAbilitiesFromDatabase() {
        //get Abilities from Usage Database
//        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
//        AchievementsTypeData achievementsTypeData = new AchievementsTypeData(databaseHelper);
//        ArrayList<AchievementType> achievementTypes = achievementsTypeData.getAchievementsTypeList();
//        AchievementData achievementData = new AchievementData(databaseHelper,achievementTypes);

        //Add Test Data
        ArrayList<Ability> abilities = new ArrayList<>();
        for(int i=0;i<7;i++) {
            ArrayList<String> improvements = new ArrayList<>();
            improvements.add("Improvement" + i);
            improvements.add("Improvement" + i+2);
            improvements.add("Improvement" + i+3);
            improvements.add("Improvement" + i+4);

            Ability newAbility = new Ability("test"+i,"JUST TEST", i*1000);
            newAbility.setImprovements(improvements);
            abilities.add(newAbility);
        }

        return abilities;
//        return achievementData.getAchievementList();
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



}

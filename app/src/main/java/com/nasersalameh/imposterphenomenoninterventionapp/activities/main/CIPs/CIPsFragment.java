package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

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
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities.AbilitiesCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.reflections.ReflectionsViewModel;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;

import java.util.ArrayList;

public class CIPsFragment extends Fragment {

    private CIPsViewModel cipsViewModel;

    private View root;

    private Activity mainActivity;

    private ArrayList<Ability> abilitiesList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cipsViewModel =
                new ViewModelProvider(this).get(CIPsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cips, container, false);

        this.root = root;

        mainActivity = getActivity();

//        setUpRecyclerView();

        return root;
    }


    private ArrayList<Ability> loadAbilitiesFromDatabase() {
        //get Abilities from Usage Database
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
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



}

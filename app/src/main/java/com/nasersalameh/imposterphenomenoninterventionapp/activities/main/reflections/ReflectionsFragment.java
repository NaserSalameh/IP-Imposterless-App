package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.reflections;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities.AbilitiesCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ReflectionData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

import java.util.ArrayList;

public class ReflectionsFragment extends Fragment {

    private ReflectionsViewModel reflectionsViewModel;

    private View root;

    private Activity mainActivity;

    private ArrayList<Reflection> reflectionsList;

    private DatabaseHelper databaseHelper;
    private LogData logData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        reflectionsViewModel =
                new ViewModelProvider(this).get(ReflectionsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_reflections, container, false);

        mainActivity = getActivity();

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);


        databaseHelper = new DatabaseHelper(mainActivity);

        this.root = root;


        setUpRecyclerView();

        return root;
    }


    private ArrayList<Reflection> loadReflectionsFromDatabase() {
        //get Abilities from Usage Database
        databaseHelper = new DatabaseHelper(mainActivity);
        AbilityData abilityData = new AbilityData(databaseHelper);
        ReflectionData reflectionData = new ReflectionData(databaseHelper,abilityData.getAbilitiesList());

        return reflectionData.getReflectionsList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(){
        //Get (updated) information List
        reflectionsList = loadReflectionsFromDatabase();

        //get Recycler View:
        RecyclerView reflectionsRecyclerView = root.findViewById(R.id.reflectionsRecyclerView);
        reflectionsRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        //Set up recycler adapter with abilities from usage database
        ReflectionsCardsAdapter adapter = new ReflectionsCardsAdapter(mainActivity, reflectionsList,mainActivity);
        reflectionsRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        logData.insertNewLog(new Log("Reflections", "Switched To Reflections Tab."));
        super.onResume();
    }

    @Override
    public void onPause() {
        logData.insertNewLog(new Log("Reflections", "Left Reflections Tab."));
        super.onPause();
    }
}

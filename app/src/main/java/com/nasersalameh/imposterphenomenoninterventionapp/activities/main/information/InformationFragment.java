package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

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
import com.nasersalameh.imposterphenomenoninterventionapp.activities.setup.TailoredPlanCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.InstallDatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;

public class InformationFragment extends Fragment {

    private InformationViewModel informationViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel =
                new ViewModelProvider(this).get(InformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_information, container, false);

        setUpRecyclerView(root);

//        setUpClickListenerOnCards();

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(View root){
        Activity currentActivity = getActivity();

        //get Response from Database
        InstallDatabaseHelper installDatabaseHelper = new InstallDatabaseHelper(currentActivity);
        installDatabaseHelper.createInformationList();
        ArrayList<Information> informationList = installDatabaseHelper.getInformationList();

        //Recycler View:
        RecyclerView informationRecyclerView = root.findViewById(R.id.informationRecyclerView);
        informationRecyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        InformationCardsAdapter adapter = new InformationCardsAdapter(currentActivity, informationList);
        informationRecyclerView.setAdapter(adapter);
    }

}
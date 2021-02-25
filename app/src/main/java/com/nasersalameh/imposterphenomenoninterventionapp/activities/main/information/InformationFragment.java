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
import com.nasersalameh.imposterphenomenoninterventionapp.database.CIPsResponseData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;

public class InformationFragment extends Fragment {

    private InformationViewModel informationViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel =
                new ViewModelProvider(this).get(InformationViewModel.class);
        View root = inflater.inflate(R.layout.fragment_information, container, false);

        setUpRecyclerView(root);

        return root;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(View root){
        Activity currentActivity = getActivity();
        DatabaseHelper dbHelper = new DatabaseHelper(currentActivity);

        //get Response from Database
        CIPsResponseData cipsResponseData = new CIPsResponseData(dbHelper, dbHelper.getDatabase());
        CIPsResponse response = cipsResponseData.getSetupResponse();
        response.calculateTailoredPlan();

        //Recycler View:
        RecyclerView planRecyclerView = root.findViewById(R.id.profileRecycleView);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        TailoredPlanCardsAdapter adapter = new TailoredPlanCardsAdapter(currentActivity, response.getTailoredPlan());
        planRecyclerView.setAdapter(adapter);
    }

}
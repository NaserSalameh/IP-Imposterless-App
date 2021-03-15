package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

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
import com.nasersalameh.imposterphenomenoninterventionapp.database.CIPsResponseData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ReflectionData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

import java.util.ArrayList;

public class CIPsFragment extends Fragment {

    private CIPsViewModel cipsViewModel;

    private View root;

    private Activity mainActivity;

    private ArrayList<CIPsResponse> responsesList;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cipsViewModel =
                new ViewModelProvider(this).get(CIPsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cips, container, false);

        this.root = root;

        mainActivity = getActivity();

        setUpRecyclerView();

        return root;
    }


    private ArrayList<CIPsResponse> loadResponsesFromDatabase() {
        //get responses from Usage Database
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        CIPsResponseData cipsResponseData = new CIPsResponseData(databaseHelper);

        return cipsResponseData.getResponsesList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(){
        //Get (updated) information List
        responsesList = loadResponsesFromDatabase();

        //get Recycler View:
        RecyclerView cipsRecyclerView = root.findViewById(R.id.cipsRecyclerView);
        cipsRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        //Set up recycler adapter with abilities from usage database
        CIPsCardsAdapter adapter = new CIPsCardsAdapter(mainActivity, responsesList,mainActivity);
        cipsRecyclerView.setAdapter(adapter);
    }



}

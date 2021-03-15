package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.CIPsResponseData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;

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

        setUpFloatingButton();
        setUpRecyclerView();

        return root;
    }

    private void setUpFloatingButton() {

        FloatingActionButton addResponseButton = root.findViewById(R.id.addCipsFloatingButton);
        addResponseButton.setOnClickListener(v -> {
            Intent startAddCIPsActionIntent = new Intent(mainActivity, CIPsAddActivity.class);

            mainActivity.startActivity(startAddCIPsActionIntent);
        });

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

    //call when this view resumes (after adding new CIPS)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> setUpRecyclerView();
        handler.postDelayed(r, 1000);


        super.onResume();
    }

}

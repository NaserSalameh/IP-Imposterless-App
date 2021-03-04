package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

import android.app.Activity;
import android.icu.text.IDNA;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.MainActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.setup.TailoredPlanCardsAdapter;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.InformationData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.InstallDatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;

public class InformationFragment extends Fragment {

    private InformationViewModel informationViewModel;
    private View root;
    private Activity mainActivity;


    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        informationViewModel =
                new ViewModelProvider(this).get(InformationViewModel.class);
        this.root = inflater.inflate(R.layout.fragment_information, container, false);

        //set Main activity
        mainActivity = getActivity();

        setUpRecyclerView(root);

        return root;
    }

    private ArrayList<Information> loadInformationFromDatabase() {
        //get information from Usage Database
        DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
        InformationData informationData = new InformationData(databaseHelper);

        return informationData.getInformationList();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(View root){
        //Get (updated) information List
        ArrayList<Information> informationList = loadInformationFromDatabase();

        //get Recycler View:
        RecyclerView informationRecyclerView = root.findViewById(R.id.informationRecyclerView);
        informationRecyclerView.setLayoutManager(new LinearLayoutManager(mainActivity));

        //Set up recycler adapter with information from usage database
        InformationCardsAdapter adapter = new InformationCardsAdapter(mainActivity, informationList,mainActivity);
        informationRecyclerView.setAdapter(adapter);
    }

    //On return to Information Fragment, rebuild recycler view
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> {
            //what ever you do here will be done after 3 seconds delay.
            setUpRecyclerView(root);
        };
        handler.postDelayed(r, 1000);

        super.onResume();
    }



}
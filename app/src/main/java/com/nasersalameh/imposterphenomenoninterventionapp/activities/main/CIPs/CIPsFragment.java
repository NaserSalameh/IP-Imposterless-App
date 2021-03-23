package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

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
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.CIPsResponseData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;

public class CIPsFragment extends Fragment {

    private CIPsViewModel cipsViewModel;

    private View root;

    private Activity mainActivity;

    private ArrayList<CIPsResponse> responsesList;

    private DatabaseHelper databaseHelper;
    private LogData logData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        cipsViewModel =
                new ViewModelProvider(this).get(CIPsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_cips, container, false);

        this.root = root;

        mainActivity = getActivity();

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);

        setUpFloatingButton();
        setUpRecyclerView();
        setUpTitleFloatingButton();

        return root;
    }

    private void setUpFloatingButton() {

        Button addResponseButton = root.findViewById(R.id.addCipsButton);
        addResponseButton.setOnClickListener(v -> {
            logData.insertNewLog(new Log("CIPs", "Started New CIPs."));
            Intent startAddCIPsActionIntent = new Intent(mainActivity, CIPsAddActivity.class);

            mainActivity.startActivity(startAddCIPsActionIntent);
        });

    }

    private ArrayList<CIPsResponse> loadResponsesFromDatabase() {
        //get responses from Usage Database
        databaseHelper = new DatabaseHelper(mainActivity);
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

    private void setUpTitleFloatingButton() {
        ContentData contentData = new ContentData(databaseHelper);
        Content content = contentData.getContentById("CIPS_TAB");

        FloatingActionButton tabGuide = root.findViewById(R.id.cipsTitleFloatingButton);

        tabGuide.setOnClickListener(v -> {
            createPopup(content.getName(),content.getContent());
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(getContext()).inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = getActivity().findViewById(R.id.cipsConstraintLayout);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> popupWindow.showAtLocation(constraintLayout, Gravity.CENTER, 100, 100);
        handler.postDelayed(r, 1000);

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);

        Button helpPopupCloseButton = container.findViewById(R.id.helpPopupCloseButton);
        helpPopupCloseButton.setOnClickListener(v -> popupWindow.dismiss());
    }

    //call when this view resumes (after adding new CIPS)
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onResume() {
        logData.insertNewLog(new Log("CIPs", "Switched to CIPs Tab."));

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r= () -> setUpRecyclerView();
        handler.postDelayed(r, 1000);

        super.onResume();
    }

    @Override
    public void onPause() {
        logData.insertNewLog(new Log("CIPs", "Left CIPs Tab."));

        super.onPause();
    }
}

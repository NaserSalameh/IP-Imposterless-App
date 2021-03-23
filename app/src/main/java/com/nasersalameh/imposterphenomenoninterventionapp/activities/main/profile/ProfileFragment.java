package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.profile;

import android.app.Activity;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.UserData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;
import com.nasersalameh.imposterphenomenoninterventionapp.models.User;

import java.io.File;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    private DatabaseHelper dbHelper;
    private LogData logData;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        dbHelper = new DatabaseHelper(getActivity());
        logData = new LogData(dbHelper);

        setupProfileUI(root);
        setUpRecyclerView(root);

        return root;
    }

    private void setupProfileUI(View root){
        Activity currentActivity = getActivity();

        //Get User from Database
        UserData userData = new UserData(dbHelper);
        User user = userData.getUser();

        //Set User name:
        TextView profileTextView = root.findViewById(R.id.profileTextView);
        profileTextView.setText(user.getUserName());

        //Set User Image
        ImageView profileImageView = root.findViewById(R.id.profileImageView);

        //If no picture, set default
        if(user.getImagePath().equals("NA"))
            profileImageView.setImageDrawable(getResources().getDrawable(R.drawable.ic_launcher_foreground));
        else
            profileImageView.setImageURI(Uri.fromFile(new File(user.getImagePath()+"/profile.jpg")));

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setUpRecyclerView(View root){
        Activity currentActivity = getActivity();
        DatabaseHelper dbHelper = new DatabaseHelper(currentActivity);

        //get Response from Database
        CIPsResponseData cipsResponseData = new CIPsResponseData(dbHelper);
        CIPsResponse response = cipsResponseData.getSetupResponse();
        response.calculateTailoredPlan();

        //Recycler View:
        RecyclerView planRecyclerView = root.findViewById(R.id.profileRecycleView);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(currentActivity));
        View anchor = root.findViewById(R.id.profileConstraintLayout);
        TailoredPlanCardsAdapter adapter = new TailoredPlanCardsAdapter(currentActivity, response.getTailoredPlan(), anchor);
        planRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        logData.insertNewLog(new Log("Profile", "Switched to Profile Tab."));
        super.onResume();
    }

    @Override
    public void onPause() {
        logData.insertNewLog(new Log("Profile", "Left Profile Tab."));
        super.onPause();
    }
}
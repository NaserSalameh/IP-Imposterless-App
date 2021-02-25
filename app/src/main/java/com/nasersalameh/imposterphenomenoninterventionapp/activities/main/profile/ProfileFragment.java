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
import com.nasersalameh.imposterphenomenoninterventionapp.database.UserData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.User;

import java.io.File;


public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        setupProfileUI(root);
        setUpRecyclerView(root);

        return root;
    }

    private void setupProfileUI(View root){
        Activity currentActivity = getActivity();
        DatabaseHelper dbHelper = new DatabaseHelper(currentActivity);

        //Get User from Database
        UserData userData = new UserData(dbHelper, dbHelper.getDatabase());
        User user = userData.getUser();

        //Set User name:
        TextView profileTextView = root.findViewById(R.id.profileTextView);
        profileTextView.setText(user.getUserName());

        //Set User Image
        ImageView profileImageView = root.findViewById(R.id.profileImageView);
        profileImageView.setImageURI(Uri.fromFile(new File(user.getImagePath()+"/profile.jpg")));

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
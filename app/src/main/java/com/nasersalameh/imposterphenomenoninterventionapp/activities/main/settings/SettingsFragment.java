package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.settings;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.profile.ProfileViewModel;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;

public class SettingsFragment extends Fragment {

    SettingsViewModel settingsViewModel;

    private Button deleteDB;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingsViewModel =
                new ViewModelProvider(this).get(SettingsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_settings, container, false);

        setUpDeleteDB(root);

        return root;
    }

    private void setUpDeleteDB(View root) {
        deleteDB = root.findViewById(R.id.settingsDeleteDBButton);

        DatabaseHelper databaseHelper = new DatabaseHelper(getContext());

        deleteDB.setOnClickListener(v -> {
            databaseHelper.deleteUsageDB(getContext());
            //Close App
            getActivity().finish();
        });
    }


}

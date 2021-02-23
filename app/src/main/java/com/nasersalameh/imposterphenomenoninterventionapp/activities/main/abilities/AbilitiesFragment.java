package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.nasersalameh.imposterphenomenoninterventionapp.R;

public class AbilitiesFragment extends Fragment {

    private AbilitiesViewModel abilitiesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        abilitiesViewModel =
                new ViewModelProvider(this).get(AbilitiesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_abilities, container, false);
        final TextView textView = root.findViewById(R.id.text_ability);
        abilitiesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}

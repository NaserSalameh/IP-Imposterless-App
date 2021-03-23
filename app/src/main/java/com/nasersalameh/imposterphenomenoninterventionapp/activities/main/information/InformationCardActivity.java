package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.InformationData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

public class InformationCardActivity extends FragmentActivity {

    //UI:
    ScrollView informationScrollView;

    TextView informationNameTextView;
    TextView informationCorpusTextView;

    Button informationActivityButton;

    ProgressBar informationActivityProgressBar;

    //Tied information
    Information information;

    //Progress to keep
    int progress;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_information_activity);

        //Inject UI:
        informationScrollView = findViewById(R.id.informationActivityScrollView);

        informationCorpusTextView = findViewById(R.id.informationCorpusTextView);

        informationNameTextView = findViewById(R.id.informationActivityNameTextView);

        informationActivityButton = findViewById(R.id.informationActivityButton);

        informationActivityProgressBar = findViewById(R.id.informationProgressBar);

        setUpInformationActivity();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setUpInformationActivity(){
        //Get information from intent
        this.information = (Information) getIntent().getSerializableExtra("Information");

        //Set Name
        informationNameTextView.setText(information.getInformationName());

        //Set corpus
        informationCorpusTextView.setText(information.getInformationCorpus());

        //Get screen height
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        double height = displayMetrics.heightPixels;

        //Set progress tracking on scroll view
        informationScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int currentScrollDepth = scrollY;
            int maxScrollDepth = informationScrollView.getMaxScrollAmount();

            progress = (int) ((double) currentScrollDepth / (double) maxScrollDepth * 100.0);

            informationActivityProgressBar.setProgress(progress);
        });

        //Set On Click Listener to button
        informationActivityButton.setOnClickListener(v -> {
            //set The information's final progress
            progress = 100;
            information.setProgress(progress);

            //Modify Information Entry
            updateInformationProgress(information);

            finish();
        });
    }

    private void updateInformationProgress(Information information){
        DatabaseHelper databaseHelper = new DatabaseHelper(getApplicationContext());
        InformationData informationData = new InformationData(databaseHelper);
        informationData.updateInformationProgress(information.getInformationName(), information.getProgress());

    }

    //In case of destruction, send back progress
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //set The information's final progress
        information.setProgress(progress);

        //Modify Information Entry
        updateInformationProgress(information);

        finish();
    }
}

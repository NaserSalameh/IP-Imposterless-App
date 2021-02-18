package com.nasersalameh.imposterphenomenoninterventionapp.activities.setup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.nasersalameh.imposterphenomenoninterventionapp.Adapter;
import com.nasersalameh.imposterphenomenoninterventionapp.R;

import java.util.ArrayList;

public class SetupActivity extends AppCompatActivity {

    //UI:
    //Personal Page Button
    private Button personalButton;
    //Information Page Button
    private Button informationButton;
    //CIPs Button
    private Button cipsButton;

    //nameTextBox
    private EditText nameTextBox;

    //Image

    //CIPs response Sliders
    private RangeSlider rangeSlider1;
    private RangeSlider rangeSlider2;
    private RangeSlider rangeSlider3;
    private RangeSlider rangeSlider4;

    //Progress Bar
    private ProgressBar progressBar;

    private int progress;
    public static final int COMPLETE_PROGRESS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_personal);

        //Progress Bar progress
        progress = 0;

        //Set UI Elements:
        personalButton = findViewById(R.id.setupPersonalButton);

        nameTextBox = findViewById(R.id.nameTextBox);

        progressBar=findViewById(R.id.setupProgressBar);

        personalButton.setOnClickListener(v -> {
            //DO: Save Need Info to a Model
            transitionToSetupInformation();
            progressBar.setProgress(progress+=10);
        });

    }

    private void transitionToSetupInformation() {
        setContentView(R.layout.activity_setup_information);

        informationButton = findViewById(R.id.setupInformationButton);
        progressBar=findViewById(R.id.setupProgressBar);

        informationButton.setOnClickListener(v -> {
            transitionToSetupCIPs();
            progressBar.setProgress(progress+=10);
        });
    }


    private void transitionToSetupCIPs() {
        setContentView(R.layout.activity_setup_cips);

        //Setup UI:
        rangeSlider1=findViewById(R.id.rangeSlider1);
        rangeSlider2=findViewById(R.id.rangeSlider2);
        rangeSlider3=findViewById(R.id.rangeSlider3);
        rangeSlider4=findViewById(R.id.rangeSlider4);

        //Set RangeSliders Label Formatting
        setLabelFormatters();

        progressBar=findViewById(R.id.setupProgressBar);

        cipsButton = findViewById(R.id.setupCipsButton);

        cipsButton.setOnClickListener(v -> {
            if(progress == COMPLETE_PROGRESS)
                //Do:Collect Responses
                wrapUpSetUp();
            else{
                //DO: Collect Responses
                refreshSetupCips();
            }
        });

    }

    private void setLabelFormatters() {
        LabelFormatter labelFormatter = value -> {
            switch ((int) value){
                case 1: return "Not True At All";
                case 2: return "Rarely";
                case 3: return "Sometimes";
                case 4: return "Often";
                case 5: return "Very True";
            }
            return "FAILURE";
        };
        rangeSlider1.setLabelFormatter(labelFormatter);
        rangeSlider2.setLabelFormatter(labelFormatter);
        rangeSlider3.setLabelFormatter(labelFormatter);
        rangeSlider4.setLabelFormatter(labelFormatter);

    }

    private void refreshSetupCips() {
        //reset rangeSliders
        rangeSlider1.setValues(1f);
        rangeSlider2.setValues(1f);
        rangeSlider3.setValues(1f);
        rangeSlider4.setValues(1f);

        progressBar.setProgress(progress+=16);
    }

    private void wrapUpSetUp() {
        //Transition to Next Screen
    }

}
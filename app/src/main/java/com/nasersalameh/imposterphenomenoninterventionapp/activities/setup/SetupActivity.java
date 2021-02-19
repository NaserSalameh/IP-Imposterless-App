package com.nasersalameh.imposterphenomenoninterventionapp.activities.setup;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.data.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.data.DatabaseHelper;

public class SetupActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;

    //To hold data to be inserted into DB
    private String userName;
    private CIPsResponse response;

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

    private TextView questionView1;
    private TextView questionView2;
    private TextView questionView3;
    private TextView questionView4;


    //Progress Bar
    private ProgressBar progressBar;

    private int progress;
    public static final int COMPLETE_PROGRESS = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_personal);

        dbHelper = new DatabaseHelper(SetupActivity.this);
        response = new CIPsResponse(System.currentTimeMillis()/1000L);
        //Progress Bar progress
        progress = 0;

        //Set UI Elements:
        personalButton = findViewById(R.id.setupPersonalButton);

        nameTextBox = findViewById(R.id.nameTextBox);

        progressBar=findViewById(R.id.setupProgressBar);

        personalButton.setOnClickListener(v -> {
            //Save User Name
            userName = nameTextBox.getText().toString();
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
        rangeSlider1 = findViewById(R.id.rangeSlider1);
        rangeSlider2 = findViewById(R.id.rangeSlider2);
        rangeSlider3 = findViewById(R.id.rangeSlider3);
        rangeSlider4 = findViewById(R.id.rangeSlider4);

        questionView1 = findViewById(R.id.questionText1);
        questionView2 = findViewById(R.id.questionText2);
        questionView3 = findViewById(R.id.questionText3);
        questionView4 = findViewById(R.id.questionText4);

        //Set RangeSliders Label Formatting
        setLabelFormatters();

        progressBar = findViewById(R.id.setupProgressBar);

        cipsButton = findViewById(R.id.setupCipsButton);

        //Fill textViews with questions
        populateQuestions();

        cipsButton.setOnClickListener(v -> {
            if(progress == COMPLETE_PROGRESS) {
                collectResponses();
                wrapUpSetUp();
            }
            else{
                collectResponses();
                moveAheadCIPsSetup();
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

    private void populateQuestions(){
        //range of questionID to start from
        int questionIDStart = ((progress-20)/16)*4;
        questionView1.setText(response.getCIPsQuestionString(questionIDStart));
        questionView2.setText(response.getCIPsQuestionString(questionIDStart+1));
        questionView3.setText(response.getCIPsQuestionString(questionIDStart+2));
        questionView4.setText(response.getCIPsQuestionString(questionIDStart+3));
    }

    private void collectResponses(){
        //range of questionID to start from
        int questionIDStart = ((progress-20)/16)*4;

        //fill appropriate responses
        response.addResponse(questionIDStart, Math.round(rangeSlider1.getValues().get(0)));
        response.addResponse(questionIDStart+1, Math.round(rangeSlider2.getValues().get(0)));
        response.addResponse(questionIDStart+2, Math.round(rangeSlider3.getValues().get(0)));
        response.addResponse(questionIDStart+3, Math.round(rangeSlider4.getValues().get(0)));

    }

    private void moveAheadCIPsSetup() {
        //reset rangeSliders
        rangeSlider1.setValues(1f);
        rangeSlider2.setValues(1f);
        rangeSlider3.setValues(1f);
        rangeSlider4.setValues(1f);

        progressBar.setProgress(progress+=16);

        populateQuestions();
    }

    private void wrapUpSetUp() {
        //Insert Responses Into DB
        dbHelper.insertUser(userName);
        dbHelper.insertCIPsResponse(response);
        //Transition to Next Screen
    }

}
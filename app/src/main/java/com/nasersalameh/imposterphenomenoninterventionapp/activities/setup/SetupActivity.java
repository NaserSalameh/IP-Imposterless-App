package com.nasersalameh.imposterphenomenoninterventionapp.activities.setup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.data.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.data.DatabaseHelper;

public class SetupActivity extends AppCompatActivity {

    public static final int IMAGE_REQUEST_CODE = 1000;
    private DatabaseHelper dbHelper;

    //To hold data to be inserted into DB
    private String userName;
    private Uri imageURI;
    private CIPsResponse response;

    //UI:
    //ScrollView
    private ScrollView scrollView;

    //Personal Page Button
    private Button personalButton;
    //Information Page Button
    private Button informationButton;
    //CIPs Questions page Button
    private Button cipsButton;
    //results page button
    private Button resultsButtons;
    //tailored plan page button
    private Button planButton;

    //nameTextBox
    private EditText nameTextBox;

    //Image
    private ImageView profileImage;

    //CIPs response Sliders
    private RangeSlider rangeSlider1;
    private RangeSlider rangeSlider2;
    private RangeSlider rangeSlider3;
    private RangeSlider rangeSlider4;

    //CIPs Questions Text View
    private TextView questionView1;
    private TextView questionView2;
    private TextView questionView3;
    private TextView questionView4;

    //Results Text View
    private TextView cipsScoreView;
    private TextView cipsResultview;

    private TextView abilityScoreView;
    private TextView achievementScoreView;
    private TextView perfectionismScoreView;

    //Progress Bars
    //CIPs Questions progressBar
    private ProgressBar progressBar;
    //Ability Scale Bar
    private ProgressBar abilityScaleBar;
    //Achievement Scale Bar
    private ProgressBar achievementScaleBar;
    //Perfectionism Scale Bar
    private ProgressBar perfectionismScaleBar;

    private int progress;
    public static final int COMPLETE_PROGRESS = 84;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_personal);

        dbHelper = new DatabaseHelper(SetupActivity.this);
        response = new CIPsResponse("FULL");
        //Progress Bar progress
        progress = 0;

        //Set UI Elements:
        personalButton = findViewById(R.id.setupPersonalButton);

        nameTextBox = findViewById(R.id.nameTextBox);

        profileImage = findViewById(R.id.profileImage);

        progressBar=findViewById(R.id.setupProgressBar);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //open gallery
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, IMAGE_REQUEST_CODE);
            }
        });

        personalButton.setOnClickListener(v -> {
            //Save User Name
            userName = nameTextBox.getText().toString();
            progressBar.setProgress(progress+=10);
            transitionToSetupInformation();
        });

    }

    //Override activity result from image upload intent
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //verify requestCode match
        if(requestCode == IMAGE_REQUEST_CODE){
            //Verify image was correctly chosen
            if(resultCode == Activity.RESULT_OK){
                //Save Image URI
                imageURI = data.getData();
                //Set user Image
                profileImage.setImageURI(imageURI);
            }
        }
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
        scrollView = findViewById(R.id.scrollView);

        rangeSlider1 = findViewById(R.id.rangeSlider1);
        rangeSlider2 = findViewById(R.id.rangeSlider2);
        rangeSlider3 = findViewById(R.id.rangeSlider3);
        rangeSlider4 = findViewById(R.id.rangeSlider4);

        rangeSlider1.setValues(3f);
        rangeSlider2.setValues(3f);
        rangeSlider3.setValues(3f);
        rangeSlider4.setValues(3f);

        questionView1 = findViewById(R.id.questionText1);
        questionView2 = findViewById(R.id.questionText2);
        questionView3 = findViewById(R.id.questionText3);
        questionView4 = findViewById(R.id.questionText4);

        //Set RangeSliders Label Formatting
        setLabelFormatters();

        progressBar = findViewById(R.id.setupProgressBar);

        //Fill textViews with questions
        populateQuestions();

        cipsButton = findViewById(R.id.setupCipsButton);

        cipsButton.setOnClickListener(v -> {
            System.out.println(progress);
            //If all responses collected
            if(progress == COMPLETE_PROGRESS) {
                collectResponses();
                wrapUpSetUp();
                transitionToSetupResults();
            }
            else{
                //If last page
                if(progress == COMPLETE_PROGRESS - 16){
                    cipsButton.setText("Show Results");
                }
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
        //reset to top of scrollView
        scrollView.scrollTo(0,0);

        //reset rangeSliders
        rangeSlider1.setValues(3f);
        rangeSlider2.setValues(3f);
        rangeSlider3.setValues(3f);
        rangeSlider4.setValues(3f);

        progressBar.setProgress(progress+=16);

        populateQuestions();
    }

    private void wrapUpSetUp() {
        //calculate various CIPs Scores
        response.calculateScoreValues();

        //Insert Responses Into DB
        //Uncomment After Testing
//        dbHelper.insertUser(userName,imageURI);
//        dbHelper.insertCIPsResponse(response);
    }

    private void transitionToSetupResults() {
        setContentView(R.layout.activity_setup_results);

        //Setup UI:
        //Results Text Views:
        cipsScoreView = findViewById(R.id.cipsScoreTextView);
        cipsResultview = findViewById(R.id.cipsResultTextView);

        cipsScoreView.setText("Clance IP Score: " + response.getCipsScore()+"/100.");
        cipsResultview.setText("Clance IP Result: " + response.getCipsResult());

        //Scores Text Views:
        abilityScoreView = findViewById(R.id.abilityScoreTextView);
        achievementScoreView = findViewById(R.id.achievementScoreTextView);
        perfectionismScoreView = findViewById(R.id.perfectionismScoreTextView);

        abilityScoreView.setText(response.getAbilityScore()+"/15");
        achievementScoreView.setText(response.getAchievementScore()+"/15");
        perfectionismScoreView.setText(response.getPerfectionismScore()+"/15");

        //Scale Bars:
        abilityScaleBar = findViewById(R.id.abilityScaleBar);
        achievementScaleBar = findViewById(R.id.achievementScaleBar);
        perfectionismScaleBar = findViewById(R.id.perfectionismScaleBar);

        abilityScaleBar.setMax(15);
        abilityScaleBar.setProgress(response.getAbilityScore());
        achievementScaleBar.setMax(15);
        achievementScaleBar.setProgress(response.getAchievementScore());
        perfectionismScaleBar.setMax(15);
        perfectionismScaleBar.setProgress(response.getPerfectionismScore());

        //Results Button:
        resultsButtons = findViewById(R.id.setupResultsButton);

        resultsButtons.setOnClickListener(v -> {
           //End activity and start main page activity
        });


    }


}
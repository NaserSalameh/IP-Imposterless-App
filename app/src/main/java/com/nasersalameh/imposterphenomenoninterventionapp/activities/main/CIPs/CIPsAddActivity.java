package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.FragmentActivity;

import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.setup.SetupActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.database.CIPsQuestionData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.CIPsResponseData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.UserData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;

public class CIPsAddActivity extends FragmentActivity {

    private DatabaseHelper dbHelper;

    private CIPsQuestionData cipsQuestionData;

    private CIPsResponse response;

    //UI:
    //ScrollView
    private ScrollView scrollView;

    //CIPs Questions page Button
    private Button cipsButton;

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

    //CIPs Questions progressBar
    private ProgressBar progressBar;

    private int progress;
    public static final int COMPLETE_PROGRESS = 80;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_cips);

        dbHelper = new DatabaseHelper(CIPsAddActivity.this);
        response = new CIPsResponse("FULL");
        //Progress Bar progress
        progress = 0;

        //Prep CIPsQuestionsData to get Questions Mapping
        cipsQuestionData = new CIPsQuestionData(dbHelper);

        //Inject UI
        scrollView = findViewById(R.id.cipsScrollView);

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
            //If all responses collected
            if(progress == COMPLETE_PROGRESS) {
                collectResponses();
                saveSetupResults();

                //Handler to thread sleep and slow down process
                Handler handler=new Handler();
                //Finish activity
                Runnable r= () -> finish();
                handler.postDelayed(r, 750);
            }
            else{
                //If last page
                if(progress == COMPLETE_PROGRESS - 20){
                    cipsButton.setText("Save Results!");
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
        int questionIDStart = ((progress)/20)*4;

        questionView1.setText(cipsQuestionData.getCipsIDQuestionsMapping().get(questionIDStart));
        questionView2.setText(cipsQuestionData.getCipsIDQuestionsMapping().get(questionIDStart+1));
        questionView3.setText(cipsQuestionData.getCipsIDQuestionsMapping().get(questionIDStart+2));
        questionView4.setText(cipsQuestionData.getCipsIDQuestionsMapping().get(questionIDStart+3));
    }

    private void collectResponses(){
        //range of questionID to start from
        int questionIDStart = ((progress)/20)*4;

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

        progressBar.setProgress(progress+=20);

        populateQuestions();
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void saveSetupResults() {
        //calculate various CIPs Scores
        response.calculateScoreValues();

        //Insert Responses Into DB
        CIPsResponseData cipsResponseData = new CIPsResponseData(dbHelper);
        cipsResponseData.insertSetupCIPsResponse(response);
        dbHelper.closeDB();
    }
}

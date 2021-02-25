package com.nasersalameh.imposterphenomenoninterventionapp.models;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CIPsResponse {

    //Time in UNIX timestamp
    private Long responseDate;
    private HashMap<Integer,Integer> cipsIDResponseMapping;

    //This will allow for partial responses, and smaller quizzes to use to keep track of User's Growth
    //Response collected could be:
    //Full: entails a Full CIPs Response
    //Ability: Collecting responses to only calculate an Ability Score
    //Achievement: Collecting responses to only calculate an Achievement Score
    //Perfectionism: Collecting responses to only calculate an Perfectionism Score
    private String responsesCollected;

    //ArrayLists to hold on Question IDs of CIPs Subcats:
    private ArrayList<Integer> abilityIDs;
    private ArrayList<Integer> achievementIDs;
    private ArrayList<Integer> perfectionismIDs;

    //Calculated Values
    private int cipsScore;
    private int abilityScore;
    private int achievementScore;
    private int perfectionismScore;

    //Possible results based on CIPsScores:
    //4 Possible results, based on CIPsScore:
    //0-40: Rarely Experiences IP Characteristics
    //41-60: Moderately Experiences IP Characteristics
    //61-80: Frequently Experiences IP Characteristics
    //81-100: Often and Intensely Experiences IP Characteristics
    private ArrayList<String> possibleCipsResults;
    private String cipsResult;
    //Tailored plan is simply the scores sorted
    //as to create an intervention plan tailored for the user
    //by order of detected behaviours intensity
    private ArrayList<String> tailoredPlan;

    //Constructor if we already have collected the answers in one-go
    public CIPsResponse(HashMap<Integer,Integer> responses, String responsesCollected){
        this.responseDate = System.currentTimeMillis();

        this.responsesCollected = responsesCollected;

        //map responses to IDs:
        cipsIDResponseMapping = new HashMap<>();
        for(int i=0;i<responses.size();i++)
            cipsIDResponseMapping.put(i,responses.get(i));

        //populate Questions Text
        populateQuestions();
        populateResults();
    }

    //Constructor to add the answers iteratively
    public CIPsResponse(String responsesCollected){
        this.responseDate = System.currentTimeMillis();

        cipsIDResponseMapping = new HashMap<>();

        this.responsesCollected = responsesCollected;

        //populate Questions Text
        populateQuestions();
        populateResults();
    }

    private void populateQuestions(){
        //populate Ability Subcat ID:
        abilityIDs = new ArrayList<>();
        abilityIDs.add(0);
        abilityIDs.add(16);
        abilityIDs.add(17);

        //populate Achievement Subcat ID:
        achievementIDs = new ArrayList<>();
        achievementIDs.add(9);
        achievementIDs.add(11);
        achievementIDs.add(15);

        //populate Perfectionism Subcat ID:
        perfectionismIDs = new ArrayList<>();
        perfectionismIDs.add(6);
        perfectionismIDs.add(7);
        perfectionismIDs.add(19);

    }

    private void populateResults(){
        possibleCipsResults = new ArrayList<>();
        possibleCipsResults.add("Rarely Experiences IP Characteristics");
        possibleCipsResults.add("Moderately Experiences IP Characteristics");
        possibleCipsResults.add("Frequently Experiences IP Characteristics");
        possibleCipsResults.add("Often and Intensely Experiences IP Characteristics");
    }

    //will add response and overwrite if already existent
    public void addResponse(int questionID, int response){
        cipsIDResponseMapping.put(questionID,response);
    }


    public Integer getCIPsResponse(Integer i){
        return cipsIDResponseMapping.get(i);
    }

    public HashMap<Integer, Integer> getCIPsIDResponseMap(){
        return cipsIDResponseMapping;
    }

    public Long getResponseDate() {
        return responseDate;
    }



    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculateScoreValues(){
        calculateCIPsScore();
        switch (responsesCollected){
            case "FULL":
                calculateAbilityScore();
                calculateAchievementScore();
                calculatePerfectionismScore();
                calculateTailoredPlan();
                break;
            case "ABILITY":
                calculateAbilityScore();
                break;
            case "ACHIEVEMENT":
                calculateAchievementScore();
                break;
            case "PERFECTIONISM":
                calculatePerfectionismScore();
                break;
        }
    }

    private int calculateCIPsScore(){
        int cipsTotal = 0;
        for(Map.Entry entry: cipsIDResponseMapping.entrySet())
            cipsTotal+= (int) entry.getValue();

        this.cipsScore = cipsTotal;
        //set CipsResult
        calculateCipsResult();
        return cipsTotal;
    }

    public int getCipsScore() {
        return cipsScore;
    }

    public void calculateCipsResult(){
        String cipsResult = "";
        if(cipsScore > 0 && cipsScore <= 40)
            cipsResult = possibleCipsResults.get(0);
        else if(cipsScore > 40 && cipsScore <= 60)
            cipsResult = possibleCipsResults.get(1);
        else if((cipsScore > 60 && cipsScore <= 80))
            cipsResult = possibleCipsResults.get(2);
        else if (cipsScore > 80)
            cipsResult = possibleCipsResults.get(3);

        this.cipsResult = cipsResult;
    }

    public String getCipsResult() {
        return cipsResult;
    }

    private int calculateAbilityScore(){
        int abilityScore = 0;
        for(Integer abilityID: abilityIDs)
            abilityScore += cipsIDResponseMapping.get(abilityID);

        this.abilityScore = abilityScore;
        return abilityScore;
    }

    private int calculateAchievementScore(){
        int achievementScore = 0;
        for(Integer achievementID: achievementIDs)
            achievementScore += cipsIDResponseMapping.get(achievementID);

        this.achievementScore = achievementScore;
        return achievementScore;
    }

    private int calculatePerfectionismScore(){
        int perfectionismScore = 0;
        for(Integer perfectionismID: perfectionismIDs)
            perfectionismScore += cipsIDResponseMapping.get(perfectionismID);

        this.perfectionismScore = perfectionismScore;
        return perfectionismScore;
    }

    public int getAbilityScore() {
        return abilityScore;
    }

    public int getAchievementScore() {
        return achievementScore;
    }

    public int getPerfectionismScore() {
        return perfectionismScore;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void calculateTailoredPlan(){

        tailoredPlan = new ArrayList<>();

        //HashMap to Sort the scores
        HashMap<String,Integer> tailoredPlanMap = new HashMap<>();
        tailoredPlanMap.put("Underestimating Abilities", abilityScore);
        tailoredPlanMap.put("Discounting Achievements", achievementScore);
        tailoredPlanMap.put("Perfectionism", perfectionismScore);

        //Sort the map and place them in order in the ArrayList
        tailoredPlanMap.entrySet().stream().sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()))
                .forEach(k -> tailoredPlan.add(k.getKey()));

    }

    public ArrayList<String> getTailoredPlan() {
        return tailoredPlan;
    }

    public String getResponsesCollected() {
        return responsesCollected;
    }
}

package com.nasersalameh.imposterphenomenoninterventionapp.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CIPsResponse {

    //Time in UNIX timestamp
    private Long responseDate;
    private ArrayList<String> cipsQuestions;
    private HashMap<Integer,String> cipsIDQuestionMapping;
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

    //Constructor to add the answers slowly
    public CIPsResponse(String responsesCollected){
        this.responseDate = System.currentTimeMillis();

        cipsIDResponseMapping = new HashMap<>();

        this.responsesCollected = responsesCollected;

        //populate Questions Text
        populateQuestions();
        populateResults();
    }

    private void populateQuestions(){
        //Digitised CIPs Questions:
        cipsQuestions = new ArrayList<>();
        cipsQuestions.add("I have often succeeded on a test or task even though I was afraid that I would not do well before I undertook the task.");
        cipsQuestions.add("I can give the impression that I’m more competent than I really am.");
        cipsQuestions.add("I avoid evaluations if possible and have a dread of others evaluating me.");
        cipsQuestions.add("When people praise me for something I’ve accomplished, I’m afraid I won’t be able to live up to their expectations of me in the future.");
        cipsQuestions.add("I sometimes think I obtained my present position or gained my present success because I happened to be in the right place at the right time or knew the right people.");
        cipsQuestions.add("I’m afraid people important to me may find out that I’m not as capable as they think I am.");
        cipsQuestions.add("I tend to remember the incidents in which I have not done my best more than those times I have done my best.");
        cipsQuestions.add("I rarely do a project or task as well as I’d like to do it.");
        cipsQuestions.add("Sometimes I feel or believe that my success in my life or in my job has been the result of some kind of error.");
        cipsQuestions.add("It’s hard for me to accept compliments or praise about my intelligence or accomplishments.");
        cipsQuestions.add("At times, I feel my success has been due to some kind of luck.");
        cipsQuestions.add("I’m disappointed at times in my present accomplishments and think I should have accomplished much more.");
        cipsQuestions.add("Sometimes I’m afraid others will discover how much knowledge or ability I really lack.");
        cipsQuestions.add("I’m often afraid that I may fail at a new assignment or undertaking even though I generally do well at what I attempt.");
        cipsQuestions.add("When I’ve succeeded at something and received recognition for my accomplishments, I have doubts that I can keep repeating that success.");
        cipsQuestions.add("If I receive a great deal of praise and recognition for something I’ve accomplished, I tend to discount the importance of what I’ve done.");
        cipsQuestions.add("I often compare my ability to those around me and think they may be more intelligent than I am.");
        cipsQuestions.add("I often worry about not succeeding with a project or examination, even though others around me have considerable confidence that I will do well.");
        cipsQuestions.add("If I’m going to receive a promotion or gain recognition of some kind, I hesitate to tell others until it is an accomplished fact.");
        cipsQuestions.add("I feel bad and discouraged if I’m not “the best” or at least “very special” in situations that involve achievement.");

        //map questions to IDs:
        cipsIDQuestionMapping = new HashMap<>();
        for(int i=0;i<cipsQuestions.size();i++)
            cipsIDQuestionMapping.put(i,cipsQuestions.get(i));

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

    public String getCIPsQuestionString(Integer questionID){
        return cipsIDQuestionMapping.get(questionID);
    }

    public Integer getCIPsQuestionID(String questionText){
        return getFromBiMap(cipsIDQuestionMapping,questionText);
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

    //Special bi-directional get to get key using value
    private static <T, E> T getFromBiMap(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void calculateScoreValues(){
        calculateCIPsScore();
        switch (responsesCollected){
            case "FULL":
                calculateAbilityScore();
                calculateAchievementScore();
                calculatePerfectionismScore();
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

}

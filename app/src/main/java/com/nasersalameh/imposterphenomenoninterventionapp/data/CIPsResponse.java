package com.nasersalameh.imposterphenomenoninterventionapp.data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CIPsResponse {

    //Time in UNIX timestamp
    private Long responseDate;
    private ArrayList<String> cipsQuestions;
    private HashMap<Integer,String> cipsIDQuestionMapping;
    private HashMap<Integer,Integer> cipsIDResponseMapping;

    public CIPsResponse(Long date, HashMap<Integer,Integer> responses){
        this.responseDate = date;

        //populate Questions Text
        populateQuestions();

        //map responses to IDs:
        cipsIDResponseMapping = new HashMap<>();
        for(int i=0;i<responses.size();i++)
            cipsIDResponseMapping.put(i,responses.get(i));
    }
    public CIPsResponse(Long date){
        this.responseDate = date;

        cipsIDResponseMapping = new HashMap<>();

        //populate Questions Text
        populateQuestions();
    }

    public Long getResponseDate() {
        return responseDate;
    }

    public void populateQuestions(){
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

    //Special bi-directional get to get key using value
    private static <T, E> T getFromBiMap(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

}

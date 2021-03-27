package com.nasersalameh.imposterphenomenoninterventionapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.fragment.app.Fragment;

import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.MainActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements.AchievementsFragment;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AbilityData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.AchievementsTypeData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static org.junit.Assert.assertNotNull;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class UnitTests {

    private MainActivity activity;

    private SQLiteDatabase db;
    private DatabaseHelper databaseHelper;


    //SQLite DB Tests
    //Test 1: TEST INSTALL MIGRATION (ALL Tables)

    //TEST 2: TEST ABILITY READ
    //TEST 3: TEST ABILITY  EXP MODIFICATION

    //TEST 4: TEST ACHIEVEMENT READ
    //TEST 5: TEST ACHIEVEMENT INSERT

    //TEST 6: TEST ACHIEVEMENTS TYPE READ
    //TEST 7: TEST ACHIEVEMENT TYPE INSERT

    //TEST 8: TEST CIPS QUESTION READ
    //TEST 9: TEST CIPS QUESTION INSERT

    //TEST 10: TEST CIPS RESPONSE READ
    //TEST 11: TEST CIPS RESPONSE INSERT

    //TEST 12: TEST CONTENT READ
    //TEST 13: TEST CONTENT INSERT

    //TEST 14: TEST GOAL READ
    //TEST 15: TEST GOAL INSERT
    //TEST 35: TEST GOALS TASK INSERT

    //TEST 16: TEST INFORMATION READ
    //TEST 17: TEST INFORMATION INSERT

    //TEST 18: TEST LOG READ
    //TEST 19: TEST LOG INSERT

    //TEST 20: TEST REFLECTION READ
    //TEST 21: TEST REFLECTION INSERT

    //TEST 22: TEST USER READ
    //TEST 23: TEST USER INSERT

    //HELPERS TEST:
    //TEST 24: TEST UNIX TO DATE TEXT
    @Test
    public void testDateFromUnix(){
        //testing date
        String test = DateConverter.getDateFromUnixTime(1616800000l);
        assert (test.equals("26/3/2021"));
    }

    @Test
    public void testDateFromUnix2(){
        //testing date
        String test = DateConverter.getDateFromUnixTime(1620000000l);
        assert (test.equals("03/5/2021"));
    }

    @Test
    public void testDateFromUnixEdge1(){
        //testing date
        String test = DateConverter.getDateFromUnixTime(0l);
        assert (test.equals("01/1/1970"));
    }

    @Test
    public void testDateFromUnixEdge2(){
        //testing date
        String test = DateConverter.getDateFromUnixTime(Long.MAX_VALUE);
        assert (test.equals("01/1/1970"));
    }

    //TEST 25: TEST TEXT DATE TO UNIX
    @Test
    public void testDateToUnix(){
        //testing date
        Long test = DateConverter.getUnixTimeFromData("26/3/2021");
        assert (test == 1616716800);
    }

    @Test
    public void testDateToUnix2(){
        //testing date
        Long test = DateConverter.getUnixTimeFromData("13/5/2021");
        assert (test == 1620860400);
    }
    @Test
    public void testDateToUnixEdge(){
        //testing date
        Long test = DateConverter.getUnixTimeFromData("01/1/1970");
        assert (test == -3600);
    }

    //MODELS TESTS:
    //TEST ABILITY LEVEL
    @Test
    public void testAbilityLevel1(){
        Ability ability = new Ability("Test");
        ability.setExperience(0);
        assert(ability.getLevel()==1);
    }

    //TEST ABILITY LEVEL
    @Test
    public void testAbilityLevel2(){
        Ability ability = new Ability("Test");
        ability.setExperience(20);

        assert(ability.getLevel()==2);
    }

    //TEST ABILITY LEVEL
    @Test
    public void testAbilityLevelCap(){
        Ability ability = new Ability("Test");
        ability.setExperience(Integer.MAX_VALUE);

        assert(ability.getLevel()==20);

    }

    //TEST CIPS FULL SCORE CALCULATION
    @Test
    public void testCIPsScoreFull(){
        HashMap<Integer,Integer> testAnswers = new HashMap<>();
        for(int i=0;i<20;i+=5){
            testAnswers.put(i,1);
            testAnswers.put(i+1,2);
            testAnswers.put(i+2,3);
            testAnswers.put(i+3,4);
            testAnswers.put(i+4,5);
        }
        CIPsResponse response = new CIPsResponse(testAnswers,"FULL");
        response.calculateScoreValues();

        assert(response.getCipsScore() == 60);

    }

    //TEST CIPS FULL SCORE CALCULATION
    @Test
    public void testCIPsResult2(){
        HashMap<Integer,Integer> testAnswers = new HashMap<>();
        for(int i=0;i<20;i+=5){
            testAnswers.put(i,5);
            testAnswers.put(i+1,5);
            testAnswers.put(i+2,5);
            testAnswers.put(i+3,5);
            testAnswers.put(i+4,5);
        }
        CIPsResponse response = new CIPsResponse(testAnswers,"ABILITY");
        response.calculateScoreValues();

        assert(response.getCipsResult().equals("Often and Intensely Experiences IP Characteristics"));
    }

    //TEST CIPS ABILITY SCORE CALCULATION
    @Test
    public void testCIPsScoreAbility(){
        HashMap<Integer,Integer> testAnswers = new HashMap<>();
        for(int i=0;i<20;i+=5){
            testAnswers.put(i,1);
            testAnswers.put(i+1,2);
            testAnswers.put(i+2,3);
            testAnswers.put(i+3,4);
            testAnswers.put(i+4,5);
        }
        CIPsResponse response = new CIPsResponse(testAnswers,"ABILITY");
        response.calculateScoreValues();

        assert(response.getAbilityScore() == 6);

    }

    //TEST CIPS ACHIEVEMENT SCORE CALCULATION
    @Test
    public void testCIPsScoreAchievement(){
        HashMap<Integer,Integer> testAnswers = new HashMap<>();
        for(int i=0;i<20;i+=5){
            testAnswers.put(i,1);
            testAnswers.put(i+1,2);
            testAnswers.put(i+2,3);
            testAnswers.put(i+3,4);
            testAnswers.put(i+4,5);
        }
        CIPsResponse response = new CIPsResponse(testAnswers,"ACHIEVEMENT");
        response.calculateScoreValues();

        assert(response.getAchievementScore() == 8);

    }

    //TEST CIPS PERFECTIONISM SCORE CALCULATION
    @Test
    public void testCIPsScorePerfectionism(){
        HashMap<Integer,Integer> testAnswers = new HashMap<>();
        for(int i=0;i<20;i+=5){
            testAnswers.put(i,1);
            testAnswers.put(i+1,2);
            testAnswers.put(i+2,3);
            testAnswers.put(i+3,4);
            testAnswers.put(i+4,5);
        }
        CIPsResponse response = new CIPsResponse(testAnswers,"PERFECTIONISM");
        response.calculateScoreValues();

        assert(response.getPerfectionismScore() == 10);
    }

    //TEST CIPS RESULT
    @Test
    public void testCIPsResult(){
        HashMap<Integer,Integer> testAnswers = new HashMap<>();
        for(int i=0;i<20;i+=5){
            testAnswers.put(i,1);
            testAnswers.put(i+1,2);
            testAnswers.put(i+2,3);
            testAnswers.put(i+3,4);
            testAnswers.put(i+4,5);
        }
        CIPsResponse response = new CIPsResponse(testAnswers,"FULL");
        response.calculateScoreValues();

        assert(response.getCipsResult().equals("Moderately Experiences IP Characteristics"));
    }

    //FRAGMENTS TESTS:
    //TEST 34: TEST ACHIEVEMENTS SCORE

    //TEST 35: TEST GOALS TASK COMPLETION - REFLECTION SET
    //TEST 36: TEST GOALS TASK COMPLETION - COMPLETION DATE SET

    //TEST 37: TEST REFLECTIONS ACHIEVEMENTS LIST - I
    //TEST 38: TEST REFLECTIONS ACHIEVEMENTS LIST - II
    //TEST 39: TEST REFLECTIONS ACHIEVEMENTS LIST - III


}
package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;

import java.util.Map;

public class CIPsResponseData {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    private static final String CIPS_TABLE ="CIPS_TABLE" ;

    public CIPsResponseData(DatabaseHelper dbHelper, SQLiteDatabase db){
        this.db = db;
        this.dbHelper = dbHelper;
    }

    public void createCIPsResponsesTable() {

        //Date will be UNIX time
        String createTableStatement = "CREATE TABLE " + CIPS_TABLE + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, DATE INTEGER, " +
                "RESPONSES_TYPE TEXT, "+
                "RESPONSE_0 INTEGER, RESPONSE_1 INTEGER, RESPONSE_2 INTEGER, " +
                "RESPONSE_3 INTEGER, RESPONSE_4 INTEGER, RESPONSE_5 INTEGER, " +
                "RESPONSE_6 INTEGER, RESPONSE_7 INTEGER, RESPONSE_8 INTEGER, " +
                "RESPONSE_9 INTEGER, RESPONSE_10 INTEGER, RESPONSE_11 INTEGER, " +
                "RESPONSE_12 INTEGER, RESPONSE_13 INTEGER, RESPONSE_14 INTEGER, " +
                "RESPONSE_15 INTEGER, RESPONSE_16 INTEGER, RESPONSE_17 INTEGER, " +
                "RESPONSE_18 INTEGER, RESPONSE_19 INTEGER, " +
                "TOTAL_CIPS_SCORE INTEGER, " +
                "ABILITY_SCORE INTEGER, ACHIEVEMENT_SCORE INTEGER, PERFECTIONISM_SCORE INTEGER)";

        db.execSQL(createTableStatement);
    }

    public boolean insertSetupCIPsResponse(CIPsResponse response){

        try {
            if(response == null)
                throw new Exception("Error: Null Response!");
            else{
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put("DATE", response.getResponseDate());
                cv.put("RESPONSES_TYPE" , response.getResponsesCollected());

                for(Map.Entry entry : response.getCIPsIDResponseMap().entrySet()){
                    Integer questionID = (Integer) entry.getKey();
                    Integer questionResponse = (Integer) entry.getValue();

                    cv.put("RESPONSE_" + questionID, questionResponse);
                }

                cv.put("TOTAL_CIPS_SCORE", response.getCipsScore());
                cv.put("ABILITY_SCORE", response.getAbilityScore());
                cv.put("ACHIEVEMENT_SCORE", response.getAchievementScore());
                cv.put("PERFECTIONISM_SCORE", response.getPerfectionismScore());

                long insertResult = db.insert(CIPS_TABLE,null,cv);

                if(insertResult == -1)
                    return false;
                else
                    return true;
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

}

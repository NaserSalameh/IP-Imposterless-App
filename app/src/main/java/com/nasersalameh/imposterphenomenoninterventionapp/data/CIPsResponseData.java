package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CIPsResponseData {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;
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

    public CIPsResponse getSetupResponse(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + CIPS_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        try {
            //Setup response is first entry
            if (cursor.moveToFirst()) {
                //Statement works
                String responseType = cursor.getString(2);

                HashMap<Integer,Integer> responsesMapping = new HashMap<>();

                //ID, Column offset is 3
                for(int i =0;i<20;i++)
                    responsesMapping.put(i,cursor.getInt(i+3));

                //Close cursor and DB
                cursor.close();
                db.close();

                CIPsResponse returnResponse = new CIPsResponse(responsesMapping,responseType);
                //return new User
                return returnResponse;
            } else {
                //Select Statement Failed
                throw new Exception("Select CIPsResponse Statement Failed");
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}

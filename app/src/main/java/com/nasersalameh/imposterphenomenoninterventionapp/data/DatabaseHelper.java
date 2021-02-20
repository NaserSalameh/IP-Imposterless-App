package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.provider.ContactsContract;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Response;

import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String USER_TABLE = "USER_TABLE";
    private static final String CIPS_TABLE ="CIPS_TABLE" ;
    private SQLiteDatabase db;
    private static String DB_NAME = "IPInterventionDatabase.db";

    private Context context;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    //Will be called the first time the database is created. The method will Create all necessary tables.
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        //TO DO: Add methods for each new activity that requires its own table
        //Create Table for setup (User Information Table (Just Name) and CIPs responses table)
        createUserInformationTable();
        createCIPsResponsesTable();

    }

    //whenever version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    private void createUserInformationTable() {

        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_NAME TEXT, IMAGE_URI TEXT)";

        db.execSQL(createTableStatement);

    }

    //Insert User
    public boolean insertUser(String userName, Uri imageURI){
        try {
            if(userName.isEmpty())
                throw new Exception("Error: Cannot accept empty name!");
            else{
                db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put("CUSTOMER_NAME", userName);
                cv.put("IMAGE_URI", String.valueOf(imageURI));

                long insertResult = db.insert(USER_TABLE,null,cv);

                System.out.println("WE WROTE IT");
                if(insertResult == -1)
                    return false;
                else
                    return true;
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage() + "\n" + e.getStackTrace());
        }

        return false;
    }

    private void createCIPsResponsesTable() {

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

    public boolean insertCIPsResponse(CIPsResponse response){

        try {
            if(response == null)
                throw new Exception("Error: Null Response!");
            else{
                db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put("DATE", response.getResponseDate());
                cv.put("RESPONSES_TYPE" , "FULL");

                for(Map.Entry entry : response.getCIPsIDResponseMap().entrySet()){
                    Integer questionID = (Integer) entry.getKey();
                    Integer questionResponse = (Integer) entry.getValue();

                    cv.put("RESPONSE_" + questionID, questionResponse);
                }

                //calculate various CIPs Scores
                response.calculateScoreValues();
                cv.put("TOTAL_CIPS_SCORE", response.getCipsTotal());
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
            System.err.println(e.getMessage() + "\n" + e.getStackTrace());
        }
        return false;
    }

}

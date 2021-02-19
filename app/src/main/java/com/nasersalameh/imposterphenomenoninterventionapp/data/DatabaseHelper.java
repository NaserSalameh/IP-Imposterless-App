package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Response;

import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_FULL_PATH = "";

    public static final String USER_TABLE = "USER_TABLE";
    private static final String CIPS_TABLE ="CIPS_TABLE" ;
    SQLiteDatabase db;

    public DatabaseHelper(@Nullable Context context) {
        super(context, "IPInterventionDB.db", null, 1);
    }

    //Will be called the first time the database is created. The method will Create all necessary tables.
    @Override
    public void onCreate(SQLiteDatabase db) {

        //TO DO: Add methods for each new activity that requires its own table
        //Create Table for setup (User Information Table (Just Name) and CIPs responses table)
        createUserInformationTable();
        createCIPsResponsesTable();


    }

    //whenever version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //This will check if the database exists
    public boolean checkForDatabase() {
        try {
            db = SQLiteDatabase.openDatabase(DB_FULL_PATH, null,
                    SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
        }
        return db != null;
    }


    private void createUserInformationTable() {

        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_NAME TEXT)";

        db.execSQL(createTableStatement);

    }

    //Insert User
    public boolean insertUser(String userName){
        try {
            if(userName.isEmpty())
                throw new Exception("Error: Cannot accept empty name!");
            else{
                db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                cv.put("CUSTOMER_NAME", userName);

                long insertResult = db.insert(USER_TABLE,null,cv);

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

//        String createTableStatement = "CREATE TABLE " + CIPS_TABLE +
//                " (ID INTEGER PRIMARY KEY AUTOINCREMENT, CUSTOMER_NAME TEXT)";
//        db.execSQL(createTableStatement);


    }

    public boolean insertCIPsResponse(CIPsResponse response){

        try {
            if(response == null)
                throw new Exception("Error: Null Response!");
            else{
                db = this.getWritableDatabase();
                ContentValues cv = new ContentValues();

                for(Map.Entry entry : response.getCIPsIDResponseMap().entrySet()){
                    String question = response.getCIPsQuestionString((Integer) entry.getKey());
                    Integer questionID = (Integer) entry.getKey();
                    Integer questionResponse = (Integer) entry.getValue();

                    cv.put("RESPONSE_" + questionID, questionResponse);
                }

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

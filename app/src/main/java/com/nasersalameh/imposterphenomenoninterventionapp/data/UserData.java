package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;

public class UserData {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public static final String USER_TABLE = "USER_TABLE";

    public UserData(DatabaseHelper dbHelper, SQLiteDatabase db){
        this.db = db;
        this.dbHelper = dbHelper;
    }

    public void createUserInformationTable() {

        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " USER_NAME TEXT," +
                " IMAGE_URI TEXT," +
                " ABILITY_SEVERITY INTEGER," +
                " ACHIEVEMENT_SEVERITY INTEGER," +
                " PERFECTIONISM_SEVERITY INTEGER," +
                " ABILITY_UNLOCKED INTEGER," +
                " ABILITY_TUTORIAL_COMPLETED INTEGER," +
                " ACHIEVEMENT_UNLOCKED INTEGER," +
                " ACHIEVEMENT_TUTORIAL_COMPLETED INTEGER," +
                " GOAL_UNLOCKED INTEGER," +
                " GOAL_TUTORIAL_COMPLETED INTEGER)";

        db.execSQL(createTableStatement);

    }

    //Insert New User
    public boolean insertNewUser(String userName, Uri imageURI, CIPsResponse response){
        try {
            if(userName.isEmpty())
                throw new Exception("Error: Cannot accept empty name!");
            else{
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                //Add User Name and Image
                cv.put("USER_NAME", userName);
                cv.put("IMAGE_URI", String.valueOf(imageURI));

                //Add User CIPs Setup scores
                cv.put("ABILITY_SEVERITY", response.getTailoredPlan().indexOf("Underestimating Abilities"));
                cv.put("ACHIEVEMENT_SEVERITY", response.getTailoredPlan().indexOf("Discounting Achievements"));
                cv.put("PERFECTIONISM_SEVERITY", response.getTailoredPlan().indexOf("Perfectionism"));

                //Set all tabs to Locked
                cv.put("ABILITY_UNLOCKED" , -1);
                cv.put("ACHIEVEMENT_UNLOCKED" , -1);
                cv.put("GOAL_UNLOCKED" , -1);

                //Set all tutorials to incomplete
                cv.put("ABILITY_TUTORIAL_COMPLETED" , -1);
                cv.put("ACHIEVEMENT_TUTORIAL_COMPLETED" , -1);
                cv.put("GOAL_TUTORIAL_COMPLETED" , -1);

                long insertResult = db.insert(USER_TABLE,null,cv);

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

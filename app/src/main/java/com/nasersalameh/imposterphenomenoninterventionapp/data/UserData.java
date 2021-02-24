package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.User;

import java.util.ArrayList;
import java.util.HashMap;

public class UserData {

    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String USER_TABLE = "USER_TABLE";

    public UserData(DatabaseHelper dbHelper, SQLiteDatabase db){
        this.db = db;
        this.dbHelper = dbHelper;
    }

    public void createUserInformationTable() {

        String createTableStatement = "CREATE TABLE " + USER_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " USER_NAME TEXT," +
                " IMAGE_PATH TEXT," +
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
    public boolean insertNewUser(String userName, String imageURI, CIPsResponse response){
        try {
            if(userName.isEmpty())
                throw new Exception("Error: Cannot accept empty name!");
            else{
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                //Add User Name and Image
                cv.put("USER_NAME", userName);
                cv.put("IMAGE_PATH", imageURI);

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

    public User getUser(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + USER_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        try {
            if (cursor.moveToFirst()) {
                //Statement works
                String userName = cursor.getString(1);
                String imagePath = cursor.getString(2);

                ArrayList<String> severities = new ArrayList<>();
                //Add Temp values to be replaced
                severities.add("TEMP");
                severities.add("TEMP");
                severities.add("TEMP");

                int abilitiesSeverity = cursor.getInt(3);
                int achievementSeverity = cursor.getInt(4);
                int perfectionismSeverity = cursor.getInt(5);
                severities.add(abilitiesSeverity, "Ability");
                severities.add(achievementSeverity, "Achievement");
                severities.add(perfectionismSeverity, "Perfectionism");

                HashMap<String, Boolean> tabs = new HashMap<>();
                tabs.put("Ability", (cursor.getInt(6) == -1 ? false : true));
                tabs.put("Achievement", (cursor.getInt(7) == -1 ? false : true));
                tabs.put("Perfectionism", (cursor.getInt(8) == -1 ? false : true));

                HashMap<String, Boolean> trainings = new HashMap<>();
                trainings.put("Ability", (cursor.getInt(9) == -1 ? false : true));
                trainings.put("Achievement", (cursor.getInt(10) == -1 ? false : true));
                trainings.put("Perfectionism", (cursor.getInt(11) == -1 ? false : true));

                User returnUser = new User(userName, imagePath, severities, tabs, trainings);

                //Close cursor and DB
                cursor.close();
                db.close();

                //return new User
                return returnUser;
            } else {
                //Select Statement Failed
                throw new Exception("Select User Statement Failed");
            }
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

}

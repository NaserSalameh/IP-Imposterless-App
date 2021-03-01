package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;

import java.util.ArrayList;

public class AchievementData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public static final String ACHIEVEMENT_TABLE = "ACHIEVEMENT_TABLE";

    private ArrayList<Achievement> achievementList;

    public AchievementData(SQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createAchievementTable() {

        String createTableStatement = "CREATE TABLE " + ACHIEVEMENT_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ACHIEVEMENT_NAME TEXT," +
                " ACHIEVEMENT_DETAILS TEXT," +
                " ACHIEVEMENT_TYPE TEXT," +
                " ACHIEVEMENT_DATE LONG)";

        db.execSQL(createTableStatement);
    }

    //Insert New Achievement
    public boolean insertNewAchievement(Achievement achievement){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Achievement Name, Type, and date
            cv.put("ACHIEVEMENT_NAME", achievement.getAchievementName());
            cv.put("ACHIEVEMENT_DETAILS", achievement.getAchievementDetails());
            cv.put("ACHIEVEMENT_TYPE", achievement.getAchievementType().getAchievementType());
            cv.put("ACHIEVEMENT_DATE", achievement.getAchievementDate());

            long insertResult = db.insert(ACHIEVEMENT_TABLE,null,cv);

            if(insertResult == -1)
                return false;
            else
                return true;
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    //get all achievements
    public void createAchievementList(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + ACHIEVEMENT_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Achievement
        ArrayList<Achievement> achievementList = new ArrayList<Achievement>();
        try {
            if(cursor.moveToFirst())
                do{
                    AchievementType achievementType = new AchievementType(cursor.getString(3));
                    Achievement achievement = new Achievement(cursor.getString(1),cursor.getString(2), achievementType,cursor.getLong(4));
                    achievementList.add(achievement);

                }while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.achievementList = achievementList;
    }


    public ArrayList<Achievement> getAchievementList() {
        //Create achievement List updated as necessary
        createAchievementList();
        return achievementList;
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;

import java.util.ArrayList;
import java.util.HashMap;

public class AchievementsTypeData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public static final String ACHIEVEMENTS_TYPE_TABLE = "ACHIEVEMENTS_TYPE_TABLE";
    private ArrayList<AchievementType> achievementTypes;

    public AchievementsTypeData(SQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createAchievementsTypeTable() {
        String createTableStatement = "CREATE TABLE " + ACHIEVEMENTS_TYPE_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ACHIEVEMENT_TYPE TEXT, " +
                " ACHIEVEMENT_SCORE INTEGER)";

        db.execSQL(createTableStatement);
    }

    //Insert New Achievement Type
    public boolean insertNewAchievementType(AchievementType achievementType){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Information Name and progress
            cv.put("ACHIEVEMENT_TYPE", achievementType.getAchievementType());
            cv.put("ACHIEVEMENT_SCORE", achievementType.getAchievementScore());

            long insertResult = db.insert(ACHIEVEMENTS_TYPE_TABLE,null,cv);

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

    private void createAchievementsTypeList(){
        db = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + ACHIEVEMENTS_TYPE_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Achievement Types
        ArrayList<AchievementType> achievementTypes = new ArrayList<AchievementType>();
        try {
            if(cursor.moveToFirst())
                do{
                    AchievementType achievementType = new AchievementType(cursor.getString(1), cursor.getInt(2));
                    achievementTypes.add(achievementType);
                }while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.achievementTypes = achievementTypes;
    }

    public ArrayList<AchievementType> getAchievementsTypeList() {
        if(achievementTypes == null)
            createAchievementsTypeList();
        return achievementTypes;
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

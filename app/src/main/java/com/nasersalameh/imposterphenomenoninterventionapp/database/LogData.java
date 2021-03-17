package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class LogData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public static final String LOG_TABLE = "LOG_TABLE";

    public LogData(SQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createLogTable() {
        String createTableStatement = "CREATE TABLE " + LOG_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " TAB TEXT, " +
                " ACTION TEXT)";

        db.execSQL(createTableStatement);
    }

    //Insert New Log
    public boolean insertNewLog(Log log){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Information Name and progress
            cv.put("TAB", log.getTab());
            cv.put("ACTION", log.getAction());

            long insertResult = db.insert(LOG_TABLE,null,cv);

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

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

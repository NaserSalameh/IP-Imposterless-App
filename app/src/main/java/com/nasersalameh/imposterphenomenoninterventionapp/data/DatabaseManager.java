package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DatabaseManager {


    private static final String DB_FULL_PATH = "";
    SQLiteDatabase db;

    //checks if database exists
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

    public boolean createDatabase(){

        return false;
    }

}

package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

public class DatabaseManager {


    private static final String DB_FULL_PATH = "";
    SQLiteDatabase db;
    DatabaseHelper dbHelper;

    public DatabaseManager(Context context){
        dbHelper= new DatabaseHelper(context);
    }


    //Select User

    //Insert CIPs Response

    //Select CIPs Response
}

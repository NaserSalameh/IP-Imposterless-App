package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;

import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static String DB_NAME = "IPInterventionDatabase.db";

    //App Context
    private Context context;

    //Data classes to write/read each table content
    private UserData userData;
    private CIPsResponseData cipsResponseData;


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
        userData = new UserData(this,db);
        cipsResponseData = new CIPsResponseData(this,db);
        userData.createUserInformationTable();
        cipsResponseData.createCIPsResponsesTable();

    }

    //whenever version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public SQLiteDatabase getDatabase() {
        return db;
    }
}

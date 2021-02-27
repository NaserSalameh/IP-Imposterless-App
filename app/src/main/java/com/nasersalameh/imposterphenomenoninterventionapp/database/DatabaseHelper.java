package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static String DB_NAME = "UsageDatabase.db";

    //App Context
    private Context context;

    //Data classes to write/read each table content
    private UserData userData;
    private CIPsResponseData cipsResponseData;
    private InformationData informationData;
    private CIPsQuestionData cipsQuestionData;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

        userData = new UserData(this);
        cipsResponseData = new CIPsResponseData(this);
        informationData = new InformationData(this);
        cipsQuestionData = new CIPsQuestionData(this);
    }

    //Will be called the first time the database is created. The method will Create all necessary tables.
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        //TO DO: Add methods for each new activity that requires its own table

        userData.setDB(db);
        cipsResponseData.setDB(db);
        informationData.setDB(db);
        cipsQuestionData.setDB(db);

        //Create Tables
        userData.createUserInformationTable();
        cipsResponseData.createCIPsResponsesTable();
        informationData.createInformationTable();
        cipsQuestionData.createCIPSQuestionsTable();

    }

    public void migrateDataFromInstallToUsage(){
        //Insert Necessary entries in tables
        InstallDatabaseHelper installDatabaseHelper = new InstallDatabaseHelper(context);
        copyInformationFromInstallDatabase(installDatabaseHelper);
        copyCIPsQuestionsFromInstallDatabase(installDatabaseHelper);

        //Close database after setup
        this.closeDB();
        this.close();
    }

    //Copies all information entries from install database to usage database
    private void copyInformationFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        ArrayList<Information> informationList = installDatabaseHelper.getInformationList();
        for(Information information : informationList)
            informationData.insertNewInformation(information);
    }

    //Copies all CIPs Questions entries from install database to usage database
    private void copyCIPsQuestionsFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        HashMap<Integer, String> cipsIDQuestionsMapping = installDatabaseHelper.getCipsIDQuestionsMapping();
        for(Map.Entry cipsQuestion : cipsIDQuestionsMapping.entrySet())
            cipsQuestionData.insertNewQuestion((String) cipsQuestion.getValue());
    }

    //whenever version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
    }

    public SQLiteDatabase getDatabase() {
        return db;
    }

    public void closeDB(){
        db.close();
    }
}

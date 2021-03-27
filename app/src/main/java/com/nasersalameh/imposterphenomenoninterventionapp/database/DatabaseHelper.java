package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.AchievementType;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.io.File;
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
    private AchievementsTypeData achievementsTypeData;
    private AchievementData achievementData;
    private AbilityData abilityData;
    private GoalData goalData;
    private ReflectionData reflectionData;
    private LogData logData;
    private ContentData contentData;

    public DatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

        userData = new UserData(this);
        cipsResponseData = new CIPsResponseData(this);
        informationData = new InformationData(this);
        cipsQuestionData = new CIPsQuestionData(this);
        achievementsTypeData = new AchievementsTypeData(this);
        abilityData = new AbilityData(this);
        logData = new LogData(this);
        contentData = new ContentData(this);
    }

    //Use for test DB
    public DatabaseHelper(@Nullable Context context, String dbName) {
        super(context, dbName, null, 1);
        this.context = context;

        userData = new UserData(this);
        cipsResponseData = new CIPsResponseData(this);
        informationData = new InformationData(this);
        cipsQuestionData = new CIPsQuestionData(this);
        achievementsTypeData = new AchievementsTypeData(this);
        abilityData = new AbilityData(this);
        logData = new LogData(this);
        contentData = new ContentData(this);
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
        achievementsTypeData.setDB(db);
        abilityData.setDB(db);
        logData.setDB(db);
        contentData.setDB(db);

        //Create Tables
        userData.createUserInformationTable();
        cipsResponseData.createCIPsResponsesTable();
        informationData.createInformationTable();
        cipsQuestionData.createCIPSQuestionsTable();
        achievementsTypeData.createAchievementsTypeTable();
        abilityData.createAbilityTable();
        logData.createLogTable();
        contentData.createContentTable();
    }

    public void migrateDataFromInstallToUsage(){
        //Insert Necessary entries in tables
        InstallDatabaseHelper installDatabaseHelper = new InstallDatabaseHelper(context);
        copyInformationFromInstallDatabase(installDatabaseHelper);
        copyCIPsQuestionsFromInstallDatabase(installDatabaseHelper);
        copyAchievementsTypeFromInstallDatabase(installDatabaseHelper);
        copyAbilitiesFromInstallDatabase(installDatabaseHelper);
        copyContentFromInstallDatabase(installDatabaseHelper);

        //Close database after setup
        this.closeDB();
        this.close();
    }

    private void copyContentFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        ContentData installContentData = new ContentData(installDatabaseHelper);
        ArrayList<Content> contents = installContentData.getContentList();
        for(Content content : contents)
            contentData.insertNewContent(content);
    }

    private void copyAbilitiesFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        AbilityData installAbilityData = new AbilityData(installDatabaseHelper);
        ArrayList<Ability> abilities = installAbilityData.getAbilitiesList();
        for(Ability ability : abilities)
            abilityData.insertNewAbility(ability);

        //After migrating Abilities, initialise GoalsData
        goalData = new GoalData(this,abilities);
        goalData.setDB(db);
        goalData.createGoalTable();

        //After migrating Abilities, initialise ReflectionData
        reflectionData = new ReflectionData(this,abilities);
        reflectionData.setDB(db);
        reflectionData.createReflectionTable();
    }

    private void copyAchievementsTypeFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        AchievementsTypeData installAchievementTypeData = new AchievementsTypeData(installDatabaseHelper);
        ArrayList<AchievementType> achievementTypes = installAchievementTypeData.getAchievementsTypeList();
        for(AchievementType achievementType : achievementTypes)
            achievementsTypeData.insertNewAchievementType(achievementType);

        //After migrating achievement types, initialise achievementData
        achievementData = new AchievementData(this,achievementTypes);
        achievementData.setDB(db);
        achievementData.createAchievementTable();

    }

    //Copies all information entries from install database to usage database
    private void copyInformationFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        InformationData installInformationData = new InformationData(installDatabaseHelper);
        ArrayList<Information> informationList = installInformationData.getInformationList();
        for(Information information : informationList)
            informationData.insertNewInformation(information);
    }

    //Copies all CIPs Questions entries from install database to usage database
    private void copyCIPsQuestionsFromInstallDatabase(InstallDatabaseHelper installDatabaseHelper) {
        CIPsQuestionData installCipsQuestions = new CIPsQuestionData(installDatabaseHelper);
        HashMap<Integer, String> cipsIDQuestionsMapping = installCipsQuestions.getCipsIDQuestionsMapping();
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
        getReadableDatabase().close();
        getWritableDatabase().close();
        this.close();
    }

    public boolean deleteUsageDB(Context context) {
        String pathToDatabases = "/data/data/" + context.getPackageName() + "/databases/";
        File file = new File(pathToDatabases+DB_NAME);
        boolean deleted = file.delete();
        return deleted;
    }
}

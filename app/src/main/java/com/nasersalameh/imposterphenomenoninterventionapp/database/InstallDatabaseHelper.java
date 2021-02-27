package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class InstallDatabaseHelper extends SQLiteOpenHelper {

    private SQLiteDatabase db;
    private static String DB_NAME = "InstallDatabase.db";

    //Constant Tables
    public static final String CIPS_QUESTIONS_TABLE = "CIPS_QUESTIONS_TABLE";
    public static final String INFORMATION_TABLE = "INFORMATION_TABLE";

    //ID-Questions Mappings
    HashMap<Integer, String> cipsIDQuestionsMapping;

    //Information Mapping
    ArrayList<Information> informationList;

    //App Context
    private Context context;

    public InstallDatabaseHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;

    }

    //Will be called the first time the database is created. The method will Create all necessary tables.
    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

    }

    //whenever version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        this.db = db;
    }

    //Special bi-directional get to get key using value
    private static <T, E> T getFromBiMap(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }

    public void createCIPsIDQuestionsMapping(){
        db  = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + CIPS_QUESTIONS_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Questions
        HashMap<Integer, String> cipsIDQuestionsMapping = new HashMap<>();
        int questionID = 0;
        try {
            if(cursor.moveToFirst())
                do{
                cipsIDQuestionsMapping.put(questionID++,cursor.getString(1));
            }while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.cipsIDQuestionsMapping = cipsIDQuestionsMapping;
    }

    public HashMap<Integer, String> getCipsIDQuestionsMapping() {
        if(cipsIDQuestionsMapping == null)
            createCIPsIDQuestionsMapping();
        return cipsIDQuestionsMapping;
    }

    //Information Table
    public void createInformationList(){
        db  = this.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + INFORMATION_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Questions
        ArrayList<Information> informationList = new ArrayList<Information>();
        try {
            if(cursor.moveToFirst())
                do{
                    Information information = new Information(cursor.getString(1),cursor.getString(2),cursor.getString(3));
                    informationList.add(information);
                }while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.informationList = informationList;
    }


    public ArrayList<Information> getInformationList() {
        if(informationList == null)
            createInformationList();
        return informationList;
    }

    //Content Table


    public SQLiteDatabase getDatabase() {
        return db;
    }

    public void closeDB(){
        db.close();
    }
}

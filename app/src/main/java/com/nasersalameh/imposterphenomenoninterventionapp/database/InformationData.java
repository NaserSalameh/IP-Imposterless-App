package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;
import java.util.HashMap;

public class InformationData {
    private SQLiteDatabase db;
    private DatabaseHelper dbHelper;

    public static final String INFORMATION_TABLE = "INFORMATION_TABLE";

    private ArrayList<Information> informationList;

    public InformationData(DatabaseHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createInformationTable() {

        String createTableStatement = "CREATE TABLE " + INFORMATION_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " INFORMATION_NAME TEXT," +
                " INFORMATION_DETAIL TEXT," +
                " INFORMATION_CORPUS TEXT," +
                " PROGRESS INTEGER)";

        db.execSQL(createTableStatement);
    }

    //Insert New Information
    public boolean insertNewInformation(Information information){
        try {
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                //Add Information Name and progress
                cv.put("INFORMATION_NAME", information.getInformationName());
                cv.put("INFORMATION_DETAIL", information.getInformationDetails());
                cv.put("INFORMATION_CORPUS", information.getInformationCorpus());
                cv.put("PROGRESS", information.getProgress());

                long insertResult = db.insert(INFORMATION_TABLE,null,cv);

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

    //Insert New Information
    public boolean updateInformationProgress(String informationName,int progress){
        try {
            if(informationName.isEmpty())
                throw new Exception("Error: Cannot update progress if Information Name is empty!");
            else{
                db = dbHelper.getWritableDatabase();
                ContentValues cv = new ContentValues();

                //Add Information Name and progress
                cv.put("INFORMATION_NAME", informationName);
                cv.put("PROGRESS", progress);

                long insertResult = db.update(INFORMATION_TABLE,cv, "INFORMATION_NAME = ?", new String[]{informationName});

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

    //get all Information
    public void createInformationList(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + INFORMATION_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Information
        ArrayList<Information> informationList = new ArrayList<Information>();
        try {
            if(cursor.moveToFirst())
                do{
                    Information information = new Information(cursor.getString(1),cursor.getString(2),cursor.getString(3), cursor.getInt(4));
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
        //Create information List updated as necessary
        createInformationList();
        return informationList;
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

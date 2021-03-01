package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

public class CIPsQuestionData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public static final String CIPS_QUESTIONS_TABLE = "CIPS_QUESTIONS_TABLE";
    private HashMap<Integer, String> cipsIDQuestionsMapping;

    public CIPsQuestionData(SQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createCIPSQuestionsTable() {

        String createTableStatement = "CREATE TABLE " + CIPS_QUESTIONS_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " QUESTION TEXT)";

        db.execSQL(createTableStatement);
    }

    //Insert New Information
    public boolean insertNewQuestion(String question){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Information Name and progress
            cv.put("QUESTION", question);

            long insertResult = db.insert(CIPS_QUESTIONS_TABLE,null,cv);

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


    public void createCIPsIDQuestionsMapping(){
        db = dbHelper.getReadableDatabase();

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

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

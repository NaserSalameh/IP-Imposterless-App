package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;

import java.util.ArrayList;

public class ContentData {

    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    private ArrayList<Content> contentList;

    public static final String CONTENT_TABLE = "CONTENT_TABLE";

    public ContentData(SQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createContentTable() {
        String createTableStatement = "CREATE TABLE " + CONTENT_TABLE +
                " (ID TEXT PRIMARY KEY," +
                " CONTENT_NAME TEXT, " +
                " CONTENT_CONTENT TEXT)";

        db.execSQL(createTableStatement);
    }

    //Insert New Content
    public boolean insertNewContent(Content content){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Content Name and progress
            cv.put("ID", content.getId());
            cv.put("CONTENT_NAME", content.getName());
            cv.put("CONTENT_CONTENT", content.getContent());

            long insertResult = db.insert(CONTENT_TABLE,null,cv);

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

    //get single Content
    public Content getContentById(String contentID){
        db  = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CONTENT_TABLE + " WHERE ID = '"+contentID+"'", null);


        //Read All Content
        ArrayList<Content> contentList = new ArrayList<Content>();
        try {
            Content content = null;
            if(cursor.moveToFirst())
                content = new Content(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                return content;

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.contentList = contentList;
        return null;
    }

    //get all Content
    public void createContentList(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + CONTENT_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Content
        ArrayList<Content> contentList = new ArrayList<Content>();
        try {
            if(cursor.moveToFirst())
                do{
                    Content content = new Content(cursor.getString(0),cursor.getString(1),cursor.getString(2));
                    contentList.add(content);

                }while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.contentList = contentList;
    }

    public ArrayList<Content> getContentList() {
        //Create content List updated as necessary
        if (contentList==null)
            createContentList();
        return contentList;
    }
    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

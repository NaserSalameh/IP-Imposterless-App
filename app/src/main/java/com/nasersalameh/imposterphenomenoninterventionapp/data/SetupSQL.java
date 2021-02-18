package com.nasersalameh.imposterphenomenoninterventionapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Response;

public class SetupSQL extends SQLiteOpenHelper {

    public static final String TEST_TABLE = "TEST_TABLE";
    public static final String NAME = "Name";
    public static final String RESPONSE_1 = "Response1";
    public static final String RESPONSE_2 = "Response2";
    public static final String RESPONSE_3 = "Response3";
    SQLiteDatabase db;

    public SetupSQL(@Nullable Context context) {
        super(context, "Test.db", null, 1);
    }

    //accessed first time the db is accessed
    @Override
    public void onCreate(SQLiteDatabase db) {



    }

    //whenever version number changes
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean addResponse(Response response){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(NAME, response.getName());
        cv.put(RESPONSE_1, response.getResponses().get(0));
        cv.put(RESPONSE_2, response.getResponses().get(1));
        cv.put(RESPONSE_3, response.getResponses().get(2));

        long returnVal = db.insert(TEST_TABLE,null,cv);

        if(returnVal == -1)
            return false;
        else
            return true;
    };
}

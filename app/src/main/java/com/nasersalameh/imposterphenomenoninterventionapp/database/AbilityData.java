package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;

import java.util.ArrayList;

public class AbilityData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    public static final String ABILITY_TABLE = "ABILITY_TABLE";

    private ArrayList<Ability> abilitiesList;

    public AbilityData(SQLiteOpenHelper dbHelper){
        this.dbHelper = dbHelper;
    }

    public void createAbilityTable() {

        String createTableStatement = "CREATE TABLE " + ABILITY_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ABILITY_NAME TEXT," +
                " ABILITY_DETAILS TEXT," +
                " ABILITY_EXP INTEGER," +
                " ABILITY_IMPROVE TEXT)";

        db.execSQL(createTableStatement);
    }

    //Insert New ABILITY
    public boolean insertNewAbility(Ability ability){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Achievement Name, Type, and date
            cv.put("ABILITY_NAME", ability.getName());
            cv.put("ABILITY_DETAILS", ability.getDetails());
            cv.put("ABILITY_EXP", ability.getExperience());

            String improvements = ability.getImprovements().get(0);
            for(int i=1;i<ability.getImprovements().size();i++)
                improvements += "," + ability.getImprovements().get(i);

            cv.put("ABILITY_IMPROVE", improvements);

            long insertResult = db.insert(ABILITY_TABLE,null,cv);

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

    //get all abilities
    private void createAbilityList(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + ABILITY_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Achievement
        ArrayList<Ability> abilitiesList = new ArrayList<Ability>();
        try {
            if(cursor.moveToFirst())
                do{
                    Ability ability = new Ability(cursor.getString(1),cursor.getString(2), cursor.getInt(3));

                    String improvements = cursor.getString(4);
                    ArrayList<String> improvementList = new ArrayList<>();
                    for(String improvement: improvements.split(","))
                        improvementList.add(improvement);

                    ability.setImprovements(improvementList);
                    abilitiesList.add(ability);
                }
                while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.abilitiesList = abilitiesList;
    }


    public ArrayList<Ability> getAbilitiesList() {
        //Create achievement List updated as necessary
        createAbilityList();
        return abilitiesList;
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }
}

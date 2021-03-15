package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.ArrayList;

public class ReflectionData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;

    private final ArrayList<Ability> abilitiesList;

    public static final String REFLECTION_TABLE = "REFLECTION_TABLE";

    private ArrayList<Reflection> reflectionsList;

    public ReflectionData(SQLiteOpenHelper dbHelper, ArrayList<Ability> abilitiesList){
        this.dbHelper = dbHelper;

        this.abilitiesList = abilitiesList;
    }

    public void createReflectionTable() {

        String createTableStatement = "CREATE TABLE " + REFLECTION_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " GOAL_NAME TEXT," +
                " GOAL_DETAILS TEXT," +
                " GOAL_TYPE TEXT," +
                " GOAL_DATE INTEGER," +
                " TASKS TEXT," +
                " ABILITIES TEXT," +
                " GREAT_ACHIEVEMENT TEXT," +
                " BEST_ABILITY TEXT," +
                " BLOCKER TEXT," +
                " BLOCKER_DIFFICULTY TEXT," +
                " DEADLINE_MET INTEGER," +
                " DEADLINE_REASON TEXT," +
                " SUCCESS_SCORE INTEGER," +
                " SUCCESS_REASON TEXT," +
                " EXPECTATION_SCORE INTEGER," +
                " EXPECTATION_REASON TEXT)";

        db.execSQL(createTableStatement);
    }

    //Insert New REFLECTION
    public boolean insertNewReflection(Reflection reflection){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add GOAL Name, Type, and date
            cv.put("GOAL_NAME", reflection.getGoal().getName());
            cv.put("GOAL_DETAILS", reflection.getGoal().getDetails());
            cv.put("GOAL_TYPE", reflection.getGoal().getType());
            cv.put("GOAL_DATE", reflection.getGoal().getDeadlineUnixDate());

            String completeTasks = "";

            //Remove last comma
            completeTasks.replaceFirst(".$","");

            cv.put("TASKS", completeTasks);

            //Add Abilities
            String abilities = "";
            for(Ability ability : reflection.getGoal().getAbilities())
                abilities+= ability.getName() + ",";

            abilities.replaceFirst(".$","");
            cv.put("ABILITIES", completeTasks);

            cv.put("GREAT_ACHIEVEMENT", reflection.getGreatAchievement());
            cv.put("BEST_ABILITY", reflection.getBestAbility());
            cv.put("BLOCKER", reflection.getBlocker());
            cv.put("BLOCKER_DIFFICULTY", reflection.getBlockerDifficulty());

            if(reflection.isDeadlineMet())
                cv.put("DEADLINE_MET", 1);
            else
            cv.put("DEADLINE_MET", -1);

            cv.put("DEADLINE_REASON", reflection.getDeadlineReason());
            cv.put("SUCCESS_SCORE", reflection.getSuccessScore());
            cv.put("SUCCESS_REASON", reflection.getLowSuccessReason());
            cv.put("EXPECTATION_SCORE", reflection.getExpectationScore());
            cv.put("EXPECTATION_REASON", reflection.getLowExpectationReason());

            long insertResult = db.insert(REFLECTION_TABLE,null,cv);

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

    //get all Reflections
    private void createReflectionList(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + REFLECTION_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Goals
        ArrayList<Reflection> reflectionsList = new ArrayList<Reflection>();
        try {
            if(cursor.moveToFirst())
                do{
                    Goal goal = new Goal(cursor.getString(1),cursor.getString(2), cursor.getString(3),cursor.getLong(4));

                    //Add all complete Tasks
                    String completeTasks = cursor.getString(5);
                    if(!completeTasks.equals(""))
                        for(String completeTask : completeTasks.split(",")){
                            Task newCompleteTask = new Task(completeTask,goal,true);
                            goal.addTask(newCompleteTask);
                        }

                    //Add all abilities
                    String abilities = cursor.getString(6);

                    //Find ability object
                    if(!abilities.equals(""))
                        for(String abilityString : abilities.split(","))
                            for(Ability ability: abilitiesList)
                                if(ability.getName().equals(abilityString))
                                    goal.addAbility(ability);


                    Reflection reflection = new Reflection(goal);

                    reflection.setGreatAchievement(cursor.getString(7));

                    reflection.setBestAbility(cursor.getString(8));

                    reflection.setBlocker(cursor.getString(9));
                    reflection.setBlockerDifficulty(cursor.getInt(10));

                    reflection.setDeadlineMet(cursor.getInt(11)==1);
                    reflection.setDeadlineReason(cursor.getString(12));

                    reflection.setSuccessScore(cursor.getInt(13));
                    reflection.setLowSuccessReason(cursor.getString(14));
                    reflection.setExpectationScore(cursor.getInt(15));
                    reflection.setLowExpectationReason(cursor.getString(16));

                    reflectionsList.add(reflection);
                }
                while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.reflectionsList = reflectionsList;
    }

    public ArrayList<Reflection> getReflectionsList() {
        //Create achievement List updated as necessary
        createReflectionList();
        return reflectionsList;
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }

}

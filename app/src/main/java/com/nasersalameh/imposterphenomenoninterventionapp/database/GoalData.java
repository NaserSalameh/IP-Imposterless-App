package com.nasersalameh.imposterphenomenoninterventionapp.database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.ArrayList;

public class GoalData {
    private SQLiteDatabase db;
    private SQLiteOpenHelper dbHelper;
    private final ArrayList<Ability> abilitiesList;

    public static final String GOAL_TABLE = "GOAL_TABLE";

    private ArrayList<Goal> goalsList;

    public GoalData(SQLiteOpenHelper dbHelper, ArrayList<Ability> abilitiesList){
        this.dbHelper = dbHelper;

        this.abilitiesList = abilitiesList;
    }

    public void createGoalTable() {

        String createTableStatement = "CREATE TABLE " + GOAL_TABLE +
                " (ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " GOAL_NAME TEXT," +
                " GOAL_DETAILS TEXT," +
                " GOAL_TYPE TEXT," +
                " GOAL_DATE INTEGER," +
                " COMPLETE_TASKS TEXT," +
                " INCOMPLETE_TASKS TEXT," +
                " ABILITIES TEXT)";

        db.execSQL(createTableStatement);

        //Add setup goal at creation of table
        insertNewGoal(createSetupGoal());
    }


    private Goal createSetupGoal() {
        //Get all Goal Data
        String goalName = "Setup";
        String goalType = "Medium";
        String goalDetails = "Explore the application!";

        //only get the date
        Long goalDate = System.currentTimeMillis();

        Goal newGoal = new Goal(goalName, goalDetails, goalType, goalDate);

        ArrayList<Task> setupTasks = new ArrayList<>();
        setupTasks.add(new Task("Explore Profile Tab!", newGoal));
        setupTasks.add(new Task("Explore Information Tab!", newGoal));
        setupTasks.add(new Task("Explore Ability Tab!", newGoal));
        setupTasks.add(new Task("Explore Achievement Tab!", newGoal));
        setupTasks.add(new Task("Explore Settings Tab!", newGoal));

        newGoal.setTasks(setupTasks);
        return newGoal;
    }


    //Insert New GOAL
    public boolean insertNewGoal(Goal goal){
        try {
            db = dbHelper.getWritableDatabase();
            ContentValues cv = new ContentValues();

            //Add Achievement Name, Type, and date
            cv.put("GOAL_NAME", goal.getName());
            cv.put("GOAL_DETAILS", goal.getDetails());
            cv.put("GOAL_TYPE", goal.getType());
            cv.put("GOAL_DATE", goal.getDeadlineUnixDate());

            String completeTasks = "";
            String incompleteTasks = "";

            for(Task task : goal.getTasks())
                if(task.isCompleted())
                    completeTasks += task.getName() + ",";
                else
                    incompleteTasks+=task.getName() + ",";

            //Remove last comma
            completeTasks.replaceFirst(".$","");
            incompleteTasks.replaceFirst(".$","");

            cv.put("COMPLETE_TASKS", completeTasks);
            cv.put("INCOMPLETE_TASKS", incompleteTasks);

            //Add Abilities
            String abilities = "";
            for(Ability ability : goal.getAbilities())
                abilities+= ability.getName() + ",";

            abilities.replaceFirst(".$","");
            cv.put("ABILITIES", completeTasks);

            long insertResult = db.insert(GOAL_TABLE,null,cv);

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

    //get all Goals
    private void createGoalList(){
        db  = dbHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + GOAL_TABLE;

        Cursor cursor = db.rawQuery(selectQuery,null);

        //Read All Goals
        ArrayList<Goal> goalsList = new ArrayList<Goal>();
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

                    //Add all incomplete Tasks
                    String incompleteTasks = cursor.getString(6);
                    if(!incompleteTasks.equals(""))
                        for(String completeTask : incompleteTasks.split(",")){
                            Task newCompleteTask = new Task(completeTask,goal,false);
                            goal.addTask(newCompleteTask);
                        }

                    //Add all abilities
                    String abilities = cursor.getString(7);

                    //Find ability object
                    if(!abilities.equals(""))
                        for(String abilityString : abilities.split(","))
                            for(Ability ability: abilitiesList)
                                if(ability.getName().equals(abilityString))
                                    goal.addAbility(ability);


                    goalsList.add(goal);
                }
                while (cursor.moveToNext());

        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        this.goalsList = goalsList;
    }

    public ArrayList<Goal> getGoalsList() {
        //Create achievement List updated as necessary
        createGoalList();
        return goalsList;
    }

    public boolean deleteGoalRow(Goal goal){
        db  = dbHelper.getWritableDatabase();
        return db.delete(GOAL_TABLE, "GOAL_NAME=?" ,  new String[]{goal.getName()}) > 0;
    }

    private void deleteAllDataInTable(){
        db  = dbHelper.getWritableDatabase();
        String truncateTableStatement = "DELETE FROM " + GOAL_TABLE;
        db.rawQuery(truncateTableStatement,null);
    }

    private void insertGoalsList(ArrayList<Goal> goals){
        for(Goal goal: goals)
            insertNewGoal(goal);
    }

    public void replaceGoalsInDB(ArrayList<Goal> goals){
        //delete all current goals
        deleteAllDataInTable();
        insertGoalsList(goals);
    }

    public void setDB(SQLiteDatabase db) {
        this.db = db;
    }

}

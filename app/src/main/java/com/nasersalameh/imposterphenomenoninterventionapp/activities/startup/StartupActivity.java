package com.nasersalameh.imposterphenomenoninterventionapp.activities.startup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.setup.SetupActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.data.DatabaseHelper;

public class StartupActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String DB_NAME = "IPInterventionDatabase.db";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        boolean databaseExists = checkForDatabase(DB_NAME);

        if(databaseExists)
            startApplication();
        else
            startSetup();

    }

    //This will check if the database exists
    public boolean checkForDatabase(String dbName) {
        try {
            db = SQLiteDatabase.openDatabase(String.valueOf(this.getDatabasePath(dbName)), null,
                    SQLiteDatabase.OPEN_READONLY);
            db.close();
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            System.err.println("Database Does Not Exist Yet!");
        }
        return db != null;
    }

    private void startSetup() {

        TextView loadingText = findViewById(R.id.setupTextView);
        loadingText.setText("Welcome!\nCreating Database!");

        //create Database which will create the database and tables
        dbHelper = new DatabaseHelper(StartupActivity.this);

        //intent to start setup activity
        Intent switchToSetup = new Intent(this, SetupActivity.class);

        loadingText.setText("Prepping Setup!");

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            public void run() {
                //what ever you do here will be done after 3 seconds delay.
                startActivity(switchToSetup);

            }
        };
        handler.postDelayed(r, 2000);
    }

    private void startApplication() {
    }



}

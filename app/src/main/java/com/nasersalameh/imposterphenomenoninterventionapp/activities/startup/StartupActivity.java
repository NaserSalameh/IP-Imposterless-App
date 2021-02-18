package com.nasersalameh.imposterphenomenoninterventionapp.activities.startup;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.setup.SetupActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.data.DatabaseManager;

public class StartupActivity extends AppCompatActivity {

    DatabaseManager dbms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        dbms = new DatabaseManager();
        boolean databaseExists = dbms.checkForDatabase();

        if(databaseExists)
            startApplication();
        else
            startSetup();
    }


    private void startApplication() {
        createDatabase();

        //intent to start setup activity
        Intent switchToSetup = new Intent(this, SetupActivity.class);
        startActivity(switchToSetup);

    }

    private void createDatabase() {
    }

    private void startSetup() {
    }



}

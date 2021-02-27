package com.nasersalameh.imposterphenomenoninterventionapp.activities.startup;

import android.content.Intent;
import android.content.res.AssetManager;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.main.MainActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.activities.setup.SetupActivity;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StartupActivity extends AppCompatActivity {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;
    private static String INSTALL_DB_NAME = "InstallDatabase.db";
    private static String DB_NAME = "UsageDatabase.db";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        String pathToDatabases = "/data/data/" + getPackageName() + "/databases/";  // Your application path

        //Copy Database From Assets
        copyAsset(getAssets(),INSTALL_DB_NAME,pathToDatabases + INSTALL_DB_NAME);

        boolean databaseExists = checkForDatabase(DB_NAME);

        if(databaseExists)
            startApplication();
        else
            startSetup();

    }

    private static boolean copyAsset(AssetManager assetManager,
                                     String databaseName, String newDatabasePathAndName) {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(databaseName);
            new File(newDatabasePathAndName).createNewFile();
            out = new FileOutputStream(newDatabasePathAndName);
            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
            return true;
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private static void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while((read = in.read(buffer)) != -1){
            out.write(buffer, 0, read);
        }
    }

    private void copyFile(String filename) {

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


        //Create Usage table
        dbHelper = new DatabaseHelper(StartupActivity.this);

        //Migrate Data from install to usage
        dbHelper.migrateDataFromInstallToUsage();

        //intent to start setup activity
        Intent switchToSetup = new Intent(this, SetupActivity.class);

        loadingText.setText("Prepping Setup!");

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            public void run() {
                //what ever you do here will be done after 3 seconds delay.
                startActivity(switchToSetup);

                //End startup Activity
                finish();
            }
        };
        handler.postDelayed(r, 2000);
    }

    //Start the main activity
    private void startApplication() {

        //Intent to start application
        Intent startMainActivity = new Intent(this, MainActivity.class);
        startActivity(startMainActivity);

        //End Startup Activity
        finish();
    }



}

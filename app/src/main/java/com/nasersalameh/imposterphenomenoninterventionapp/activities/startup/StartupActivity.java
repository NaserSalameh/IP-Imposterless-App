package com.nasersalameh.imposterphenomenoninterventionapp.activities.startup;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

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

        //Create Usage table
        dbHelper = new DatabaseHelper(StartupActivity.this);

        boolean setupCompleted = checkForUser(DB_NAME);

        if(setupCompleted)
            startApplication();
        else {
            //Delete Db
            dbHelper.deleteUsageDB(this);
            //Check permission and startup if possible
            requestPermission();
        }
    }


    public void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,}, 1);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            startSetup();
        } else {
            Toast.makeText(this, "Permission denied to read your External storage", Toast.LENGTH_SHORT).show();
            requestPermission();
        }
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

    //This will check if the database exists
    public boolean checkForTable(String dbName, String tableName) {
        try {
            db = SQLiteDatabase.openDatabase(String.valueOf(this.getDatabasePath(dbName)), null,
                    SQLiteDatabase.OPEN_READONLY);

            boolean check = true;
            String query = "select DISTINCT tbl_name from sqlite_master where tbl_name = '"+tableName+"'";
            try (Cursor cursor = db.rawQuery(query, null)) {
                if(cursor!=null) {
                    if(cursor.getCount()>0)
                        check = true;
                    else
                        check = false;
                }
            }
            db.close();
            return check;
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            System.err.println("Table Does Not Exist Yet!");
        }
        return db != null;
    }

    //This will check if the database exists
    public boolean checkForUser(String dbName) {
        try {
            db = SQLiteDatabase.openDatabase(String.valueOf(this.getDatabasePath(dbName)), null,
                    SQLiteDatabase.OPEN_READONLY);

            boolean check = true;
            String selectQuery = "SELECT * FROM USER_TABLE";
            try (Cursor cursor = db.rawQuery(selectQuery, null)) {
                if(cursor!=null) {
                    if(cursor.getCount()>0)
                        check = true;
                    else
                        check = false;
                }
            }
            db.close();
            return check;
        } catch (SQLiteException e) {
            // database doesn't exist yet.
            System.err.println("No User Found!");
        }
        return db != null;
    }

    private void startSetup() {

        TextView loadingText = findViewById(R.id.setupTextView);
        loadingText.setText("Welcome!\nCreating Database!");
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

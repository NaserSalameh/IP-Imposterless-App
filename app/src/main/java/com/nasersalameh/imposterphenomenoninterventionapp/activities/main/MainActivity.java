package com.nasersalameh.imposterphenomenoninterventionapp.activities.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.UserData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.User;

import androidx.annotation.Nullable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    public static final int INFORMATION_ACTIVITY_RESULT = 101;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Setup Drawer Navigation
        setUpNavigation();

        //Load up DB
        populateApp();
    }

    private void setUpNavigation(){
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_profile,
                R.id.nav_goals,
                R.id.nav_abilities,
                R.id.nav_achievements,
                R.id.nav_information,
                R.id.nav_settings)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    //Load DB and Populate various tabs:
    private void populateApp(){
        DatabaseHelper dbHelper = new DatabaseHelper(MainActivity.this);

        //Populate Nav Header:
        User user = loadUserData(dbHelper);
        populateNavHeader(user);
        unlockAppropriateTabs(user);

        //SetUpActivityResults

    }


    private User loadUserData(DatabaseHelper dbHelper){
        UserData userData = new UserData(dbHelper);
        return userData.getUser();
    }

    private void populateNavHeader(User user){
        //Set Image
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        ImageView navImage = headerView.findViewById(R.id.navHeaderImageView);

        //Get image Path
        try {
            navImage.setImageURI(Uri.fromFile(new File(user.getImagePath() + "/profile.jpg")));
        }
        catch (Exception e){
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
        //Set Name
        TextView navText = headerView.findViewById(R.id.navHeaderNameTextView);
        navText.setText(user.getUserName());
    }

    private void unlockAppropriateTabs(User user) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Information Tab Activity
        if(requestCode == INFORMATION_ACTIVITY_RESULT)
            if(requestCode== Activity.RESULT_OK || requestCode == Activity.RESULT_CANCELED){
                //Get Card Progress Bar
//                System.out.println("IM HERE NOW");
//                ProgressBar cardProgressBar = findViewById(R.id.cardProgressBar);
//                cardProgressBar.setProgress(data.getIntExtra("Progress",-1));
            }

    }
}
package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;

public class AchievementCardPopup {

    private final Context context;
    private final Activity mainActivity;
    private final Achievement achievement;
    private RecyclerView recyclerView;
    private PopupWindow popupWindow;

    AchievementCardPopup(Context context, Activity mainActivity, RecyclerView recyclerView, Achievement achievement){
        this.context = context;
        this.mainActivity = mainActivity;
        this.recyclerView = recyclerView;

        this.achievement = achievement;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createPopUpWindow(AchievementCardsAdapter.ViewHolder viewHolder) {
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_achievements_activity,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View view = recyclerView.getLayoutManager().findViewByPosition(0);

//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        mainActivity.getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
//        int height = (int) ((int) displayMetrics.heightPixels * .25);
//        int width =  (int) ((int) displayMetrics.widthPixels * .3);
//
//        System.out.println(height);
//        System.out.println(width);
        final PopupWindow popupWindow = new PopupWindow(container, 1000, 1000, true);
        //what ever you do here will be done after 3 seconds delay.

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        };
        handler.postDelayed(r, 500);

        //Handler to thread sleep and slow down process
        r = new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            public void run() {
                setUpAchievementActivity(container);
            }
        };
        handler.postDelayed(r, 500);

        this.popupWindow = popupWindow;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setUpAchievementActivity(ViewGroup container){
        //Inject UI:
        ImageView achievementImageView = container.findViewById(R.id.achievementActivityImageView);

        TextView achievementNameTextView = container.findViewById(R.id.achievementActivityNameTextView);

        TextView achievementDateTextView = container.findViewById(R.id.achievementActivityDateTextView);

        TextView achievementDetailsTextView = container.findViewById(R.id.achievementActivityDetailsTextView);

        TextView achievementActivityButton = container.findViewById(R.id.achievementActivityButton);

        //Set Image
        achievementImageView.setImageDrawable(getAchievementDrawable(achievement.getAchievementType().getAchievementType()));

        //Set Name
        achievementNameTextView.setText(achievement.getAchievementName());

        //Set date -> Convert To Readable Date
//        achievementDateTextView.setText(achievement.getAchievementDate());

        //set Details
        achievementDetailsTextView.setText(achievement.getAchievementDetails());

        achievementActivityButton.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

    }

    private Drawable getAchievementDrawable(String achievementType){
        //get and set achievement icon
        int iconID = 0;
        switch(achievementType){
            case "type0":
                iconID = R.drawable.ic_achievement_small_goal;
                break;
            case "type1":
                iconID = R.drawable.ic_achievement_medium_goal;
                break;
            case "type2":
                iconID = R.drawable.ic_achievement_large_goal;
                break;
            case "type3":
                iconID = R.drawable.ic_achievement_cips_completion;
                break;
            case "type4":
                iconID = R.drawable.ic_achievement_information_completion;
                break;
            case "type5":
                iconID = R.drawable.ic_achievement_streak;
                break;
            case "type6":
                iconID = R.drawable.ic_achievement_time;
                break;
            case "type7":
                iconID = R.drawable.ic_achievement_growth;
                break;
        }
        return ContextCompat.getDrawable(context,iconID);
    }


}


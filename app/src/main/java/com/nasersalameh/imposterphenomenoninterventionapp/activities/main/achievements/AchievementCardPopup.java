package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DrawableGetter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;

import java.util.Date;

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
        ViewGroup container = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_achievements_popup_activity,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View view = recyclerView.getLayoutManager().findViewByPosition(0);

        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        Runnable r=new Runnable() {
            public void run() {
                popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
            }
        };
        handler.postDelayed(r, 500);

        //Handler to thread sleep and slow down process
        r = () -> setUpAchievementActivity(container);
        handler.postDelayed(r, 500);

        this.popupWindow = popupWindow;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void setUpAchievementActivity(ViewGroup container){
        //Inject UI:
        ImageView achievementImageView = container.findViewById(R.id.achievementPopupImageView);

        TextView achievementNameTextView = container.findViewById(R.id.achievementPopupNameTextView);

        TextView achievementTypeTextView = container.findViewById(R.id.achievementPopupTypeTextView);

        TextView achievementDateTextView = container.findViewById(R.id.achievementPopupDateTextView);

        TextView achievementDetailsTextView = container.findViewById(R.id.achievementPopupDetailsTextView);

        Button achievementActivityButton = container.findViewById(R.id.achievementsPopupCloseButton);

        //Set Image
        achievementImageView.setImageDrawable(getAchievementDrawable(achievement.getAchievementType().getAchievementType()));

        //Set Name
        achievementNameTextView.setText(achievement.getAchievementName());

        //Set Type and Score
        achievementTypeTextView.setText("Type: " + achievement.getAchievementType().getAchievementType() +
                                        "\nScore: " + achievement.getAchievementType().getAchievementScore());

        //Set date -> Convert To Readable Date
        String date = DateConverter.getDateFromUnixTime(achievement.getAchievementDate());
        achievementDateTextView.setText(date);

        //set Details
        achievementDetailsTextView.setText(achievement.getAchievementDetails());

        achievementActivityButton.setOnClickListener(v -> {
            popupWindow.dismiss();
        });

    }

    private Drawable getAchievementDrawable(String achievementType){
        //get and set achievement icon
        int iconID = DrawableGetter.getAchievementDrawable(achievementType);
        return ContextCompat.getDrawable(context,iconID);
    }


}


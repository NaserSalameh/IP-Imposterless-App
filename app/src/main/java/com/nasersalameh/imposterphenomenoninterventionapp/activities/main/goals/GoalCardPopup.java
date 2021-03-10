package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.goals;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Goal;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Task;

import java.util.Date;

public class GoalCardPopup {

    private final Context context;
    private final Activity mainActivity;
    private final Goal goal;
    private RecyclerView goalRecyclerView;

    private PopupWindow popupWindow;

    public GoalCardPopup(Context context, Activity mainActivity, RecyclerView goalRecyclerView, Goal goal){
        this.context = context;
        this.mainActivity = mainActivity;
        this.goalRecyclerView = goalRecyclerView;

        this.goal = goal;

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void createPopUpWindow(TasksCardsAdapter.ViewHolder viewHolder) {
        //Create and inflate layout
        ViewGroup container = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.fragment_goals_goal_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View view = goalRecyclerView.getLayoutManager().findViewByPosition(0);

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
        r = () -> setUpTaskPopup(container);
        handler.postDelayed(r, 500);

        this.popupWindow = popupWindow;
    }

    private void setUpTaskPopup(ViewGroup container) {

    }
}


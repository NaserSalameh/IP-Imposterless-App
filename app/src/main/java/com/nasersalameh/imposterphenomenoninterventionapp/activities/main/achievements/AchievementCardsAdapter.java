package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DrawableGetter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;

public class AchievementCardsAdapter extends RecyclerView.Adapter<AchievementCardsAdapter.ViewHolder> {

    private final Activity mainActivity;
    private LayoutInflater layoutInflater;
    private ArrayList<Achievement> achievementList;

    private Context context;

    private RecyclerView recyclerView;

    public AchievementCardsAdapter(Context context, ArrayList<Achievement> achievementList, Activity mainActivity, RecyclerView recyclerView){
        this.layoutInflater = LayoutInflater.from(context);
        this.achievementList = achievementList;

        this.context = context;
        this.mainActivity = mainActivity;
        this.recyclerView =recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_achievements_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //get and set achievement icon
        int iconID = DrawableGetter.getAchievementDrawable(achievementList.get(index).getAchievementType().getAchievementType());
        Drawable drawable = ContextCompat.getDrawable(context.getApplicationContext(),iconID);
        viewHolder.achievementImageView.setImageDrawable(drawable);

        viewHolder.achievementTextView.setText(achievementList.get(index).getAchievementName());

//        //Set on Click Listener to View Card
        viewHolder.cardView.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
            LogData logData = new LogData(databaseHelper);
            logData.insertNewLog(new Log("Achievement", "Viewed Achievement " + achievementList.get(index).getAchievementName()));
            AchievementCardPopup achievementCardPopup = new AchievementCardPopup(context, mainActivity, recyclerView, achievementList.get(index));
            achievementCardPopup.createPopUpWindow(viewHolder);
        });

    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return achievementList.size();
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        ImageView achievementImageView;

        TextView achievementTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.achievementCardView);

            achievementImageView = itemView.findViewById(R.id.achievementImageView);
            achievementTextView = itemView.findViewById(R.id.achievementTextView);

        }
    }



}

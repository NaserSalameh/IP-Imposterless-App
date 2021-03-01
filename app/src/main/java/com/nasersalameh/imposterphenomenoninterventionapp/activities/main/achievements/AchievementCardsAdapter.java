package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.achievements;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;

public class AchievementCardsAdapter extends RecyclerView.Adapter<AchievementCardsAdapter.ViewHolder> {

    public static final int INFORMATION_ACTIVITY_RESULT = 101;
    private ViewHolder cardView;

    private LayoutInflater layoutInflater;
    private ArrayList<Achievement> achievementList;
    private int achievementIndex;

    private Activity mainActivity;

    public AchievementCardsAdapter(Context context, ArrayList<Achievement> achievementList, Activity mainActivity){
        this.layoutInflater = LayoutInflater.from(context);
        this.achievementList = achievementList;

        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_achievements_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //Bind card view
        cardView = viewHolder;

        //bind Card with Behaviour
        this.achievementIndex = index;

        //get and set achievement icon
        int iconID = 0;
        switch(achievementList.get(index).getAchievementType().getAchievementType()){
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
        Drawable drawable = ContextCompat.getDrawable(mainActivity.getApplicationContext(),iconID);
        viewHolder.achievementImageView.setImageDrawable(drawable);

        viewHolder.achievementTextView.setText(achievementList.get(index).getAchievementName());

//        //Set on Click Listener to View Card
//        viewHolder.cardView.setOnClickListener(v -> {
//
//            //Start Information Activity
//            Intent startInformationCardActivity = new Intent(mainActivity, InformationCardActivity.class);
//
//            //Add current information to intent
//            startInformationCardActivity.putExtra("Information", informationList.get(index));
//
//            mainActivity.startActivity(startInformationCardActivity);
//        });

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

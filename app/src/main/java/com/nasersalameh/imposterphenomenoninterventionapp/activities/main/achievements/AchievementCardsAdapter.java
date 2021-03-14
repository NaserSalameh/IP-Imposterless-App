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
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;

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
        int iconID = 0;
        switch(achievementList.get(index).getAchievementType().getAchievementType()){
            case "Small Goal":
                iconID = R.drawable.ic_achievement_small_goal;
                break;
            case "Medium Goal":
                iconID = R.drawable.ic_achievement_medium_goal;
                break;
            case "Large Goal":
                iconID = R.drawable.ic_achievement_large_goal;
                break;
            case "Meeting Deadline":
                iconID = R.drawable.ic_achievement_cips_completion;
                break;
            case "Overcoming Blocker":
                iconID = R.drawable.ic_achievement_information_completion;
                break;
            case "Task Size":
                iconID = R.drawable.ic_achievement_streak;
                break;
            case "Ability Boost":
                iconID = R.drawable.ic_achievement_growth;
                break;
            case "Aligned Expectation":
                iconID = R.drawable.ic_achievement_growth;
                break;
        }
        Drawable drawable = ContextCompat.getDrawable(context.getApplicationContext(),iconID);
        viewHolder.achievementImageView.setImageDrawable(drawable);

        viewHolder.achievementTextView.setText(achievementList.get(index).getAchievementName());

//        //Set on Click Listener to View Card
        viewHolder.cardView.setOnClickListener(v -> {
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

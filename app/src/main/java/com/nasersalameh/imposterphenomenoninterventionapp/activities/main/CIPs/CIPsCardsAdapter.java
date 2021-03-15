package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.CIPs;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.CIPsResponse;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

import java.util.ArrayList;

public class CIPsCardsAdapter extends RecyclerView.Adapter<CIPsCardsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<CIPsResponse> responsesList;

    private Activity mainActivity;

    public CIPsCardsAdapter(Context context, ArrayList<CIPsResponse> responsesList, Activity mainActivity){
        this.layoutInflater = LayoutInflater.from(context);
        this.responsesList = responsesList;

        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_cips_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        //bind Card with Behaviour

        CIPsResponse response = responsesList.get(index);

        //calculate needed scores
        response.calculateScoreValues();

        String responseDate = DateConverter.getDateFromUnixTime(response.getResponseDate()/1000);
        viewHolder.completionDateTextView.setText("CIPs Completed On: " + responseDate);
        viewHolder.scoreTextView.setText("Clance IP Score: " + response.getCipsScore()+"/100.");
        viewHolder.resultTextView.setText("Clance IP Result: " + response.getCipsResult());

        viewHolder.abilityScoreTextView.setText(response.getAbilityScore()+"/15");
        viewHolder.achievementScoreTextView.setText(response.getAchievementScore()+"/15");
        viewHolder.perfectionismScoreTextView.setText(response.getPerfectionismScore()+"/15");

        viewHolder.abilityScaleBar.setMax(15);
        viewHolder.abilityScaleBar.setProgress(response.getAbilityScore());
        viewHolder.achievementScaleBar.setMax(15);
        viewHolder.achievementScaleBar.setProgress(response.getAchievementScore());
        viewHolder.perfectionismScaleBar.setMax(15);
        viewHolder.perfectionismScaleBar.setProgress(response.getPerfectionismScore());

        //Drop down button
        viewHolder.cipsButton.setOnClickListener(v -> {
            if(viewHolder.expandableView.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(viewHolder.cardView, new AutoTransition());
                viewHolder.expandableView.setVisibility(View.VISIBLE);
                viewHolder.cipsButton.setBackgroundResource(R.drawable.ic_keyboard_arrow_up);
            }
            else{
                TransitionManager.beginDelayedTransition(viewHolder.cardView, new AutoTransition());
                viewHolder.expandableView.setVisibility(View.GONE);
                viewHolder.cipsButton.setBackgroundResource(R.drawable.ic_keyboard_arrow_down);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return responsesList.size();
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        ConstraintLayout expandableView;

        TextView completionDateTextView;
        TextView scoreTextView;
        TextView resultTextView;

        Button cipsButton;

        TextView abilityScoreTextView;
        ProgressBar abilityScaleBar;

        TextView achievementScoreTextView;
        ProgressBar achievementScaleBar;

        TextView perfectionismScoreTextView;
        ProgressBar perfectionismScaleBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.cipsCardView);

            expandableView = itemView.findViewById(R.id.cipsCardExpandableView);
            completionDateTextView = itemView.findViewById(R.id.cipsCardCompletionDateTextView);
            scoreTextView = itemView.findViewById(R.id.cipsCardScoreTextView);
            resultTextView = itemView.findViewById(R.id.cipsCardResultTextView);

            cipsButton = itemView.findViewById(R.id.cipsCardArrowDown);

            abilityScoreTextView = itemView.findViewById(R.id.cipsCardAbilityScoreTextView);
            abilityScaleBar = itemView.findViewById(R.id.cipsCardAbilityScaleBar);

            achievementScoreTextView = itemView.findViewById(R.id.cipsCardAchivementScoreTextView);
            achievementScaleBar = itemView.findViewById(R.id.cipsCardAchievementScaleBar);

            perfectionismScoreTextView = itemView.findViewById(R.id.cipsCardPerfectionismScoreTextView);
            perfectionismScaleBar = itemView.findViewById(R.id.cipsCardPerfectionismScaleBar);
        }
    }



}

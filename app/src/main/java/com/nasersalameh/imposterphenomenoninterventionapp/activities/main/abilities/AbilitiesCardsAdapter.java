package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities;
import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;

import java.util.ArrayList;

import kotlin.reflect.KVisibility;

public class AbilitiesCardsAdapter extends RecyclerView.Adapter<AbilitiesCardsAdapter.ViewHolder> {

    private final Activity mainActivity;
    private ViewHolder cardView;

    private LayoutInflater layoutInflater;
    private ArrayList<Ability> abilitiesList;

    private Context context;

    private RecyclerView recyclerView;

    public AbilitiesCardsAdapter(Context context, ArrayList<Ability> abilitiesList, Activity mainActivity, RecyclerView recyclerView){
        this.layoutInflater = LayoutInflater.from(context);
        this.abilitiesList = abilitiesList;

        this.context = context;
        this.mainActivity = mainActivity;
        this.recyclerView =recyclerView;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_abilities_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //Bind card view
        cardView = viewHolder;

        //Get ability
        Ability currentAbility = abilitiesList.get(index);

        viewHolder.abilitiesCardProgressBar.setProgress(currentAbility.getProgress());

        viewHolder.abilitiesCardNameTextView.setText(currentAbility.getName());

        //Drop down button
        viewHolder.abilitiesCardButton.setOnClickListener(v -> {
            if(viewHolder.expandableView.getVisibility() == View.GONE){
                TransitionManager.beginDelayedTransition(viewHolder.cardView, new AutoTransition());
                viewHolder.expandableView.setVisibility(View.VISIBLE);
                viewHolder.abilitiesCardButton.setBackgroundResource(R.drawable.ic_keyboard_arrow_up);
            }
            else{
                TransitionManager.beginDelayedTransition(viewHolder.cardView, new AutoTransition());
                viewHolder.expandableView.setVisibility(View.GONE);
                viewHolder.abilitiesCardButton.setBackgroundResource(R.drawable.ic_keyboard_arrow_down);
            }
        });

        int level = currentAbility.getLevel();
        viewHolder.abilitiesCardNameLevelView.setText("Level: "+ level);
        viewHolder.abilitiesCardNameExpView.setText("Exp: " + currentAbility.getExperience()+"/"+currentAbility.getLevelExp(level+1));

        viewHolder.abilitiesCardNameDetailsView.setText(currentAbility.getDetails());

        //ChipGroup
        ChipGroup chipGroup = viewHolder.abilitiesCardImproveChipGroup;

        //add all improvements
        for(String improvement: currentAbility.getImprovements()){
            Chip chip = (Chip) layoutInflater.inflate(R.layout.fragment_abilities_card_improve_chip,chipGroup, false);
            chip.setText(improvement);
            chipGroup.addView(chip);
        }

    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return abilitiesList.size();
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        ConstraintLayout expandableView;
        ProgressBar abilitiesCardProgressBar;

        TextView abilitiesCardNameTextView;

        Button abilitiesCardButton;

        TextView abilitiesCardNameLevelView;
        TextView abilitiesCardNameExpView;

        TextView abilitiesCardNameDetailsView;

        ChipGroup abilitiesCardImproveChipGroup;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.abilitiesCardView);

            expandableView = itemView.findViewById(R.id.abilitiesCardExpandableView);

            abilitiesCardProgressBar= itemView.findViewById(R.id.abilitiesCardProgressBar);

            abilitiesCardNameTextView= itemView.findViewById(R.id.abilitiesCardNameTextView);

            abilitiesCardButton = itemView.findViewById(R.id.abilitiesCardArrowButton);

            abilitiesCardNameLevelView= itemView.findViewById(R.id.abilitiesCardLevelTextView);
            abilitiesCardNameExpView= itemView.findViewById(R.id.abilitiesCardExpTextView);

            abilitiesCardNameDetailsView= itemView.findViewById(R.id.abilitiesCardDetailTextView);

            abilitiesCardImproveChipGroup = itemView.findViewById(R.id.abilitiesCardImproveChipGroup);

        }
    }



}

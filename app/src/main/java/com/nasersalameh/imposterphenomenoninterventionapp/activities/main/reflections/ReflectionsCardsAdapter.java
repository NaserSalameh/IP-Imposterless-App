package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.reflections;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.helpers.DateConverter;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Reflection;

import java.util.ArrayList;

public class ReflectionsCardsAdapter extends RecyclerView.Adapter<ReflectionsCardsAdapter.ViewHolder> {

    public static final int INFORMATION_ACTIVITY_RESULT = 101;

    private LayoutInflater layoutInflater;
    private ArrayList<Reflection> reflectionsList;
    private int informationIndex;

    private Activity mainActivity;

    public ReflectionsCardsAdapter(Context context, ArrayList<Reflection> reflectionsList, Activity mainActivity){
        this.layoutInflater = LayoutInflater.from(context);
        this.reflectionsList = reflectionsList;

        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_reflection_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {
        //bind Card with Behaviour
        this.informationIndex = index;
        viewHolder.reflectionNameTextView.setText(reflectionsList.get(index).getGoal().getName()+" Reflection");

        String completionDate = DateConverter.getDateFromUnixTime(reflectionsList.get(index).getGoal().getCompletionUnixDate()/1000);
        viewHolder.reflectionCardDateTextView.setText("Completed: " + completionDate);

        //Set on Click Listener to View Card
        viewHolder.cardView.setOnClickListener(v -> {

            //Start Reflection Activity
            Intent viewReflectionActivity = new Intent(mainActivity, ReflectionCardActivity.class);

            //Add current Reflection to intent
            viewReflectionActivity.putExtra("Reflection", reflectionsList.get(index));

            mainActivity.startActivity(viewReflectionActivity);
        });

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return reflectionsList.size();
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        TextView reflectionNameTextView;
        TextView reflectionCardDateTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.reflectionCardView);

            reflectionNameTextView = itemView.findViewById(R.id.reflectionCardNameTextView);
            reflectionCardDateTextView = itemView.findViewById(R.id.reflectionCardDateTextView);
        }
    }



}

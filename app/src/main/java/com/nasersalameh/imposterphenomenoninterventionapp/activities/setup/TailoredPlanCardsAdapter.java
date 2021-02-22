package com.nasersalameh.imposterphenomenoninterventionapp.activities.setup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;

import java.util.ArrayList;

public class TailoredPlanCardsAdapter extends RecyclerView.Adapter<TailoredPlanCardsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> tailoredPlan;

    public TailoredPlanCardsAdapter(Context context, ArrayList<String> tailoredPlan){
        this.layoutInflater = LayoutInflater.from(context);
        this.tailoredPlan = tailoredPlan;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.activity_setup_plan_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //bind Card with Behaviour
        String behaviour = tailoredPlan.get(index);
        String severity ="";
        switch(index){
            case 0: severity = "High";
            break;
            case 1: severity = "Medium";
            break;
            case 2: severity = "Low";
            break;
        }

        viewHolder.behaviourText.setText(behaviour);
        viewHolder.severityText.setText("SEVERITY: " + severity);

        //Add Links
//        viewHolder.infoButton;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return tailoredPlan.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView behaviourText;
        TextView severityText;

        FloatingActionButton infoButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            behaviourText = itemView.findViewById(R.id.behaviourTextView);
            severityText = itemView.findViewById(R.id.severityTextView);

            infoButton = itemView.findViewById(R.id.infoFloatingButton);

        }
    }

}

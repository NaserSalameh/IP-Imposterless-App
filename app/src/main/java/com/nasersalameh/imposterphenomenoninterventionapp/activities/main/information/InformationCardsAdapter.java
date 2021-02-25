package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;

public class InformationCardsAdapter extends RecyclerView.Adapter<InformationCardsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Information> informationList;

    public InformationCardsAdapter(Context context, ArrayList<Information> informationList){
        this.layoutInflater = LayoutInflater.from(context);
        this.informationList = informationList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_information_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //bind Card with Behaviour

        //Add Links
//        viewHolder.infoButton;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return informationList.size();
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

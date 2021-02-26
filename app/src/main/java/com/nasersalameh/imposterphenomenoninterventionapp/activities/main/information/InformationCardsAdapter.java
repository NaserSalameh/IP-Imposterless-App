package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
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
        viewHolder.informationNameText.setText(informationList.get(index).getInformationName());
        viewHolder.informationDetailText.setText(informationList.get(index).getInformationDetails());

        viewHolder.informationProgress.setProgress(informationList.get(index).getProgress());
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemCount() {
        return informationList.size();
    }

    //Tie the UI to the adapter
    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView informationNameText;
        TextView informationDetailText;
        ProgressBar informationProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            informationNameText = itemView.findViewById(R.id.cardNameTextView);
            informationDetailText = itemView.findViewById(R.id.cardDetailsTextView);

            informationProgress = itemView.findViewById(R.id.cardProgressBar);

        }
    }




}

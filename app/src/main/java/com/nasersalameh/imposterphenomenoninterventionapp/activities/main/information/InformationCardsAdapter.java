package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;

import java.util.ArrayList;

public class InformationCardsAdapter extends RecyclerView.Adapter<InformationCardsAdapter.ViewHolder> {

    public static final int INFORMATION_ACTIVITY_RESULT = 101;
    private ViewHolder cardView;

    private LayoutInflater layoutInflater;
    private ArrayList<Information> informationList;
    private int informationIndex;

    private Activity mainActivity;

    public InformationCardsAdapter(Context context, ArrayList<Information> informationList, Activity mainActivity){
        this.layoutInflater = LayoutInflater.from(context);
        this.informationList = informationList;

        this.mainActivity = mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = layoutInflater.inflate(R.layout.fragment_information_card,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int index) {

        //Bind card view
        cardView = viewHolder;

        //bind Card with Behaviour
        this.informationIndex = index;
        viewHolder.informationNameText.setText(informationList.get(index).getInformationName());
        viewHolder.informationDetailText.setText(informationList.get(index).getInformationDetails());

        //get progress and  Information progress max
        int progress = informationList.get(index).getProgress();
        int maxProgress = viewHolder.informationProgress.getMax();
        int setProgress = (int) (((double) progress / 100.0) * maxProgress);
        viewHolder.informationProgress.setProgress(setProgress);


        //Set on Click Listener to View Card
        viewHolder.cardView.setOnClickListener(v -> {

                //Start Information Activity
                Intent startInformationCardActivity = new Intent(mainActivity, InformationCardActivity.class);

                //Add current information to intent
                startInformationCardActivity.putExtra("Information", informationList.get(index));

                mainActivity.startActivity(startInformationCardActivity);
            });

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
    public class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardView;

        TextView informationNameText;
        TextView informationDetailText;
        ProgressBar informationProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.informationCardView);

            informationNameText = itemView.findViewById(R.id.cardNameTextView);
            informationDetailText = itemView.findViewById(R.id.cardDetailsTextView);

            informationProgress = itemView.findViewById(R.id.cardProgressBar);

        }



    }
}

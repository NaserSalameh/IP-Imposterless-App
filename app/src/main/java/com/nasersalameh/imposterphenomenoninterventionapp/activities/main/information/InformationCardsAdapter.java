package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.information;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.database.InformationData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.LogData;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Information;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Log;

import java.util.ArrayList;

public class InformationCardsAdapter extends RecyclerView.Adapter<InformationCardsAdapter.ViewHolder> {

    public static final int INFORMATION_ACTIVITY_RESULT = 101;

    private LayoutInflater layoutInflater;
    private ArrayList<Information> informationList;
    private int informationIndex;

    private Activity mainActivity;

    private DatabaseHelper databaseHelper;
    private LogData logData;

    public InformationCardsAdapter(Context context, ArrayList<Information> informationList, Activity mainActivity){
        this.layoutInflater = LayoutInflater.from(context);
        this.informationList = informationList;

        this.mainActivity = mainActivity;

        databaseHelper = new DatabaseHelper(mainActivity);
        logData = new LogData(databaseHelper);
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

        //get progress and Information progress max
        int progress = informationList.get(index).getProgress();

        viewHolder.informationProgress.setProgress(progress);

        //Set current information appearance if locked/unlocked
        if(informationList.get(index).isUnlocked()) {
            viewHolder.cardView.setEnabled(true);
            viewHolder.informationCardConstraintLayout.setAlpha(1f);
            //set status image
            if(progress != 100)
                viewHolder.informationCardStatusImageView.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_baseline_lock_open_24));
            else
                viewHolder.informationCardStatusImageView.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_baseline_task_alt_24));
        }
        else {
            viewHolder.cardView.setEnabled(false);
            viewHolder.informationCardConstraintLayout.setAlpha(.75f);
            viewHolder.informationCardStatusImageView.setImageDrawable(mainActivity.getDrawable(R.drawable.ic_baseline_lock_24));

        }

        //Set next information locked/unlocked, based on current information progress
        if(informationList.get(index).getProgress() == 100){

            Information nextInformation = informationList.get(index + 1);
            nextInformation.setUnlocked(true);

            //update progress to set as unlocked
            DatabaseHelper databaseHelper = new DatabaseHelper(mainActivity);
            InformationData informationData = new InformationData(databaseHelper);
            informationData.updateInformationProgress(nextInformation.getInformationName(),nextInformation.getProgress());
        }

        //Set on Click Listener to View Card
        viewHolder.cardView.setOnClickListener(v -> {
                logData.insertNewLog(new Log("Information","Selected Information " + informationList.get(index).getInformationName()));

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

        ConstraintLayout informationCardConstraintLayout;

        ImageView informationCardStatusImageView;
        TextView informationNameText;
        TextView informationDetailText;
        ProgressBar informationProgress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.informationCardView);

            informationCardConstraintLayout = itemView.findViewById(R.id.informationCardConstraintLayout);

            informationCardStatusImageView = itemView.findViewById(R.id.informationCardStatusimageView);

            informationNameText = itemView.findViewById(R.id.cardNameTextView);
            informationDetailText = itemView.findViewById(R.id.cardDetailsTextView);

            informationProgress = itemView.findViewById(R.id.cardProgressBar);

        }
    }



}

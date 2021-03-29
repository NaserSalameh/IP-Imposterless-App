package com.nasersalameh.imposterphenomenoninterventionapp.activities.setup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.text.Layout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nasersalameh.imposterphenomenoninterventionapp.R;
import com.nasersalameh.imposterphenomenoninterventionapp.database.ContentData;
import com.nasersalameh.imposterphenomenoninterventionapp.database.DatabaseHelper;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Content;

import java.util.ArrayList;

public class TailoredPlanCardsAdapter extends RecyclerView.Adapter<TailoredPlanCardsAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<String> tailoredPlan;

    private Context context;
    private Activity mainActivity;
    private View anchor;

    public TailoredPlanCardsAdapter(Context context, ArrayList<String> tailoredPlan, View anchor){
        this.layoutInflater = LayoutInflater.from(context);
        this.tailoredPlan = tailoredPlan;

        this.context = context;
        mainActivity = (Activity) context;
        this.anchor = anchor;
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
        viewHolder.severityText.setText("Importance: " + severity);

        setUpFloatingButton(viewHolder.infoButton, behaviour);
    }


    private void setUpFloatingButton(FloatingActionButton infoButton, String behaviour) {
        DatabaseHelper databaseHelper = new DatabaseHelper(context);
        ContentData contentData = new ContentData(databaseHelper);
        Content content = contentData.getContentById("BEHAVIOUR_" + behaviour);
        infoButton.setOnClickListener(v -> {
            createPopup(content.getName(),content.getContent());
        });
    }

    private void createPopup(String popupTitle, String popupText){
        //Create and inflate layout
        LayoutInflater layoutInflater = ((Activity)context).getLayoutInflater();
        ViewGroup container = (ViewGroup) layoutInflater.inflate(R.layout.help_popup,null);

        // which view you pass in doesn't matter, it is only used for the window tolken
        @SuppressLint("WrongViewCast")
        View constraintLayout = anchor.findViewById(R.id.setupPlanConstraintLayout);

        //if view is in profile instead
        if(constraintLayout == null)
            constraintLayout = anchor.findViewById(R.id.profileConstraintLayout);

        final PopupWindow popupWindow = new PopupWindow(container, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setElevation(30);

        //Handler to thread sleep and slow down process
        Handler handler=new Handler();
        View finalConstraintLayout = constraintLayout;
        Runnable r= () -> popupWindow.showAtLocation(finalConstraintLayout, Gravity.CENTER, 0, 0);
        handler.postDelayed(r, 250);

        TextView popupTitleTextView = container.findViewById(R.id.helpTitleTextView);
        popupTitleTextView.setText(popupTitle);

        TextView popupTextView = container.findViewById(R.id.helpDetailsTextView);
        popupTextView.setText(popupText);

        Button helpPopupCloseButton = container.findViewById(R.id.helpPopupCloseButton);
        helpPopupCloseButton.setOnClickListener(v -> popupWindow.dismiss());
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

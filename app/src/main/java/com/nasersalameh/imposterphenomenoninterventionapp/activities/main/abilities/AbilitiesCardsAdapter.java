package com.nasersalameh.imposterphenomenoninterventionapp.activities.main.abilities;
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
import com.nasersalameh.imposterphenomenoninterventionapp.models.Ability;
import com.nasersalameh.imposterphenomenoninterventionapp.models.Achievement;

import java.util.ArrayList;

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

        ImageView achievementImageView;

        TextView achievementTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }



}

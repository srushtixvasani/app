package com.example.dailynotdilly.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.AfternoonActivity;
import com.example.dailynotdilly.BreatheActivity;
import com.example.dailynotdilly.EveningActivity;
import com.example.dailynotdilly.JournalActivity;
import com.example.dailynotdilly.MainActivity;
import com.example.dailynotdilly.ManifestActivity;
import com.example.dailynotdilly.MorningActivity;
import com.example.dailynotdilly.R;
import com.example.dailynotdilly.ToDoListActivity;
import com.example.dailynotdilly.models.Feature;

import java.util.ArrayList;
import java.util.List;

public class FeatureAdapter extends
        RecyclerView.Adapter<FeatureAdapter.MyViewHolder> {

    private List<Feature> featureList;
    private Context context;
    private SelectedFeature selectedFeature;

    public FeatureAdapter(List<Feature> featureList, SelectedFeature selectedFeature) {
        this.featureList = featureList;
        this.selectedFeature = selectedFeature;
    }

    @NonNull
    @Override
    public FeatureAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.feature_cardviews, null));
    }

    @Override
    public void onBindViewHolder(@NonNull FeatureAdapter.MyViewHolder holder, int position) {
        Feature feature = featureList.get(position);

        Integer feature_image = feature.getImage();
        holder.imageView.setImageResource(feature_image);

    }

    @Override
    public int getItemCount() {
        return featureList.size();
    }

    public interface SelectedFeature {

        void selectedFeature(Feature feature);
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final Intent intent;

                    if (getAdapterPosition() == 0){
                        intent = new Intent(context, BreatheActivity.class);
                    } else if (getAdapterPosition() == 1){
                        intent = new Intent(context, JournalActivity.class);
                    } else if (getAdapterPosition() == 2) {
                        intent = new Intent(context, ToDoListActivity.class);
                    } else {
                        intent = new Intent(context, ManifestActivity.class);
                    }

                    context.startActivity(intent);
                }
            });
        }
    }
}

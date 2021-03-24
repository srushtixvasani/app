package com.example.dailynotdilly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.ManifestFeature;

import java.util.List;

public class ManifestFeatureAdapter extends RecyclerView.Adapter<ManifestFeatureAdapter.ViewHolder> {
   private List<ManifestFeature> manifestFeatureList;

    public ManifestFeatureAdapter(List<ManifestFeature> manifestFeatureList) {
        this.manifestFeatureList = manifestFeatureList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manifest_list_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManifestFeatureAdapter.ViewHolder holder, int position) {

        holder.manifestFeature = manifestFeatureList.get(position);
        holder.manifestName.setText(manifestFeatureList.get(position).getName());
        holder.manifestImageView.setImageResource(manifestFeatureList.get(position).getImageURL());

    }

    @Override
    public int getItemCount() {
        return manifestFeatureList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        final View parentView;
        final ImageView manifestImageView;
        final TextView manifestName;

        ManifestFeature manifestFeature;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            // instantiate items above
            parentView = itemView;
            manifestName = itemView.findViewById(R.id.manifest_item_text);
            manifestImageView = itemView.findViewById(R.id.manifest_item_image);


        }
    }
}

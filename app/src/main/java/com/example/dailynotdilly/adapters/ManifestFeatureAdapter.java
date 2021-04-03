package com.example.dailynotdilly.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.AddManifestFragment;
import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.ManifestFeature;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.dailynotdilly.AddManifestFragment.REQUEST_CODE_IMAGE;

public class ManifestFeatureAdapter extends RecyclerView.Adapter<ManifestFeatureAdapter.ViewHolder> {
   private List<ManifestFeature> manifestFeatureList;
   private eachRowOnClickListener listener;
   AddManifestFragment addManifestFragment;

   Context context;

    public ManifestFeatureAdapter(List<ManifestFeature> manifestFeatureList) {
        this.manifestFeatureList = manifestFeatureList;
    }

    public void setEachRowOnClickListener(eachRowOnClickListener listener){
        this.listener =listener;
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
//
        String imageURL = manifestFeatureList.get(position).getImageURL();

//        Uri uri = Uri.parse(imageURL).normalizeScheme();
//
//        holder.manifestImageView.setImageURI(uri);
      //  holder.manifestImageView.setImageURI(uri);

        Picasso.with(holder.manifestName.getContext())
                .load(imageURL)
                .placeholder(R.drawable.loading)
                .into(holder.manifestImageView);

        // holder.manifestImageView.im(manifestFeatureList.get(position).getImageURL());
//        holder.manifestImageView.setImageResource(manifestFeatureList.get(position).getImageURL());

//       Intent intent = Intent.parseUri();
//        Bitmap bitmap = (Bitmap) intent.getParcelableExtra("BitmapImage");

//       holder.manifestImageView.setImageBitmap(bitmap);

//        Picasso.with(holder.manifestName.getContext())
//                .load(manifestFeatureList.get(position)
//                .getImageURL()).placeholder(R.drawable.dream_house)
//                .into(holder.manifestImageView);

    }

    @Override
    public int getItemCount() {
        return manifestFeatureList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        final View parentView;
        final ImageView manifestImageView;
        final TextView manifestName;

        ManifestFeature manifestFeature;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            // instantiate items above
            parentView = itemView;
            manifestName = itemView.findViewById(R.id.manifest_item_text);
            manifestImageView = itemView.findViewById(R.id.manifest_item_image);


            // when user clicks each item, opens up detailed description of the manifestation
            parentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(parentView, position);
                        }
                    }

                }
            });
        }
    }

    // interface
    public interface eachRowOnClickListener {
        void onItemClick(View itemView, int position);
    }

}

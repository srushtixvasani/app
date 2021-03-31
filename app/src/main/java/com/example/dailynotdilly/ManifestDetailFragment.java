package com.example.dailynotdilly;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.fragment.app.Fragment;

import com.example.dailynotdilly.models.ManifestFeature;
import com.google.android.material.imageview.ShapeableImageView;

public class ManifestDetailFragment extends Fragment {

    private ManifestFeature manifestFeature;

    static ManifestDetailFragment newInstance(ManifestFeature manifestFeature){

        ManifestDetailFragment fragment = new ManifestDetailFragment();
        Bundle args = new Bundle();
        args.putSerializable("manifestFeature", manifestFeature);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            manifestFeature = (ManifestFeature) getArguments().getSerializable("manifestFeature");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.manifest_fragment_detail_item, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // set up layout
        TextView name = view.findViewById(R.id.manifest_name);
        TextView description = view.findViewById(R.id.manifest_description);
        ImageView image = view.findViewById(R.id.manifest_image);
        AppCompatImageButton backButton = view.findViewById(R.id.back);


        name.setText(manifestFeature.getName());
        description.setText(manifestFeature.getDescription());
//        image.setImageResource(manifestFeature.getImageURL());

        //set back button to go back to manifest feature
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ManifestActivity.class);
                startActivity(intent);
            }
        });



    }
}

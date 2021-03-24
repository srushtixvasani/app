package com.example.dailynotdilly;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dailynotdilly.models.ManifestFeature;

import java.util.ArrayList;
import java.util.List;

public class ManifestActivity extends AppCompatActivity {

    private static final String TAG_LIST_FRAGMENT = "TAG_LIST_FRAGMENT";
    ManifestFragment manifestFragment;
    List<ManifestFeature> exampleManifests;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manifest_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        if (savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            manifestFragment = new ManifestFragment();
            fragmentTransaction.add(R.id.manifest_feature, manifestFragment, TAG_LIST_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            manifestFragment = (ManifestFragment) fragmentManager.findFragmentByTag(TAG_LIST_FRAGMENT);
        }

        exampleManifests = new ArrayList<>();
        exampleManifests.add(new ManifestFeature("Dream house", "Buy dream house", R.drawable.dream_house));
        exampleManifests.add(new ManifestFeature("Dream location", "Live here", R.drawable.dream_location));
        exampleManifests.add(new ManifestFeature("Dream holiday", "Go on this holiday", R.drawable.dream_holiday));
        exampleManifests.add(new ManifestFeature("Dream car", "Buy dream car", R.drawable.dream_car));

        manifestFragment.setManifestFeature(exampleManifests);
    }

}

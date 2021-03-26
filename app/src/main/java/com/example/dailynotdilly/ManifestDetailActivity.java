package com.example.dailynotdilly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.dailynotdilly.models.ManifestFeature;

public class ManifestDetailActivity extends AppCompatActivity {

    ManifestDetailFragment manifestDetailFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.manifest_detail_activity);

        ManifestFeature manifestFeature = (ManifestFeature) getIntent().getSerializableExtra("manifestation");
        if (savedInstanceState == null) {
            manifestDetailFragment = ManifestDetailFragment
                    .newInstance(manifestFeature);
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.manifest_detail_fragment, manifestDetailFragment);
            fragmentTransaction.commit();
        }

    }
}
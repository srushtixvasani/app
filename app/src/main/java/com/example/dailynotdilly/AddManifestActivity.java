package com.example.dailynotdilly;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

public class AddManifestActivity extends AppCompatActivity {

    public static final String TAG_ADD_FRAGMENT = "TAG_ADD_FRAGMENT";
    AddManifestFragment addManifestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_manifest_activity);

        FragmentManager fragmentManager = getSupportFragmentManager();
        addManifestFragment = new AddManifestFragment();


        if (savedInstanceState ==null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.add_manifest_activity, addManifestFragment, TAG_ADD_FRAGMENT);
            fragmentTransaction.commit();
        } else {
            addManifestFragment = (AddManifestFragment) fragmentManager.findFragmentByTag(TAG_ADD_FRAGMENT);
        }


    }
}
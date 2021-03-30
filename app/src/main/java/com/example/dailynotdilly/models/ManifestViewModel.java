package com.example.dailynotdilly.models;

import android.app.Application;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.data.ManifestDatabase;

import java.util.List;

public class ManifestViewModel extends AndroidViewModel {

    private LiveData<List<ManifestFeature>> manifests;

    public ManifestViewModel(@NonNull Application application) {
        super(application);

        ManifestDatabase db = ManifestDatabase.getInstance(this.getApplication());
        manifests = db.manifestDao().loadAllManifests();
    }

    // public interface used to extract manifests
    public LiveData<List<ManifestFeature>> getManifests() {
        return manifests;
    }



}

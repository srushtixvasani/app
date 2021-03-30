package com.example.dailynotdilly.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;
import androidx.room.*;

import com.example.dailynotdilly.models.ManifestFeature;

import java.util.List;

@Dao
public interface ManifestDao {

    @Insert
    long insert(ManifestFeature manifestFeature);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    int updateManifest(ManifestFeature manifestFeature);

    @Query("DELETE FROM manifest_table WHERE id = :id")
    int deleteManifest(int id);

    // get all manifests
    @Query("SELECT * FROM manifest_table")
    LiveData<List<ManifestFeature>> loadAllManifests();

    // get a manifest by the id
    @Query("SELECT * FROM manifest_table WHERE id = :id")
    LiveData<ManifestFeature> loadManifestByID(int id);

    // deleting obsolete data not required; i.e all the manifests if necessary
    @Query("DELETE FROM manifest_table")
    void deleteAllBoard();



}


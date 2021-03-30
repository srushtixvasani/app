package com.example.dailynotdilly.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.dailynotdilly.models.ManifestFeature;

@Database(entities = {ManifestFeature.class}, version = 1, exportSchema = false)
public abstract class ManifestDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "manifest_db";
    private static ManifestDatabase INSTANCE;

    public static ManifestDatabase getInstance(Context context) {

        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(
                    context.getApplicationContext(), ManifestDatabase.class,
                    ManifestDatabase.DATABASE_NAME
            ).build();
        }
        return INSTANCE;
    }

    public abstract ManifestDao manifestDao() {}


}

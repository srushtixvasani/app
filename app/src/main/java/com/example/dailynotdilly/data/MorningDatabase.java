package com.example.dailynotdilly.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dailynotdilly.models.MorningRoutine;
import com.example.dailynotdilly.utils.Converter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MorningRoutine.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class MorningDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 5;
    public static final String DATABASE_NAME = "morning_database";
    private static volatile MorningDatabase INSTANCE;

    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final RoomDatabase.Callback mRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);
            databaseExecutor.execute(() -> {
                // invoke the Dao
                MorningDao morningDao = INSTANCE.morningDao();

                // delete tasks
                morningDao.deleteAllHabits();

                //write

            });
        }
    };

    public static MorningDatabase getDatabase(final Context context) {

        // confirm that everything goes back to the background thread
        if (INSTANCE == null) {
            synchronized (MorningDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), MorningDatabase.class,
                            DATABASE_NAME).addCallback(mRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    protected abstract MorningDao morningDao();




}

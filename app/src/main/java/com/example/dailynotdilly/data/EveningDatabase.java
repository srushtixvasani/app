package com.example.dailynotdilly.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dailynotdilly.models.EveningRoutine;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {EveningRoutine.class}, version = 1, exportSchema = false)
public abstract class EveningDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 5;
    public static final String DATABASE_NAME = "evening_database";
    private static volatile EveningDatabase INSTANCE;

    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final RoomDatabase.Callback eRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);
            databaseExecutor.execute(() -> {
                // invoke the Dao
                EveningDao eveningDao = INSTANCE.eveningDao();

                // delete tasks
                eveningDao.deleteAllHabits();

                //write

            });
        }
    };

    public static EveningDatabase getDatabase(final Context context) {

        // confirm that everything goes back to the background thread
        if (INSTANCE == null) {
            synchronized (EveningDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), EveningDatabase.class,
                            DATABASE_NAME).addCallback(eRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    protected abstract EveningDao eveningDao();

}


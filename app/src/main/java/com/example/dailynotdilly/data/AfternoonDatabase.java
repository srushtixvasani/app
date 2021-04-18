package com.example.dailynotdilly.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dailynotdilly.models.AfternoonRoutine;
import com.example.dailynotdilly.utils.Converter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AfternoonRoutine.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class AfternoonDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 5;
    public static final String DATABASE_NAME = "afternoon_database";
    private static volatile AfternoonDatabase INSTANCE;

    public static final ExecutorService databaseExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final RoomDatabase.Callback aRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase database) {
            super.onCreate(database);
            databaseExecutor.execute(() -> {
                // invoke the Dao
                AfternoonDao afternoonDao = INSTANCE.afternoonDao();

                // delete tasks
                afternoonDao.deleteAllHabits();

                //write

            });
        }
    };

    public static AfternoonDatabase getDatabase(final Context context) {

        // confirm that everything goes back to the background thread
        if (INSTANCE == null) {
            synchronized (AfternoonDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), AfternoonDatabase.class,
                            DATABASE_NAME).addCallback(aRoomDatabaseCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    protected abstract AfternoonDao afternoonDao();

}

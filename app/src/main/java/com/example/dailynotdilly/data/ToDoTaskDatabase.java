package com.example.dailynotdilly.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.dailynotdilly.models.ToDoTask;
import com.example.dailynotdilly.utils.Converter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {ToDoTask.class}, version = 1, exportSchema = false)
@TypeConverters({Converter.class})
public abstract class ToDoTaskDatabase extends RoomDatabase {

    public static final int NUMBER_OF_THREADS = 4;
    public static final String DATABASE_NAME = "to_do_database";
    private static volatile ToDoTaskDatabase INSTANCE;

    public static final ExecutorService databaseWriterExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    public static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            databaseWriterExecutor.execute(() -> {
                // invoke the Dao
                ToDoTaskDao toDoTaskDao = INSTANCE.toDoTaskDao();
                // delete all the tasks
                toDoTaskDao.deleteAll();

                // Write

            });
        }
    };

    public static ToDoTaskDatabase getDatabase(final Context context) {

        // confirm that everything goes back to the background thread
        if (INSTANCE == null) {
            synchronized (ToDoTaskDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), ToDoTaskDatabase.class,
                            DATABASE_NAME).addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract ToDoTaskDao toDoTaskDao();

}

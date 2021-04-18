package com.example.dailynotdilly.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.models.AfternoonRoutine;

import java.util.List;

public class AfternoonRepository {

    private final AfternoonDao afternoonDao;
    private final LiveData<List<AfternoonRoutine>> allHabits;

    public AfternoonRepository(Application application) {
        AfternoonDatabase database = AfternoonDatabase.getDatabase(application);

        afternoonDao = database.afternoonDao();
        allHabits = afternoonDao.getAllHabits();
    }


    public LiveData<List<AfternoonRoutine>> getHabits() {
        return allHabits;
    }

    public void insertHabit(AfternoonRoutine afternoonRoutine) {
        AfternoonDatabase.databaseExecutor.execute( () -> afternoonDao.insertHabit(afternoonRoutine));
    }

    public LiveData<AfternoonRoutine> getHabit(long id) {
        return afternoonDao.getHabit(id);
    }

    public void updateHabit(AfternoonRoutine afternoonRoutine) {
        AfternoonDatabase.databaseExecutor.execute( () -> afternoonDao.updateHabit(afternoonRoutine));
    }

    public void deleteHabit(AfternoonRoutine afternoonRoutine) {
        AfternoonDatabase.databaseExecutor.execute( () -> afternoonDao.deleteHabit(afternoonRoutine));
    }
}

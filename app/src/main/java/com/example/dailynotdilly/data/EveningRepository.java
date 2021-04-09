package com.example.dailynotdilly.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.models.EveningRoutine;

import java.util.List;

public class EveningRepository {

    private final EveningDao eveningDao;
    private final LiveData<List<EveningRoutine>> allHabits;

    //Constructor
    public EveningRepository(Application application) {
        EveningDatabase db = EveningDatabase.getDatabase(application);

        eveningDao = db.eveningDao();
        allHabits = eveningDao.getAllHabits();
    }

    public LiveData<List<EveningRoutine>> getHabits() {
        return allHabits;
    }

    public void insertHabit(EveningRoutine eveningRoutine) {
        EveningDatabase.databaseExecutor.execute( () -> eveningDao.insertHabit(eveningRoutine));
    }

    public LiveData<EveningRoutine> getHabit(long id) {
        return eveningDao.getHabit(id);
    }

    public void updateHabit(EveningRoutine eveningRoutine) {
        EveningDatabase.databaseExecutor.execute( () -> eveningDao.updateHabit(eveningRoutine));
    }

    public void deleteHabit(EveningRoutine eveningRoutine) {
        EveningDatabase.databaseExecutor.execute( () -> eveningDao.deleteHabit(eveningRoutine));
    }


}

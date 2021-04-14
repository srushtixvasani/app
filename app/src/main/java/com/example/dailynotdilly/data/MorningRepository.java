package com.example.dailynotdilly.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.models.MorningRoutine;

import java.util.List;

public class MorningRepository {

    private final MorningDao morningDao;
    private final LiveData<List<MorningRoutine>> allHabits;

    //Constructor
    public MorningRepository(Application application) {
        MorningDatabase db = MorningDatabase.getDatabase(application);

        morningDao = db.morningDao();
        allHabits = morningDao.getAllHabits();
    }

    public LiveData<List<MorningRoutine>> getHabits() {
        return allHabits;
    }

    public void insertHabit(MorningRoutine morningRoutine) {
        MorningDatabase.databaseExecutor.execute( () -> morningDao.insertHabit(morningRoutine));
    }

    public LiveData<MorningRoutine> getHabit(long id) {
        return morningDao.getHabit(id);
    }

    public void updateHabit(MorningRoutine morningRoutine) {
        MorningDatabase.databaseExecutor.execute( () -> morningDao.updateHabit(morningRoutine));
    }

    public void deleteHabit(MorningRoutine morningRoutine) {
        MorningDatabase.databaseExecutor.execute( () -> morningDao.deleteHabit(morningRoutine));
    }


}

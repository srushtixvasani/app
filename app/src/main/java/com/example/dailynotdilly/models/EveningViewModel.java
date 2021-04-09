package com.example.dailynotdilly.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.data.EveningRepository;

import java.util.List;

public class EveningViewModel extends AndroidViewModel {

    public static EveningRepository eveningRepository;
    public final LiveData<List<EveningRoutine>> allHabits;

    public EveningViewModel(@NonNull Application application) {
        super(application);

        eveningRepository = new EveningRepository(application);
        allHabits = eveningRepository.getHabits();
    }

    public LiveData<List<EveningRoutine>> getAllHabits(){
        return allHabits;
    }

    public static void insertHabit(EveningRoutine eveningRoutine) {
        eveningRepository.insertHabit(eveningRoutine);
    }

    public LiveData<EveningRoutine> getHabit(long id) {
        return eveningRepository.getHabit(id);
    }

    public static void updateHabit(EveningRoutine eveningRoutine) {
        eveningRepository.updateHabit(eveningRoutine);
    }

    public static void deleteHabit(EveningRoutine eveningRoutine) {
        eveningRepository.deleteHabit(eveningRoutine);
    }

}

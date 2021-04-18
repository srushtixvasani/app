package com.example.dailynotdilly.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.data.AfternoonRepository;

import java.util.List;

public class AfternoonViewModel extends AndroidViewModel {

    public static AfternoonRepository afternoonRepository;
    public final LiveData<List<AfternoonRoutine>> allHabits;

    public AfternoonViewModel(@NonNull Application application) {
        super(application);

        afternoonRepository = new AfternoonRepository(application);
        allHabits = afternoonRepository.getHabits();
    }

    public LiveData<List<AfternoonRoutine>> getAllHabits(){
        return allHabits;
    }

    public static void insertHabit(AfternoonRoutine afternoonRoutine) {
        afternoonRepository.insertHabit(afternoonRoutine);
    }

    public LiveData<AfternoonRoutine> getHabit(long id) {
        return afternoonRepository.getHabit(id);
    }

    public static void updateHabit(AfternoonRoutine afternoonRoutine) {
        afternoonRepository.updateHabit(afternoonRoutine);
    }

    public static void deleteHabit(AfternoonRoutine afternoonRoutine) {
        afternoonRepository.deleteHabit(afternoonRoutine);
    }
}

package com.example.dailynotdilly.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.data.MorningRepository;

import java.util.List;

public class MorningViewModel extends AndroidViewModel {

    public static MorningRepository morningRepository;
    public final LiveData<List<MorningRoutine>> allHabits;

    public MorningViewModel(@NonNull Application application) {
        super(application);

        morningRepository = new MorningRepository(application);
        allHabits = morningRepository.getHabits();
    }

    public LiveData<List<MorningRoutine>> getAllHabits(){

        return allHabits;
    }

    public static void insertHabit(MorningRoutine morningRoutine) {
        morningRepository.insertHabit(morningRoutine);
    }

    public LiveData<MorningRoutine> getHabit(long id) {
        return morningRepository.getHabit(id);
    }

    public static void updateHabit(MorningRoutine morningRoutine) {
        morningRepository.updateHabit(morningRoutine);
    }

    public static void deleteHabit(MorningRoutine morningRoutine) {
        morningRepository.deleteHabit(morningRoutine);
    }


}

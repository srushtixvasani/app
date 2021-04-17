package com.example.dailynotdilly.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MorningSharedViewModel extends ViewModel {

    private final MutableLiveData<MorningRoutine> selectedHabit = new MutableLiveData<>();
    private boolean isEdit;

    //getters and setters
    public void selectHabit(MorningRoutine morningRoutine) {
        selectedHabit.setValue(morningRoutine);
    }

    public LiveData<MorningRoutine> getSelectedHabit() {
        return selectedHabit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }
}

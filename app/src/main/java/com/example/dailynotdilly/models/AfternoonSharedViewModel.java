package com.example.dailynotdilly.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AfternoonSharedViewModel extends ViewModel {

    private final MutableLiveData<AfternoonRoutine> selectedHabit = new MutableLiveData<>();
    private boolean isEdit;

    //getters and setters
    public void selectHabit(AfternoonRoutine afternoonRoutine) {
        selectedHabit.setValue(afternoonRoutine);
    }

    public LiveData<AfternoonRoutine> getSelectedHabit() {
        return selectedHabit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }
}

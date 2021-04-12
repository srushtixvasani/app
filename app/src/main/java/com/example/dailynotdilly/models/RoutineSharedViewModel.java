package com.example.dailynotdilly.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RoutineSharedViewModel extends ViewModel {

    private final MutableLiveData<EveningRoutine> selectedHabit = new MutableLiveData<>();
    private boolean isEdit;

    //getters and setters
    public void selectHabit(EveningRoutine eveningRoutine) {
        selectedHabit.setValue(eveningRoutine);
    }

    public LiveData<EveningRoutine> getSelectedHabit() {
        return selectedHabit;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }


}

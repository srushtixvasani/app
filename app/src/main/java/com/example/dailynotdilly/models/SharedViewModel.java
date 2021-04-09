package com.example.dailynotdilly.models;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {

   private final MutableLiveData<ToDoTask> selectedTask = new MutableLiveData<>();
   private boolean isEdit;


   // getters and setters
    public void  selectTask(ToDoTask toDoTask) {
        selectedTask.setValue(toDoTask);
    }

    public LiveData<ToDoTask> getSelectedItem() {
        return selectedTask;
    }

    public void setIsEdit(boolean isEdit) {
        this.isEdit = isEdit;
    }

    public boolean getIsEdit() {
        return isEdit;
    }

}

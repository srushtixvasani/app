package com.example.dailynotdilly.models;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.data.ToDoRepository;

import java.util.List;

public class ToDoViewModel extends AndroidViewModel {

    public static ToDoRepository repository;
    public final LiveData<List<ToDoTask>> allTasks;


    public ToDoViewModel(@NonNull Application application) {
        super(application);

        repository = new ToDoRepository(application);
        allTasks = repository.getAllTasks();

    }

    public LiveData<List<ToDoTask>> getAllTasks() {
        return allTasks;
    }

    public static void insert(ToDoTask toDoTask) {
        repository.insert(toDoTask);
    }

    public LiveData<ToDoTask> get(long id) {
        return repository.get(id);
    }

    public static void update(ToDoTask toDoTask) {
        repository.update(toDoTask);
    }

    public static void delete(ToDoTask toDoTask) {
        repository.delete(toDoTask);
    }
}

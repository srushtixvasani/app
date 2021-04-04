package com.example.dailynotdilly.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.dailynotdilly.models.ToDoTask;

import java.util.List;

public class ToDoRepository {

    private final ToDoTaskDao toDoTaskDao;
    private final LiveData<List<ToDoTask>> allTasks;

    // Constructor
    public ToDoRepository(Application application) {
        ToDoTaskDatabase database = ToDoTaskDatabase.getDatabase(application);

        toDoTaskDao = database.toDoTaskDao();
        allTasks = toDoTaskDao.getTasks();
    }

    public LiveData<List<ToDoTask>> getAllTasks() {
        return allTasks;
    }

    public void insert(ToDoTask toDoTask) {
        ToDoTaskDatabase.databaseWriterExecutor.execute( () -> toDoTaskDao.insertTask(toDoTask));
    }

    public LiveData<ToDoTask> get(long id) {
        return toDoTaskDao.get(id);
    }

    public void update(ToDoTask toDoTask) {
        ToDoTaskDatabase.databaseWriterExecutor.execute( () -> toDoTaskDao.update(toDoTask));
    }

    public void delete(ToDoTask toDoTask) {
        ToDoTaskDatabase.databaseWriterExecutor.execute( () -> toDoTaskDao.delete(toDoTask));
    }


}

package com.example.dailynotdilly.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailynotdilly.models.ToDoTask;

import java.util.List;

@Dao
public interface ToDoTaskDao {

    // implement CRUD operations

    @Insert
    void insertTask(ToDoTask toDoTask);

    @Query("DELETE FROM task_table")
    void deleteAll();

    // get All tasks
    @Query("SELECT * FROM task_table")
    LiveData<List<ToDoTask>> getTasks();

    // get one specific task
    @Query("SELECT * FROM task_table WHERE task_table.task_id == :id")
    LiveData<ToDoTask> get(long id);

    @Update
    void update(ToDoTask toDoTask);

    @Delete
    void delete(ToDoTask toDoTask);

}

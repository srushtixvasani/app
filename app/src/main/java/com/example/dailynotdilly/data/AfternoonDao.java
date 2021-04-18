package com.example.dailynotdilly.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailynotdilly.models.AfternoonRoutine;

import java.util.List;

@Dao
public interface AfternoonDao {

    // Implement CRUD functions
    @Insert
    void insertHabit(AfternoonRoutine afternoonRoutine);

    @Query("DELETE FROM afternoon_routine")
    void deleteAllHabits();

    // get all evening habits
    @Query("SELECT * FROM afternoon_routine")
    LiveData<List<AfternoonRoutine>> getAllHabits();

    // get a specific habit
    @Query("SELECT * FROM afternoon_routine WHERE afternoon_routine.habit_id == :id")
    LiveData<AfternoonRoutine> getHabit(long id);

    @Update
    void updateHabit(AfternoonRoutine afternoonRoutine);

    @Delete
    void deleteHabit(AfternoonRoutine afternoonRoutine);

}

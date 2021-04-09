package com.example.dailynotdilly.data;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailynotdilly.models.EveningRoutine;

import java.util.List;

@Dao
public interface EveningDao {

    // Implement CRUD functions
    @Insert
    void insertHabit(EveningRoutine eveningRoutine);

    @Query("DELETE FROM evening_routine")
    void deleteAllHabits();

    // get all evening habits
    @Query("SELECT * FROM evening_routine")
    LiveData<List<EveningRoutine>> getAllHabits();

    // get a specific habit
    @Query("SELECT * FROM evening_routine WHERE evening_routine.habit_id == :id")
    LiveData<EveningRoutine> getHabit(long id);

    @Update
    void updateHabit(EveningRoutine eveningRoutine);

    @Delete
    void deleteHabit(EveningRoutine eveningRoutine);



}

package com.example.dailynotdilly.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.dailynotdilly.models.MorningRoutine;

import java.util.List;

@Dao
public interface MorningDao {

    // implement CRUD functions
    @Insert
    void insertHabit(MorningRoutine morningRoutine);

    @Query("DELETE FROM morning_routine")
    void deleteAllHabits();

    // get all evening habits
    @Query("SELECT * FROM morning_routine")
    LiveData<List<MorningRoutine>> getAllHabits();

    // get a specific habit
    @Query("SELECT * FROM morning_routine WHERE morning_routine.habit_id == :id")
    LiveData<MorningRoutine> getHabit(long id);

    @Update
    void updateHabit(MorningRoutine morningRoutine);

    @Delete
    void deleteHabit(MorningRoutine morningRoutine);


}

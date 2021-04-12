package com.example.dailynotdilly.models;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "evening_routine")
public class EveningRoutine {

    @ColumnInfo(name = "habit_id")
    @PrimaryKey(autoGenerate = true)
    public long habitId;

    public String habit;

    @ColumnInfo(name = "set_time")
    public Date timeSet;

    @ColumnInfo(name = "set_minute")
    public Date minuteSet;

    @ColumnInfo(name = "is_done")
    public boolean isDone;

    // constructor
    public EveningRoutine(String habit, Date timeSet, Date minuteSet, boolean isDone) {
        this.habit = habit;
        this.timeSet = timeSet;
        this.minuteSet = minuteSet;
        this.isDone = isDone;
    }

    // getters and setters
    public long getHabitId() {
        return habitId;
    }

    public void setHabitId(long habitId) {
        this.habitId = habitId;
    }

    public String getHabit() {
        return habit;
    }

    public void setHabit(String habit) {
        this.habit = habit;
    }

    public Date getTimeSet() {
        return timeSet;
    }

    public void setTimeSet(Date timeSet) {
        this.timeSet = timeSet;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Date getMinuteSet() {
        return minuteSet;
    }

    public void setMinuteSet(Date minuteSet) {
        this.minuteSet = minuteSet;
    }

    @Override
    public String toString() {
        return "EveningRoutine{" +
                "habitId=" + habitId +
                ", habit='" + habit + '\'' +
                ", timeSet=" + timeSet +
                ", minuteSet=" + minuteSet +
                ", isDone=" + isDone +
                '}';
    }
}

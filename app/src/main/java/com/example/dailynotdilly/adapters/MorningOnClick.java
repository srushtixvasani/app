package com.example.dailynotdilly.adapters;

import com.example.dailynotdilly.models.MorningRoutine;

public interface MorningOnClick {

    void morningHabitOnClick(MorningRoutine morningRoutine);

    void morningRadioOnClick(MorningRoutine morningRoutine);

    void morningMinuteChipOnClick(MorningRoutine morningRoutine);

    void morningTimeChipOnClick(MorningRoutine morningRoutine);
}

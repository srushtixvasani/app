package com.example.dailynotdilly;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailynotdilly.models.MorningRoutine;
import com.example.dailynotdilly.models.MorningSharedViewModel;
import com.example.dailynotdilly.models.MorningViewModel;
import com.example.dailynotdilly.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class MorningBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText enterHabit;
    private ImageButton timeButton;
    private ImageButton addHabitButton;
    private Group timePickerGroup;
    private ImageButton minuteButton;
    private Group minutePickerGroup;
    private NumberPicker minuteSetButton;
    private TimePicker timePicker;
    private Date timeSet;
    private Date minuteSet;
    Calendar calendar = Calendar.getInstance();
    private MorningSharedViewModel sharedViewModel;
    private boolean isEdit;


    public MorningBottomFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.morning_bottom_sheet, container, false);

        timePickerGroup = view.findViewById(R.id.morning_time_group);
        timePicker = view.findViewById(R.id.morning_time_picker);
        timeButton = view.findViewById(R.id.morning_time_button);
        enterHabit = view.findViewById(R.id.enter_morning_habit);
        minutePickerGroup = view.findViewById(R.id.morning_minute_group);
        minuteButton = view.findViewById(R.id.morning_minute_button);
        minuteSetButton = view.findViewById(R.id.set_morning_minute_button);
        addHabitButton = view.findViewById(R.id.save_mHabit_button);

        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        if (sharedViewModel.getSelectedHabit().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();

            MorningRoutine morningRoutine = sharedViewModel.getSelectedHabit().getValue();
            enterHabit.setText(morningRoutine.getHabit());
            Log.d("RoutineSharedViewModel", "onViewCreated: " + morningRoutine.getHabit());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        timeButton.setOnClickListener(v -> {
            timePickerGroup.setVisibility(timePickerGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideKeyboard(v);
        });

        minuteButton.setOnClickListener(v1 -> {
            minutePickerGroup.setVisibility(minutePickerGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideKeyboard(v1);
        });

        minuteSetButton.setMinValue(0);
        minuteSetButton.setMaxValue(59);
        minuteSetButton.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                calendar.clear();
                calendar.set(0,0,0,0, newVal);

                minuteSet = calendar.getTime();
            }
        });

        timePicker.setIs24HourView(true);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                calendar.clear();
                calendar.set(0,0,0,hourOfDay,minute);

                timeSet = calendar.getTime();
            }
        });

        addHabitButton.setOnClickListener(v -> {
            String habit = enterHabit.getText().toString().trim();
            if (!TextUtils.isEmpty(habit) && timeSet != null) {

                MorningRoutine mHabit = new MorningRoutine(habit, timeSet, minuteSet, false);

                // check if user clicks to edit existing habit, then update it
                if (isEdit) {
                    MorningRoutine updateHabit = sharedViewModel.getSelectedHabit().getValue();
                    updateHabit.setHabit(habit);
                    updateHabit.setTimeSet(timeSet);
                    updateHabit.setMinuteSet(minuteSet);

                    MorningViewModel.updateHabit(updateHabit);
                    sharedViewModel.setIsEdit(false);
                } else {
                    MorningViewModel.insertHabit(mHabit);
                }
                enterHabit.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }

            } else {
                Snackbar.make(addHabitButton, "Empty Habit", Snackbar.LENGTH_LONG).show();
            }
        });

        sharedViewModel = new ViewModelProvider(requireActivity()).get(MorningSharedViewModel.class);
    }

    @Override
    public void onClick(View v) {

    }
}

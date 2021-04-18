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

import com.example.dailynotdilly.models.AfternoonRoutine;
import com.example.dailynotdilly.models.AfternoonSharedViewModel;
import com.example.dailynotdilly.models.AfternoonViewModel;
import com.example.dailynotdilly.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class AfternoonBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener {

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
    private AfternoonSharedViewModel sharedViewModel;
    private boolean isEdit;

    public AfternoonBottomFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.afternoon_bottom_sheet, container, false);

        timePickerGroup = view.findViewById(R.id.afternoon_time_group);
        timePicker = view.findViewById(R.id.afternoon_time_picker);
        timeButton = view.findViewById(R.id.afternoon_time_button);
        enterHabit = view.findViewById(R.id.enter_afternoon_habit);
        minutePickerGroup = view.findViewById(R.id.afternoon_minute_group);
        minuteButton = view.findViewById(R.id.afternoon_minute_button);
        minuteSetButton = view.findViewById(R.id.set_afternoon_minute_button);
        addHabitButton = view.findViewById(R.id.save_afternoon_habit_button);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sharedViewModel.getSelectedHabit().getValue() != null) {
            isEdit = sharedViewModel.getIsEdit();

            AfternoonRoutine afternoonRoutine = sharedViewModel.getSelectedHabit().getValue();
            enterHabit.setText(afternoonRoutine.getHabit());
            Log.d("RoutineSharedViewModel", "onViewCreated: " + afternoonRoutine.getHabit());
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

                AfternoonRoutine aHabit = new AfternoonRoutine(habit,timeSet, minuteSet, false);

                // check if user clicks to edit existing habit, then update it
                if (isEdit) {
                    AfternoonRoutine updateHabit = sharedViewModel.getSelectedHabit().getValue();
                    updateHabit.setHabit(habit);
                    updateHabit.setTimeSet(timeSet);
                    updateHabit.setMinuteSet(minuteSet);

                    AfternoonViewModel.updateHabit(updateHabit);
                    sharedViewModel.setIsEdit(false);
                } else {
                    AfternoonViewModel.insertHabit(aHabit);
                }
                enterHabit.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }
            } else {
                Snackbar.make(addHabitButton, "Empty Habit", Snackbar.LENGTH_LONG).show();
            }
        });

        sharedViewModel = new ViewModelProvider(requireActivity()).get(AfternoonSharedViewModel.class);
    }

    @Override
    public void onClick(View v) {

    }
}

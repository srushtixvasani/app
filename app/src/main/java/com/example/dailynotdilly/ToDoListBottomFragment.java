package com.example.dailynotdilly;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.Group;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.dailynotdilly.models.Priority;
import com.example.dailynotdilly.models.SharedViewModel;
import com.example.dailynotdilly.models.ToDoTask;
import com.example.dailynotdilly.models.ToDoViewModel;
import com.example.dailynotdilly.utils.Utils;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.chip.Chip;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.Date;

public class ToDoListBottomFragment extends BottomSheetDialogFragment implements View.OnClickListener {

    private EditText enterToDo;
    private ImageButton calenderButton;
    private ImageButton priorityButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private Priority priority;
    private int selectedButtonId;
    private ImageButton saveButton;
    private CalendarView calendarView;
    private Group calenderGroup;
    Calendar calendar = Calendar.getInstance();
    private Date dueDate;
    private SharedViewModel sharedViewModel;
    private boolean isEdit;

    public ToDoListBottomFragment() {}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.to_do_list_bottom_sheet, container, false);

        calenderGroup = view.findViewById(R.id.calendar_group);
        calendarView = view.findViewById(R.id.calendar_view);
        calenderButton = view.findViewById(R.id.today_calendar_button);
        enterToDo = view.findViewById(R.id.enter_todo);
        saveButton = view.findViewById(R.id.save_todo_button);
        priorityButton = view.findViewById(R.id.priority_todo_button);
        radioGroup = view.findViewById(R.id.radioGroup_priority);

        Chip todayChip = view.findViewById(R.id.today_chip);
        todayChip.setOnClickListener(this);
        Chip tomorrowChip = view.findViewById(R.id.tomorrow_chip);
        tomorrowChip.setOnClickListener(this);
        Chip nextWeekChip = view.findViewById(R.id.next_week_chip);
        nextWeekChip.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (sharedViewModel.getSelectedItem().getValue() != null) {

            isEdit = sharedViewModel.getIsEdit();

            ToDoTask toDoTask = sharedViewModel.getSelectedItem().getValue();
            enterToDo.setText(toDoTask.getTask());
            Log.d("SharedViewModel", "onViewCreated: " + toDoTask.getTask());
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calenderButton.setOnClickListener(v -> {
            calenderGroup.setVisibility(calenderGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            Utils.hideKeyboard(v);
        });


        calendarView.setOnDateChangeListener((calendarView, year, month, dayOfMonth) -> {
            calendar.clear();
            calendar.set(year, month, dayOfMonth);

            dueDate = calendar.getTime();
            // for testing purposes
            //Log.d("Calender", "onSelectedDayChange: ====> Month " + (month + 1) + ", dayOfMonth"
            //+ dayOfMonth);
        });

        priorityButton.setOnClickListener(v1 -> {
            Utils.hideKeyboard(v1);
            radioGroup.setVisibility(radioGroup.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                if (radioGroup.getVisibility() == View.VISIBLE) {

                    selectedButtonId = checkedId;
                    radioButton = view.findViewById(selectedButtonId);

                    if (radioButton.getId() == R.id.radioButton_high) {
                        priority = Priority.HIGH;
                    } else if (radioButton.getId() == R.id.radioButton_med) {
                        priority =Priority.MEDIUM;
                    } else if (radioButton.getId() == R.id.radioButton_low) {
                        priority = Priority.LOW;
                    } else {
                        priority = Priority.LOW;
                    }
                } else {
                    priority = Priority.LOW;
                }
            });

        });

        saveButton.setOnClickListener(v -> {
            String toDo = enterToDo.getText().toString().trim();
            if (!TextUtils.isEmpty(toDo) && dueDate != null && priority != null) {
                ToDoTask mToDo = new ToDoTask(toDo, priority, dueDate,
                        Calendar.getInstance().getTime(), false);

                // check if user clicks to edit existing to-do, then update it
                if (isEdit) {
                    ToDoTask updateToDo = sharedViewModel.getSelectedItem().getValue();
                    updateToDo.setTask(toDo);
                    updateToDo.setDateCreated(Calendar.getInstance().getTime());
                    updateToDo.setPriority(priority);
                    updateToDo.setDueDate(dueDate);
                    ToDoViewModel.update(updateToDo);
                    sharedViewModel.setIsEdit(false);
                } else {
                    ToDoViewModel.insert(mToDo);
                }
                enterToDo.setText("");
                if (this.isVisible()) {
                    this.dismiss();
                }

            } else {
                Snackbar.make(saveButton, R.string.empty_task, Snackbar.LENGTH_LONG).show();
            }
        });

        sharedViewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.today_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 0);
            dueDate = calendar.getTime();
            Log.d("DUE DATE", "onClick: " + dueDate.toString());

        } else if (id == R.id.tomorrow_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 1);
            dueDate = calendar.getTime();
            Log.d("DUE DATE", "onClick: " + dueDate.toString());

        } else if (id == R.id.next_week_chip) {
            calendar.add(Calendar.DAY_OF_YEAR, 7);
            dueDate = calendar.getTime();
            Log.d("DUE DATE", "onClick: " + dueDate.toString());

        }

    }
}
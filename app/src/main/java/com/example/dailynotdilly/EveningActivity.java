package com.example.dailynotdilly;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.adapters.EveningAdapter;
import com.example.dailynotdilly.adapters.EveningOnClick;
import com.example.dailynotdilly.models.EveningRoutine;
import com.example.dailynotdilly.models.EveningViewModel;
import com.example.dailynotdilly.models.EveningSharedViewModel;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class EveningActivity extends AppCompatActivity implements EveningOnClick {

    private static final String HABIT = "EVENING_HABIT";
    private EveningViewModel eveningViewModel;
    private RecyclerView recyclerView;
    private AppCompatImageButton addButton;
    private EveningAdapter recyclerViewAdapter;
    EveningBottomFragment eveningBottomFragment;
    private EveningSharedViewModel sharedViewModel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.evening_routine);

        // set up toolbar
        Toolbar toolbar = findViewById(R.id.evening_toolbar);
        setSupportActionBar(toolbar);

        eveningBottomFragment = new EveningBottomFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.evening_bottom_sheet_fragment);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        // Opens up Home Activity
        ImageButton homeActivity = findViewById(R.id.homeActivity);
        homeActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EveningActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Opens up morning Activity
        ImageButton morningActivity = findViewById(R.id.morningRoutine);
        morningActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EveningActivity.this, MorningActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Afternoon Activity
        ImageButton afternoonActivity = findViewById(R.id.afternoonRoutine);
        afternoonActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EveningActivity.this, AfternoonActivity.class);
                startActivity(intent);
            }
        });

        //set up recycler view
        recyclerView = findViewById(R.id.evening_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // instantiate the view model
        eveningViewModel = new ViewModelProvider.
                AndroidViewModelFactory(EveningActivity.this.getApplication())
                .create(EveningViewModel.class);

        eveningViewModel.getAllHabits().observe(this, eveningRoutineList -> {
             //for testing purposes
           for (EveningRoutine eveningRoutine : eveningRoutineList) {
               Log.d(HABIT, "onCreate: " + eveningRoutine.getHabitId());
           }

            // set up recycler adapter
            recyclerViewAdapter = new EveningAdapter(eveningRoutineList, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(this).get(EveningSharedViewModel.class);

        // set up add habit button
        addButton = findViewById(R.id.add_evening_habit);
        addButton.setOnClickListener(v -> {
            showEveningBottomFragment();
        });

    }

    private void showEveningBottomFragment() {
        eveningBottomFragment.show(getSupportFragmentManager(), eveningBottomFragment.getTag());

    }

    @Override
    public void eveningHabitOnClick(EveningRoutine eveningRoutine) {
        sharedViewModel.selectHabit(eveningRoutine);
        sharedViewModel.setIsEdit(true);

        showEveningBottomFragment();
    }

    @Override
    public void eveningRadioOnClick(EveningRoutine eveningRoutine) {
        EveningViewModel.deleteHabit(eveningRoutine);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void eveningMinuteChipOnClick(EveningRoutine eveningRoutine) {
        sharedViewModel.selectHabit(eveningRoutine);

    }

    @Override
    public void eveningTimeChipOnClick(EveningRoutine eveningRoutine) {
        sharedViewModel.selectHabit(eveningRoutine);

    }
}

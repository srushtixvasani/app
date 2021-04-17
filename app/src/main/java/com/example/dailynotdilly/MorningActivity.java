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

import com.example.dailynotdilly.adapters.MorningAdapter;
import com.example.dailynotdilly.adapters.MorningOnClick;
import com.example.dailynotdilly.models.MorningRoutine;
import com.example.dailynotdilly.models.MorningSharedViewModel;
import com.example.dailynotdilly.models.MorningViewModel;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class MorningActivity extends AppCompatActivity implements MorningOnClick {

    private static final String HABIT = "MORNING_HABIT";
    private MorningViewModel morningViewModel;
    private RecyclerView recyclerView;
    private AppCompatImageButton addButton;
    private MorningAdapter recyclerViewAdapter;
    MorningBottomFragment morningBottomFragment;
    private MorningSharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.morning_activity);

        // set up toolbar
        Toolbar toolbar = findViewById(R.id.morning_toolbar);
        setSupportActionBar(toolbar);

        morningBottomFragment = new MorningBottomFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.morning_bottom_sheet_fragment);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        // Opens up Home Activity
        ImageButton HomeButton = findViewById(R.id.home_button);
        HomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MorningActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Afternoon Activity
        ImageButton afternoonButton = findViewById(R.id.afternoon_button);
        afternoonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MorningActivity.this, AfternoonActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Afternoon Activity
        ImageButton eveningButton = findViewById(R.id.evening_button);
        eveningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MorningActivity.this, EveningActivity.class);
                startActivity(intent);
            }
        });

        //set up recycler view
        recyclerView = findViewById(R.id.morning_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // instantiate the view model
        morningViewModel = new ViewModelProvider.
                AndroidViewModelFactory(MorningActivity.this.getApplication())
                .create(MorningViewModel.class);

        morningViewModel.getAllHabits().observe(this, morningRoutineList -> {
            //for testing purposes
            for (MorningRoutine morningRoutine : morningRoutineList) {
                Log.d(HABIT, "onCreate: " + morningRoutine.getHabitId());
            }

            // set up recycler adapter
            recyclerViewAdapter = new MorningAdapter(morningRoutineList, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(this).get(MorningSharedViewModel.class);

        // set up add habit button
        addButton = findViewById(R.id.add_morning_habit);
        addButton.setOnClickListener(v -> {
            showMorningBottomFragment();
        });

    }

    private void showMorningBottomFragment() {
        morningBottomFragment.show(getSupportFragmentManager(), morningBottomFragment.getTag());

    }


    @Override
    public void morningHabitOnClick(MorningRoutine morningRoutine) {
        sharedViewModel.selectHabit(morningRoutine);
        sharedViewModel.setIsEdit(true);

        showMorningBottomFragment();
    }

    @Override
    public void morningRadioOnClick(MorningRoutine morningRoutine) {
        MorningViewModel.deleteHabit(morningRoutine);
        recyclerViewAdapter.notifyDataSetChanged();

    }

    @Override
    public void morningMinuteChipOnClick(MorningRoutine morningRoutine) {
        sharedViewModel.selectHabit(morningRoutine);

    }

    @Override
    public void morningTimeChipOnClick(MorningRoutine morningRoutine) {
        sharedViewModel.selectHabit(morningRoutine);

    }
}

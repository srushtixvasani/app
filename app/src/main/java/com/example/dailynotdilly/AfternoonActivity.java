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

import com.example.dailynotdilly.adapters.AfternoonAdapter;
import com.example.dailynotdilly.adapters.AfternoonOnClick;
import com.example.dailynotdilly.models.AfternoonRoutine;
import com.example.dailynotdilly.models.AfternoonSharedViewModel;
import com.example.dailynotdilly.models.AfternoonViewModel;

import com.google.android.material.bottomsheet.BottomSheetBehavior;

public class AfternoonActivity extends AppCompatActivity implements AfternoonOnClick {

    private static final String HABIT = "AFTERNOON_HABIT";
    private AfternoonViewModel afternoonViewModel;
    private RecyclerView recyclerView;
    private AppCompatImageButton addButton;
    private AfternoonAdapter recyclerViewAdapter;
    AfternoonBottomFragment afternoonBottomFragment;
    private AfternoonSharedViewModel sharedViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.afternoon_activity);

        // set up toolbar
        Toolbar toolbar = findViewById(R.id.afternoon_toolbar);
        setSupportActionBar(toolbar);

        afternoonBottomFragment = new AfternoonBottomFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.afternoon_bottom_sheet_fragment);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);

        // Opens up Home Activity
        ImageButton homeButton = findViewById(R.id.home);
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfternoonActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        // Opens up morning Activity
        ImageButton morningButton = findViewById(R.id.morning);
        morningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfternoonActivity.this, MorningActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Evening Activity
        ImageButton eveningButton = findViewById(R.id.evening);
        eveningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AfternoonActivity.this, EveningActivity.class);
                startActivity(intent);
            }
        });

        //set up recycler view
        recyclerView = findViewById(R.id.afternoon_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // instantiate the view model
        afternoonViewModel = new ViewModelProvider.
                AndroidViewModelFactory(AfternoonActivity.this.getApplication())
                .create(AfternoonViewModel.class);

        afternoonViewModel.getAllHabits().observe(this, afternoonRoutineList -> {
            //for testing purposes
            for (AfternoonRoutine afternoonRoutine : afternoonRoutineList) {
                Log.d(HABIT, "onCreate: " + afternoonRoutine.getHabitId());
            }

            // set up recycler adapter
            recyclerViewAdapter = new AfternoonAdapter(afternoonRoutineList, this);
            recyclerView.setAdapter(recyclerViewAdapter);
        });

        sharedViewModel = new ViewModelProvider(this).get(AfternoonSharedViewModel.class);

        // set up add habit button
        addButton = findViewById(R.id.add_afternoon_habit);
        addButton.setOnClickListener(v -> {
            showEveningBottomFragment();
        });

    }

    private void showEveningBottomFragment() {
        afternoonBottomFragment.show(getSupportFragmentManager(), afternoonBottomFragment.getTag());
    }

    @Override
    public void afternoonHabitOnClick(AfternoonRoutine afternoonRoutine) {
        sharedViewModel.selectHabit(afternoonRoutine);
        sharedViewModel.setIsEdit(true);

        showEveningBottomFragment();
    }

    @Override
    public void afternoonRadioOnClick(AfternoonRoutine afternoonRoutine) {
        AfternoonViewModel.deleteHabit(afternoonRoutine);
        recyclerViewAdapter.notifyDataSetChanged();
    }

    @Override
    public void afternoonMinuteChipOnClick(AfternoonRoutine afternoonRoutine) {
        sharedViewModel.selectHabit(afternoonRoutine);

    }

    @Override
    public void afternoonTimeChipOnClick(AfternoonRoutine afternoonRoutine) {
        sharedViewModel.selectHabit(afternoonRoutine);
    }
}
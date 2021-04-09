package com.example.dailynotdilly;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.adapters.ToDoAdapter;
import com.example.dailynotdilly.adapters.ToDoOnClick;
import com.example.dailynotdilly.models.Priority;
import com.example.dailynotdilly.models.SharedViewModel;
import com.example.dailynotdilly.models.ToDoTask;
import com.example.dailynotdilly.models.ToDoViewModel;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity implements ToDoOnClick {

    private static final String TAG = "TASK" ;
    private ToDoViewModel toDoViewModel;
    private RecyclerView recyclerView;
    private ToDoAdapter recyclerViewAdapter;
    private int counter = 1;
    ToDoListBottomFragment bottomFragment;
    private SharedViewModel sharedViewModel;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_activity);

        //set Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        AppCompatImageButton backButton = findViewById(R.id.to_do_back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        bottomFragment = new ToDoListBottomFragment();
        ConstraintLayout constraintLayout = findViewById(R.id.bottom_fragment);
        BottomSheetBehavior<ConstraintLayout> bottomSheetBehavior = BottomSheetBehavior.from(constraintLayout);
        bottomSheetBehavior.setPeekHeight(BottomSheetBehavior.STATE_HIDDEN);


        //set up recycler view
        recyclerView = findViewById(R.id.to_do_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // instantiate the viewModel
        toDoViewModel = new ViewModelProvider.
                AndroidViewModelFactory(ToDoListActivity.this.getApplication())
                .create(ToDoViewModel.class);

        toDoViewModel.getAllTasks().observe(this, toDoTasks -> {

            // for testing purposes
//           for (ToDoTask toDoTask : toDoTasks) {
//               Log.d(TAG, "onCreate: " + toDoTask.getTaskId());
//           }

           // set up recycler adapter
            recyclerViewAdapter = new ToDoAdapter(toDoTasks, this);
            recyclerView.setAdapter(recyclerViewAdapter);

        });

        sharedViewModel = new ViewModelProvider(this).get(SharedViewModel.class);



        //set Floating Action button
        FloatingActionButton floatingActionButton = findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(view -> {
//            ToDoTask toDoTask = new ToDoTask("Task " + counter++ , Priority.MEDIUM, Calendar.getInstance().getTime(),
//                    Calendar.getInstance().getTime(), false);
//
//            ToDoViewModel.insert(toDoTask);

            showBottomFragment();
        });

    }

    private void showBottomFragment() {
        bottomFragment.show(getSupportFragmentManager(), bottomFragment.getTag());
    }

    // when the user clicks on each row
    @Override
    public void toDoOnClick(ToDoTask toDoTask) {
        // for testing purposes
        //Log.d("OnClick", "ToDoOnClick: " + pos);

        sharedViewModel.selectTask(toDoTask);
        sharedViewModel.setIsEdit(true);
        showBottomFragment();
    }

    // when the user click on the radio button
    @Override
    public void toDoRadioButton(ToDoTask toDoTask) {
        ToDoViewModel.delete(toDoTask);
        recyclerViewAdapter.notifyDataSetChanged();

    }
}

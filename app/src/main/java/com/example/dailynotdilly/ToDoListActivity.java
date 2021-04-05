package com.example.dailynotdilly;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.adapters.ToDoAdapter;
import com.example.dailynotdilly.models.Priority;
import com.example.dailynotdilly.models.ToDoTask;
import com.example.dailynotdilly.models.ToDoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;
import java.util.List;

public class ToDoListActivity extends AppCompatActivity {

    private static final String TAG = "TASK" ;
    private ToDoViewModel toDoViewModel;
    private RecyclerView recyclerView;
    private ToDoAdapter recyclerViewAdapter;
    private int counter = 1;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_activity);

        //set Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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
            recyclerViewAdapter = new ToDoAdapter(toDoTasks);
            recyclerView.setAdapter(recyclerViewAdapter);


        });

        //set Floating Action button
        FloatingActionButton floatingActionButton = findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(view -> {
            ToDoTask toDoTask = new ToDoTask("Task " + counter++ , Priority.MEDIUM, Calendar.getInstance().getTime(),
                    Calendar.getInstance().getTime(), false);

            ToDoViewModel.insert(toDoTask);
        });

    }
}

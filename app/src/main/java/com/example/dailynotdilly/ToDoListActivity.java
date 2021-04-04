package com.example.dailynotdilly;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import com.example.dailynotdilly.models.Priority;
import com.example.dailynotdilly.models.ToDoTask;
import com.example.dailynotdilly.models.ToDoViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.Calendar;

public class ToDoListActivity extends AppCompatActivity {

    private ToDoViewModel toDoViewModel;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.to_do_list_activity);

        //set Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // instantiate the viewModel
        toDoViewModel = new ViewModelProvider.
                AndroidViewModelFactory(ToDoListActivity.this.getApplication())
                .create(ToDoViewModel.class);

        //set Floating Action button
        FloatingActionButton floatingActionButton = findViewById(R.id.add_fab);
        floatingActionButton.setOnClickListener(view -> {
            ToDoTask toDoTask = new ToDoTask("To-Do", Priority.HIGH, Calendar.getInstance().getTime(),
                    Calendar.getInstance().getTime(), false);

            ToDoViewModel.insert(toDoTask);
        });

    }
}

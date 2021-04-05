package com.example.dailynotdilly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.ToDoTask;
import com.google.android.material.chip.Chip;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private final List<ToDoTask> toDoList;

    public ToDoAdapter(List<ToDoTask> toDoList) {
        this.toDoList = toDoList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.to_do_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ToDoTask toDoTask = toDoList.get(position);

        holder.toDo.setText(toDoTask.getTask());


    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public AppCompatRadioButton radioButton;
        public TextView toDo;
        public Chip toDoChip;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.to_do_radio_button);
            toDo = itemView.findViewById(R.id.todo_row_text);
            toDoChip = itemView.findViewById(R.id.to_do_row_chip);

        }
    }
}

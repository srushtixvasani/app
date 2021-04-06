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
import com.example.dailynotdilly.utils.Utils;
import com.google.android.material.chip.Chip;

import java.util.List;

public class ToDoAdapter extends RecyclerView.Adapter<ToDoAdapter.ViewHolder> {

    private final List<ToDoTask> toDoList;
    private final ToDoOnClick toDoOnClickListener;

    public ToDoAdapter(List<ToDoTask> toDoList, ToDoOnClick toDoListener) {

        this.toDoList = toDoList;
        this.toDoOnClickListener = toDoListener;
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

        String format = Utils.formatDate(toDoTask.getDueDate());
        holder.toDoChip.setText(format);


    }

    @Override
    public int getItemCount() {
        return toDoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public TextView toDo;
        public Chip toDoChip;
        ToDoOnClick toDoOnClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.to_do_radio_button);
            toDo = itemView.findViewById(R.id.todo_row_text);
            toDoChip = itemView.findViewById(R.id.to_do_row_chip);
            this.toDoOnClick = toDoOnClickListener;

            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();

            if (id == R.id.todo_row_layout) {
                ToDoTask currentTask  = toDoList.get(getAdapterPosition());

                toDoOnClick.ToDoOnClick(getAdapterPosition(), currentTask);
            }
        }
    }
}

package com.example.dailynotdilly.adapters;

import android.content.res.ColorStateList;
import android.graphics.Color;
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
        String format = Utils.formatDate(toDoTask.getDueDate());

        ColorStateList colorStateList = new ColorStateList(new int[][]{
                new int[] {-android.R.attr.state_enabled},
                new int[] {android.R.attr.state_enabled}

        }, new int[]{
                Color.LTGRAY, // disabled state color
                Utils.priorityColor(toDoTask)
        });

        holder.toDo.setText(toDoTask.getTask());
        holder.toDoChip.setText(format);
        //holder.toDoChip.setTextColor(Utils.priorityColor(toDoTask));
        holder.toDoChip.setChipIconTint(colorStateList);
        //holder.radioButton.setButtonTintList(colorStateList);


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

            radioButton = itemView.findViewById(R.id.to_do_radio);
            toDo = itemView.findViewById(R.id.todo_row_text);
            toDoChip = itemView.findViewById(R.id.to_do_row_chip);
            this.toDoOnClick = toDoOnClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            ToDoTask currentTask = toDoList.get(getAdapterPosition());
            if (id == R.id.todo_row_layout) {
                toDoOnClick.toDoOnClick(currentTask);
            } else if (id == R.id.to_do_radio) {
                toDoOnClick.toDoRadioButton(currentTask);
            }
        }
    }
}

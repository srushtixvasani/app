package com.example.dailynotdilly.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.EveningRoutine;

import java.util.List;

public class EveningAdapter extends RecyclerView.Adapter<EveningAdapter.ViewHolder> {

    private final List<EveningRoutine> eveningRoutineList;
    private final EveningOnClick eveningOnClick;

    public EveningAdapter(List<EveningRoutine> eveningRoutineList, EveningOnClick eHabitOnCLickListener) {

        this.eveningRoutineList = eveningRoutineList;
        this.eveningOnClick = eHabitOnCLickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.evening_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EveningRoutine eveningRoutine = eveningRoutineList.get(position);

        // FORMAT THE TIME


    }

    @Override
    public int getItemCount() {
        return eveningRoutineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        @Override
        public void onClick(View v) {

        }
    }
}

package com.example.dailynotdilly.adapters;

import android.app.TimePickerDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.EveningRoutine;
import com.example.dailynotdilly.utils.RoutineUtils;
import com.example.dailynotdilly.utils.Utils;
import com.google.android.material.chip.Chip;

import java.util.Date;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class EveningAdapter extends RecyclerView.Adapter<EveningAdapter.ViewHolder> {

    private final List<EveningRoutine> eveningRoutineList;
    private final EveningOnClick eveningOnClickListener;

    public EveningAdapter(List<EveningRoutine> eveningRoutineList, EveningOnClick eHabitOnCLickListener) {

        this.eveningRoutineList = eveningRoutineList;
        this.eveningOnClickListener = eHabitOnCLickListener;
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
        String timeFormat = RoutineUtils.formatTime(eveningRoutine.getTimeSet());
        String minuteFormat = RoutineUtils.formatTime(eveningRoutine.getMinuteSet());

        // set the layout as per the habit
        holder.eveningHabit.setText(eveningRoutine.getHabit());
        holder.eveningTimeChip.setText(timeFormat);
        holder.eveningMinuteChip.setText(minuteFormat);

    }

    @Override
    public int getItemCount() {
        return eveningRoutineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public TextView eveningHabit;
        public Chip eveningTimeChip;
        public Chip eveningMinuteChip;
        EveningOnClick eveningOnClick;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.evening_radio);
            eveningHabit = itemView.findViewById(R.id.evening_habit_text_view);
            eveningTimeChip = itemView.findViewById(R.id.evening_time_chip);
            eveningMinuteChip = itemView.findViewById(R.id.evening_minute_chip);
            this.eveningOnClick = eveningOnClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);

            eveningMinuteChip.setOnClickListener(this);



        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            EveningRoutine currentHabit = eveningRoutineList.get(getAdapterPosition());
            if (id == R.id.evening_row_item) {
                eveningOnClick.eveningHabitOnClick(currentHabit);
            } else if (id == R.id.evening_radio) {
                eveningOnClick.eveningRadioOnClick(currentHabit);
            } else if (id == R.id.evening_minute_chip ) {
                eveningOnClick.eveningMinuteChipOnClick(currentHabit);
                // inflate the layout of the info window
                View infoWindow = LayoutInflater.from(v.getContext()).inflate(R.layout.info_window, null);

//                String habit = currentHabit.getHabit();
//                String minuteSet = RoutineUtils.formatTime(currentHabit.getMinuteSet());

                // create the info window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                final PopupWindow infoPopup = new PopupWindow(infoWindow, width, height, true);

                // shows the info window
                infoPopup.showAtLocation(v, Gravity.CENTER, 0, 0);

                // dismisses the info window when clicked
                infoWindow.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        infoPopup.dismiss();
                        return true;
                    }
                });
            }
        }


    }
}

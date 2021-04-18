package com.example.dailynotdilly.adapters;

import android.content.Context;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.EveningRoutine;
import com.example.dailynotdilly.utils.RoutineUtils;
import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


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
        String minuteFormat = RoutineUtils.formatTimerMinute(eveningRoutine.getMinuteSet());

        // set the layout as per the habit
        holder.eveningHabit.setText(eveningRoutine.getHabit());
        holder.eveningTimeChip.setText(timeFormat);
        holder.eveningMinuteChip.setText(minuteFormat + " minutes");

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

        private TextView habitText;
        private TextView timerText;
        private Button startButton;
        private CountDownTimer countDownTimer;


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
            } else if (id == R.id.evening_time_chip) {
                eveningOnClick.eveningTimeChipOnClick(currentHabit);
            } else if (id == R.id.evening_minute_chip) {
                eveningOnClick.eveningMinuteChipOnClick(currentHabit);
                // inflate the layout of the timer window
                View timerWindow = LayoutInflater.from(v.getContext()).inflate(R.layout.timer_activity, null);

                // create the timer window
                int width = LinearLayout.LayoutParams.MATCH_PARENT;
                int height = LinearLayout.LayoutParams.MATCH_PARENT;
                final PopupWindow timerPopup = new PopupWindow(timerWindow, width, height, true);

                // shows the timer window
                timerPopup.showAtLocation(v, Gravity.CENTER, 0, 0);

                // dismisses the info window when clicked
                timerWindow.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        timerPopup.dismiss();
                        return true;
                    }
                });

                // get the layout items
                habitText = timerWindow.findViewById(R.id.timer_habit_tv);
                timerText = timerWindow.findViewById(R.id.timer_textview);
                startButton = timerWindow.findViewById(R.id.timer_start);
                boolean isRunning;

                String habitTxt = currentHabit.getHabit();
                habitText.setText(habitTxt);

                String minuteString = RoutineUtils.formatTimerMinute(currentHabit.getMinuteSet());
                timerText.setText(minuteString);

                startButton.setOnClickListener(v1 -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
                    formatter.setLenient(false);
                    Context context = v1.getContext();

                    try {
                        Date minutes = formatter.parse(minuteString);
                        Long setMinutesInMillis = ( 3600000 - (-1* minutes.getTime()));
                        Log.d("SET MILLIS", "onClick: " + setMinutesInMillis + "date :" + minutes);

                        countDownTimer = new CountDownTimer(setMinutesInMillis, 1000) {
                            public void onTick(long millisUntilFinished) {

                                // Used for formatting digit to be in 2 digits only
                                NumberFormat f = new DecimalFormat("00");
                                long hour = (millisUntilFinished / 3600000) % 24;
                                long min = (millisUntilFinished / 60000) % 60;
                                long sec = (millisUntilFinished / 1000) % 60;
                                timerText.setText( f.format(min) + ":" + f.format(sec));
                            }
                            // When the task is over it will print 00:00:00 there
                            public void onFinish() {
                                timerText.setText("00:00");
                                String habit_completed = habitTxt + " completed. Well done!";

                                Toast.makeText(context, habit_completed, Toast.LENGTH_LONG).show();
                                timerPopup.dismiss();
                            }
                        }.start();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                });

            }

        }
    }
}
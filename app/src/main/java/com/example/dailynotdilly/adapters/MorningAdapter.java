package com.example.dailynotdilly.adapters;

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

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dailynotdilly.R;
import com.example.dailynotdilly.models.MorningRoutine;
import com.example.dailynotdilly.utils.RoutineUtils;
import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MorningAdapter extends RecyclerView.Adapter<MorningAdapter.ViewHolder> {

    private final List<MorningRoutine> morningRoutineList;
    private final MorningOnClick morningOnClickListener;

    public MorningAdapter(List<MorningRoutine> morningRoutineList, MorningOnClick mHabitOnClickListener) {

        this.morningRoutineList = morningRoutineList;
        this.morningOnClickListener = mHabitOnClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.morning_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        MorningRoutine morningRoutine = morningRoutineList.get(position);
        String timeFormat = RoutineUtils.formatTime(morningRoutine.getTimeSet());
        String minuteFormat = RoutineUtils.formatTimerMinute(morningRoutine.getMinuteSet());

        // set the layout as per the habit
        holder.morningHabit.setText(morningRoutine.getHabit());
        holder.morningTimeChip.setText(timeFormat);
        holder.morningMinuteChip.setText(minuteFormat + " minutes");

    }

    @Override
    public int getItemCount() {
        return morningRoutineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public TextView morningHabit;
        public Chip morningTimeChip;
        public Chip morningMinuteChip;
        MorningOnClick morningOnClick;

        private TextView habitText;
        private TextView timerText;
        private Button startButton;
        private Button pauseButton;
        private Button resetButton;
        private CountDownTimer countDownTimer;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.morning_radio);
            morningHabit = itemView.findViewById(R.id.morning_habit_text_view);
            morningTimeChip = itemView.findViewById(R.id.morning_time_chip);
            morningMinuteChip = itemView.findViewById(R.id.morning_minute_chip);
            this.morningOnClick = morningOnClickListener;

            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
            morningMinuteChip.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            MorningRoutine currentHabit = morningRoutineList.get(getAdapterPosition());

            if (id == R.id.morning_row_item) {
                morningOnClick.morningHabitOnClick(currentHabit);
            } else if ( id == R.id.morning_radio) {
                morningOnClick.morningRadioOnClick(currentHabit);
            } else if ( id == R.id.morning_time_chip) {
                morningOnClick.morningTimeChipOnClick(currentHabit);
            } else if ( id == R.id.morning_minute_chip) {
                morningOnClick.morningMinuteChipOnClick(currentHabit);

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
                pauseButton = timerWindow.findViewById(R.id.timer_pause);
                resetButton = timerWindow.findViewById(R.id.timer_reset);
                boolean isRunning;

                String habitTxt = currentHabit.getHabit();
                habitText.setText(habitTxt);

                String minuteString = RoutineUtils.formatTimerMinute(currentHabit.getMinuteSet());
                timerText.setText(minuteString);

                resetButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String resetMinute = RoutineUtils.formatTimerMinute(currentHabit.getMinuteSet());
                        timerText.setText(resetMinute);

                    }
                });

                startButton.setOnClickListener(v1 -> {
                    SimpleDateFormat formatter = new SimpleDateFormat("mm:ss");
                    formatter.setLenient(false);
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

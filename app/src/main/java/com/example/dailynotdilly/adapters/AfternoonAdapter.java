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
import com.example.dailynotdilly.models.AfternoonRoutine;
import com.example.dailynotdilly.utils.RoutineUtils;
import com.google.android.material.chip.Chip;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class AfternoonAdapter extends RecyclerView.Adapter<AfternoonAdapter.ViewHolder> {

    private final List<AfternoonRoutine> afternoonRoutineList;
    private final AfternoonOnClick afternoonOnClickListener;

    public AfternoonAdapter(List<AfternoonRoutine> afternoonRoutineList, AfternoonOnClick aHabitOnClickListener) {

        this.afternoonRoutineList = afternoonRoutineList;
        this.afternoonOnClickListener = aHabitOnClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.afternoon_row, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        AfternoonRoutine afternoonRoutine = afternoonRoutineList.get(position);
        String timeFormat = RoutineUtils.formatTime(afternoonRoutine.getTimeSet());
        String minuteFormat = RoutineUtils.formatTimerMinute(afternoonRoutine.getMinuteSet());

        // set the layout as per the habit
        holder.afternoonHabit.setText(afternoonRoutine.getHabit());
        holder.afternoonTimeChip.setText(timeFormat);
        holder.afternoonMinuteChip.setText(minuteFormat + " minutes");

    }

    @Override
    public int getItemCount() {
        return afternoonRoutineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public AppCompatRadioButton radioButton;
        public TextView afternoonHabit;
        public Chip afternoonTimeChip;
        public Chip afternoonMinuteChip;
        AfternoonOnClick afternoonOnClick;

        private TextView habitText;
        private TextView timerText;
        private Button startButton;
        private CountDownTimer countDownTimer;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            radioButton = itemView.findViewById(R.id.afternoon_radio);
            afternoonHabit = itemView.findViewById(R.id.afternoon_habit_text_view);
            afternoonTimeChip = itemView.findViewById(R.id.afternoon_time_chip);
            afternoonMinuteChip = itemView.findViewById(R.id.afternoon_minute_chip);
            this.afternoonOnClick = afternoonOnClickListener;


            itemView.setOnClickListener(this);
            radioButton.setOnClickListener(this);
            afternoonMinuteChip.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            int id = v.getId();
            AfternoonRoutine currentHabit = afternoonRoutineList.get(getAdapterPosition());
            if (id == R.id.afternoon_row_item) {
                afternoonOnClick.afternoonHabitOnClick(currentHabit);
            } else if (id == R.id.afternoon_radio) {
                afternoonOnClick.afternoonRadioOnClick(currentHabit);
            } else if (id == R.id.afternoon_time_chip) {
                afternoonOnClick.afternoonTimeChipOnClick(currentHabit);
            } else if (id == R.id.afternoon_minute_chip) {
                afternoonOnClick.afternoonMinuteChipOnClick(currentHabit);

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

package com.example.dailynotdilly;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dailynotdilly.models.Quotes;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Stack;

public class MainActivity extends AppCompatActivity {

    public TextView quoteTextView;
    public ImageButton nextbutton;
    public ImageButton backButton;
    public ArrayList<Quotes> quotesArrayList;
    public int index;
    public Stack<Quotes> backQuotes;
    public boolean isBackQuote;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);


        // Get Calender date
        Calendar calendar = Calendar.getInstance();
        String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());

        // Set date to text view
        TextView dateTextView = findViewById(R.id.date_textview);
        dateTextView.setText(currentDate);

        //Text Clock to get time
        // TextClock textClock = (TextClock) findViewById(R.id.text_clock);


        // Motivational Quotes
        TextView quoteTextView = findViewById(R.id.quotes_textview);
        ImageButton nextButton = findViewById(R.id.next_button);
        ImageButton backButton = findViewById(R.id.back_button);

        
        //1) import quotes from strings.xml file
        Resources res = getResources();
        String[] quotes = res.getStringArray(R.array.quotes);
        quotesArrayList = new ArrayList<>();
        addQuotes(quotes);
        backQuotes = new Stack<>();

        //2) generate random quote
        final int quotesListLength = quotesArrayList.size();
        index = getRandomQuote(quotesListLength -1);
        quoteTextView.setText(quotesArrayList.get(index).toString());

        //3) use next button, to generate new random quote
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isBackQuote = false;
                index = getRandomQuote(quotesListLength -1);
                quoteTextView.setText(quotesArrayList.get(index).toString());
                backQuotes.push(quotesArrayList.get(index));
            }
        });
        
        //4) use back button, to go back to the old quote
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isBackQuote && backQuotes.size() > 0){
                    backQuotes.pop();
                    isBackQuote = true;
                }

                if ( isBackQuote && backQuotes.size() > 0)
                quoteTextView.setText(backQuotes.pop().toString());
                else
                    Toast.makeText(MainActivity.this, "Press Next Button", Toast.LENGTH_SHORT).show();
            }
        });

        // Opens up Morning Activity
        ImageButton morningActivityButton = findViewById(R.id.morning_activity);
        morningActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MorningActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Afternoon Activity
        ImageButton afternoonActivityButton = findViewById(R.id.afternoon_activity);
        afternoonActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, AfternoonActivity.class);
                startActivity(intent);
            }
        });

        // Opens up Evening Activity
        ImageButton eveningActivityButton = findViewById(R.id.evening_activity);
        eveningActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EveningActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * Adding quotes to the quotesArrayList
     * @param quotes
     */
    public void addQuotes(String[] quotes){
        for (int i=0; i< quotes.length; i++){
            String quote = quotes[i];
            Quotes newQuote = new Quotes(quote);
            quotesArrayList.add(newQuote);
        }
    }

    /**
     * Generates a random number for quotes
     * @param length
     * @return
     */

    public int getRandomQuote(int length){
        return (int) (Math.random() * length) + 1;
    }

}
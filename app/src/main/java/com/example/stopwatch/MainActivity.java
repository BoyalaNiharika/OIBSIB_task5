package com.example.stopwatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    // Variables to track stopwatch state
    private int seconds;
    private boolean running;
    private boolean wasRunning;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Retrieve the saved state of the stopwatch
        if(savedInstanceState != null){
            savedInstanceState.getInt("seconds");
            savedInstanceState.getBoolean("running");
            savedInstanceState.getBoolean("wasRunning");
        }

        // Start the stopwatch timer
        runTimer();

    }

    // Start button click event
    public void onStart(View view){
        running = true;
        Toast.makeText(this, "Timer Started", Toast.LENGTH_SHORT).show();
    }

    // Stop button click event
    public void onStop(View view){
        running = false;
        Toast.makeText(this, "Timer Stopped", Toast.LENGTH_SHORT).show();
    }

    // Reset button click event
    public void onReset(View view){
        running = false;
        seconds = 0;
        Toast.makeText(this, "Timer Reset", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Save the running state of the stopwatch
        wasRunning = running;
        running = false;
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Restore the running state of the stopwatch
        if(wasRunning){
            running = true;
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        // Save the current state of the stopwatch
        outState.putInt("seconds",seconds);
        outState.putBoolean("running",running);
        outState.putBoolean("wasRunning",wasRunning);
    }

    private void runTimer(){
        TextView timeview = findViewById(R.id.textView);
        Handler handler = new Handler();

        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = seconds/3600;
                int secs = (seconds % 3600)/60;
                int millisec = seconds % 60;
                // Format the time display
                String time = String.format(Locale.getDefault(),"%02d:%02d:%02d",minutes,secs,millisec);
                // Update the time display
                timeview.setText(time);
                // Increment the seconds if the stopwatch is running
                if(running){
                    seconds++;
                }
                // Schedule the update every 1 millisecond
                handler.postDelayed(this,0);
            }
        });
    }
}

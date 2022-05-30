package com.example.viewgoal_page;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CustomGoalActivity extends AppCompatActivity {

    EditText goalET;
    EditText durationET;
    TextView dateTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_goal);


        dateTV = findViewById(R.id.date_textView);
        dateTV.setText(setDate());

        Button setButton = (Button) findViewById(R.id.setButton);

        setButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalET = (EditText) findViewById(R.id.goal_editText);
                durationET = (EditText) findViewById(R.id.duration_editTextTime);

                if(goalET.getText().toString().equalsIgnoreCase("")         || //Empty goal
                   durationET.getText().toString().equalsIgnoreCase("")     || //empty duration
                   Integer.valueOf(durationET.getText().toString()) < 1                ){ //duration less than 1min

                    Toast.makeText(CustomGoalActivity.this, "Invalid entry, fill out all fields to set goal.", Toast.LENGTH_LONG).show();
                    return;
                }

                String goal = goalET.getText().toString();
                int duration_mins = Integer.valueOf(durationET.getText().toString());

                Intent intent = new Intent(CustomGoalActivity.this, MainPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("goal", goal);
                intent.putExtra("duration", duration_mins);
                intent.putExtra("date",setDate());
                Toast.makeText(CustomGoalActivity.this,"Goal Set",Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();
            }
        });

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("Set Custom Goal");
        }

    }

    private String setDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
package com.example.viewgoal_page;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class InfoScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_screen);

//        getSupportActionBar().hide();

//        int time_delay_milliseconds = 15000;
//        Handler handler = new Handler();
//
//        Runnable r = new Runnable() {
//            @Override
//            public void run() {
//                finish();
//
//                Intent intent = new Intent(InfoScreenActivity.this, InfoScreenActivity2.class);
//                startActivity(intent);
//            }
////        };
//
//        handler.postDelayed(r, time_delay_milliseconds);

        Button skip;
        skip = findViewById(R.id.finish_button);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InfoScreenActivity.this, InfoScreenActivity2.class);
                startActivity(intent);
            }
        });


    }
}
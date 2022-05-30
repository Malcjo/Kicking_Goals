package com.example.viewgoal_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class SetGoalActivity extends AppCompatActivity {
    Button presetReadBtn;
    Button presetReflect;
    Button customGoalBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_goal);


        customGoalBtn = findViewById(R.id.customGoal_button);
        customGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetGoalActivity.this, CustomGoalActivity.class);

                startActivity(intent);
            }
        });



        presetReadBtn = (Button)findViewById(R.id.readGoalBtn);
        presetReadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(SetGoalActivity.this, MainPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("goal", getResources().getString(R.string.read));
                intent.putExtra("duration", 15);
                intent.putExtra("date",setDate());

                Toast.makeText(SetGoalActivity.this,"Goal Set",Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();
            }
        });

        presetReflect = (Button)findViewById(R.id.reflectGoalBtn);
        presetReflect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetGoalActivity.this, MainPageActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("goal", getResources().getString(R.string.reflect));
                intent.putExtra("duration", 15);
                intent.putExtra("date",setDate());

                Toast.makeText(SetGoalActivity.this,"Goal Set",Toast.LENGTH_SHORT).show();

                startActivity(intent);
                finish();
            }
        });

        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("Set Goal");
        }


    }
    public String setDate(){
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }
}
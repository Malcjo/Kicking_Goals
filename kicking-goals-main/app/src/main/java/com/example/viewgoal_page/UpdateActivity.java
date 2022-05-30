package com.example.viewgoal_page;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    TextView id;
    EditText goal, duration, date;
    Button updateButton, deleteButton;


    /*
     * Variables to store intent data.
     */
    String id_intent, goal_intent, duration_intent, date_intent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        id = findViewById(R.id.id_textView2);
        goal = findViewById(R.id.goal_editText2);
        duration = findViewById(R.id.duration_editTextTime2);
        date = findViewById(R.id.date_editTextDate2);

        getAndSetIntentData();

        updateButton = findViewById(R.id.updateButton);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HistoryDBHelper history = new HistoryDBHelper(UpdateActivity.this);

                String goal2 = goal.getText().toString();
                int duration_mins2 = Integer.valueOf(duration.getText().toString());
                String date2 = date.getText().toString();

                history.updateEntry(Integer.valueOf(id_intent), goal2, duration_mins2, date2);
            }
        });

        deleteButton = findViewById(R.id.deleteButton);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmDeleteDialog();
            }
        });

        /*
         * Setting the action bar to show selected entry as title.
         */
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("View/Edit - " + goal_intent);
        }
    }

    public void getAndSetIntentData(){

        if( getIntent().hasExtra("id") &&
            getIntent().hasExtra("goal") &&
            getIntent().hasExtra("duration") &&
            getIntent().hasExtra("date")){


            /*
             * Getting data from intent
             */
            id_intent = getIntent().getStringExtra("id");
            goal_intent = getIntent().getStringExtra("goal");
            duration_intent = getIntent().getStringExtra("duration");
            date_intent = getIntent().getStringExtra("date");

            /*
             * Setting data to EditText
             */
            id.setText(id_intent);
            goal.setText(goal_intent);
            duration.setText(duration_intent);
            date.setText(date_intent);

        } else {

            Toast.makeText(this, "No Data", Toast.LENGTH_SHORT).show();
        }
    }

    public void confirmDeleteDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Confirm Delete"); Use this if you think it might make the UI look nicer.
        builder.setMessage("Delete " + goal_intent + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HistoryDBHelper history = new HistoryDBHelper(UpdateActivity.this);
                history.deleteEntry(Integer.valueOf(id_intent));
                finish(); //Returns to the previous activity... HistoryActivity
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.show();

    }


}
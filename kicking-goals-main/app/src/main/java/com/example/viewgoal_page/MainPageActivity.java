package com.example.viewgoal_page;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import java.util.Calendar;
import java.util.concurrent.atomic.AtomicBoolean;
public class MainPageActivity extends AppCompatActivity {

    HistoryDBHelper history = new HistoryDBHelper(this);

    private String currentGoal ="";
    private int currentDuration =0;
    private String currentDate = "";

    String goal_Intent, date_Intent;
    int duration_Intent;

    Button newGoalBtn;
    Button kickBtn;
    TextView goalTextView;
    TextView durationTextView;
    TextView dateTextView;
    TextView forTextView;
    TextView kickGoal_textView;
    TextView setGoal_textView;
    TextView noGoal_textView;
    TextView CompleteGoal_textView2;
    Button darkmodeBtn;
    Button change_goal_button;
    Button undo_goal_button;

    /*
     *
     * Overriding menu items to be custom
     *
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_page_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.darkMode_menu) {
            if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES){
                System.out.println("is in dark mode");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
            else
            {
                System.out.println("is in light mode");
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            }

        }

        if(item.getItemId() == R.id.How_to_Use_this_app){
            Intent intent = new Intent(MainPageActivity.this, InfoScreenActivity.class);
            startActivity(intent);
        }

        if(item.getItemId() == R.id.populate_history){
            history.defaultHistory();
        }

        return super.onOptionsItemSelected(item);
    }


    /*
     * Overriding onBackPressed() so that the user does not close the app and resets it.
     */
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        getSupportActionBar().hide();


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        /*
         * Congrats Toast (custom toast appearing for User when they kick the goal)
         */
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.congrats_toast, (ViewGroup) findViewById(R.id.congrats_layout));
        final Toast congratsToast = new Toast(getApplicationContext());
        congratsToast.setGravity(Gravity.CENTER_VERTICAL, 0, 0); //Toast will show centre of sreen
        congratsToast.setDuration(Toast.LENGTH_LONG);
        congratsToast.setView(layout);


        newGoalBtn = (Button)findViewById(R.id.newGoalBtn);
        kickBtn = (Button)findViewById(R.id.kickBtn);
        goalTextView = (TextView)findViewById(R.id.todaysGoalTextView);
        durationTextView = (TextView)findViewById(R.id.todaysDurationlTextView);
        dateTextView = (TextView)findViewById(R.id.todaysDatelTextView);
        forTextView = (TextView) findViewById(R.id.for_textView);
        kickGoal_textView = (TextView) findViewById(R.id.kickGoal_textView);
        setGoal_textView = (TextView) findViewById(R.id.setGoal_textView);
        noGoal_textView = (TextView) findViewById(R.id.noGoal_textView);
        CompleteGoal_textView2 = findViewById(R.id.CompleteGoal_textView2);
        change_goal_button = findViewById(R.id.change_goal_button);
        undo_goal_button = findViewById(R.id.undo_goal_button);

        getAndSetIntendData();

        setVisivilityOfObjects();




        undo_goal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                history.deleteEntryNoToast(history.lastEntry());
                kickBtn.setClickable(true);
                change_goal_button.setVisibility(View.VISIBLE);
                CompleteGoal_textView2.setVisibility(View.GONE);
                kickGoal_textView.setVisibility(View.VISIBLE);
                undo_goal_button.setVisibility(View.GONE);
            }
        });


        change_goal_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, SetGoalActivity.class);
                startActivity(intent);
            }
        });



        newGoalBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, SetGoalActivity.class);
                startActivity(intent);
            }
        });


        kickBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentGoal = getIntent().getStringExtra("goal");
                currentDuration = getIntent().getIntExtra("duration", 0);
                currentDate = getIntent().getStringExtra("date");

                if(currentGoal.equalsIgnoreCase("")          || //Empty goal
                   currentDuration < 1                                  || //duration less than 1min
                   currentDate.equalsIgnoreCase("")          ){
                    Toast.makeText(MainPageActivity.this, "Invalid entry, fill out all fields to set goal.", Toast.LENGTH_LONG).show();
                    return;
                }


                history.createEntry(currentGoal, currentDuration, currentDate);
                congratsToast.show();


//                recreate();
                kickGoal_textView.setVisibility(View.GONE);
                CompleteGoal_textView2.setVisibility(View.VISIBLE);
                change_goal_button.setVisibility(View.GONE);
                kickBtn.setClickable(false);
                undo_goal_button.setVisibility(View.VISIBLE);

            }
        });

        Button histButton = (Button) findViewById(R.id.historyBtn);

        histButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainPageActivity.this, HistoryActivity.class);
                startActivity(intent);

            }
        });




    }

    private void setVisivilityOfObjects(){
        if(currentGoal.equals("") && currentDuration == 0)
        {
            newGoalBtn.setVisibility(View.VISIBLE);
            setGoal_textView.setVisibility(View.VISIBLE);
            noGoal_textView.setVisibility(View.VISIBLE);
            kickBtn.setVisibility(View.GONE);
            goalTextView.setVisibility(View.GONE);
            durationTextView.setVisibility(View.GONE);
            dateTextView.setVisibility(View.GONE);
            forTextView.setVisibility(View.GONE);
            kickGoal_textView.setVisibility(View.GONE);
            change_goal_button.setVisibility(View.GONE);
            undo_goal_button.setVisibility(View.GONE);


        }
        else
        {
            newGoalBtn.setVisibility(View.GONE);
            setGoal_textView.setVisibility(View.GONE);
            noGoal_textView.setVisibility(View.GONE);
            kickBtn.setVisibility(View.VISIBLE);
            goalTextView.setVisibility(View.VISIBLE);
            durationTextView.setVisibility(View.VISIBLE);
            dateTextView.setVisibility(View.VISIBLE);
            forTextView.setVisibility(View.VISIBLE);
            kickGoal_textView.setVisibility(View.VISIBLE);
            change_goal_button.setVisibility(View.VISIBLE);

        }
    }


    public void getAndSetIntendData(){
        if(getIntent().hasExtra("goal")
        && getIntent().hasExtra("duration")
        && getIntent().hasExtra("date"))
        {
            goal_Intent = getIntent().getStringExtra("goal");
            duration_Intent =  getIntent().getIntExtra("duration", 0);
            date_Intent = getIntent().getStringExtra("date");

            currentGoal = goal_Intent;
            currentDuration = duration_Intent;
            currentDate = date_Intent;

            goalTextView.setText(currentGoal);
            durationTextView.setText(currentDuration + " Minutes");
            dateTextView.setText("by end of " + currentDate);
        }
        else{

        }
    }

    public void createAlarm() {
        int DATA_FETCHER_RC = 123;
        AlarmManager mAlarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, DATA_FETCHER_RC, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        mAlarmManager.setInexactRepeating(AlarmManager.RTC, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);

    }
    public class AlarmReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            currentGoal = "";
            currentDuration = 0;
            currentDate = "";
            getIntent().putExtra("goal", "");
            getIntent().putExtra("duration", 0);
            getIntent().putExtra("date", "");
            recreate();
        }
    }
}

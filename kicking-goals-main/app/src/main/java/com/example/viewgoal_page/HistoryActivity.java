package com.example.viewgoal_page;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    HistoryDBHelper history;
    ArrayList<String> idList, goalList, dateList;
    ArrayList<Integer> durationList;
    TextView noKickedGoals;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);



        history = new HistoryDBHelper(HistoryActivity.this);
        idList = new ArrayList<>();
        goalList = new ArrayList<>();
        durationList = new ArrayList<>();
        dateList = new ArrayList<>();


        /*
         * Setting text to display empty history if no records present.
         */
        noKickedGoals = (TextView) findViewById(R.id.noData_textView);

        /*
         * Storing data from SQL database to arrays to display.
         */
        storeDataInArrays();


        recyclerView = findViewById(R.id.recView);
        CustomAdapter customAdapter = new CustomAdapter(HistoryActivity.this, this, idList, goalList, durationList, dateList);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
         * Setting the action bar to show selected entry as title.
         */
        ActionBar ab = getSupportActionBar();
        if(ab != null){
            ab.setTitle("History");
        }






    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            recreate(); //refreshes the history activity to start again - should be with the new values.
        }
    }

    public void storeDataInArrays(){
        Cursor cursor = history.readAllData();

        if(cursor.getCount() == 0){
            Toast.makeText(this, "No entry", Toast.LENGTH_SHORT).show();
            noKickedGoals.setVisibility(View.VISIBLE);

        } else {

            while (cursor.moveToNext()){

                idList.add(cursor.getString(0));
                goalList.add(cursor.getString(1));
                durationList.add(cursor.getInt(2));
                dateList.add(cursor.getString(3));
            }

            Toast.makeText(this, "Tap entry to View/Edit", Toast.LENGTH_SHORT).show();
            noKickedGoals.setVisibility(View.GONE);
            System.out.println(noKickedGoals);

        }

    }

    /*
     * Sets new option with our created resource in menufolder.
     * Adding the delete icon in. It automatically shifts it to the right handside.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.delete_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    /* DELETE ALL ICON == TRASH CAN on the actionbar
     * Basically allows us to set onclick listener for the delete icon.
     * TODO: [NON CRITICAL]
     *  User should not be able to delete, if the RecyclerView is already empty
     *  Have tried if recyclerView != null, doesn't work will need to figure out.
     *  NON CRITICAL.
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.deleteAll){
//            if(recyclerView != null){
//                System.out.println("Not null");
//            }
            confirmDeleteAllDialog();

        }

        return super.onOptionsItemSelected(item);
    }

    public void confirmDeleteAllDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("Confirm Delete"); Use this if you think it might make the UI look nicer.
        builder.setMessage("Delete History. This action cannot be undone?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                HistoryDBHelper history = new HistoryDBHelper(HistoryActivity.this);
                history.deleteAllEntry();
                Intent intent = new Intent(HistoryActivity.this, HistoryActivity.class);
                startActivity(intent);
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
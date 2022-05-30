package com.example.viewgoal_page;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class HistoryDBHelper extends SQLiteOpenHelper {

    private Context context;
    private static final int DATABASE_VERSION = 1;

    /* DECLARING SQL TABLES*/
    private static final String DATABASE_NAME = "kickingGoals.db";
    private static final String TABLE_NAME = "history_table";
    private static final String COL1 = "ID";
    private static final String COL2 = "GOAL";
    private static final String COL3 = "DURATION_MINS";
    private static final String COL4 = "DATE_COMPLETE";


    public HistoryDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_NAME + " (" +
                COL1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL2 + " TEXT, " +
                COL3 + " INTEGER, " +
                COL4 + " TEXT);";

        db.execSQL(createTable);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        /*
         * On app upgrade, the db will drop the table if it exists already.
         * Otherwise it will create a brand new table.
         * This is not good! If Users want to keep their history when app is upgraded.
         * For better db migration between app upgrades visit
         * https://thebhwgroup.com/blog/how-android-sqlite-onupgrade
         */


        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);

    }

    public void createEntry(String goal, int duration_mins, String date_complete){
        SQLiteDatabase db = this.getWritableDatabase(); //refer to this SQLiteHelperClass
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL2, goal);
        contentValues.put(COL3, duration_mins);
        contentValues.put(COL4, date_complete);

        long result = db.insert(TABLE_NAME , null, contentValues);

        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Success", Toast.LENGTH_LONG).show();
        }
    }

    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = null;

        if(db != null){
           cursor = db.rawQuery(query, null);
        }

        return cursor;
    }


    /*
     * Update existing entry
     */
    public void updateEntry(int id,String goal, int duration_mins, String date_complete){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();


        System.out.println("ID: " + id);
        System.out.println("Goal: " + goal);
        System.out.println("Duration: " + duration_mins);
        System.out.println("Date: " + date_complete);


        contentValues.put(COL2, goal);
        contentValues.put(COL3, duration_mins);
        contentValues.put(COL4, date_complete);

        long result = db.update(TABLE_NAME, contentValues, " ID = ?", new String[]{String.valueOf(id)});

        if(result == -1){
            Toast.makeText(context, "Update Fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Update Success", Toast.LENGTH_LONG).show();
        }
    }



    /*
     * Deletes a single entry in the database.
     */
    public void deleteEntry(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, " ID = ?", new String[]{String.valueOf(id)});

        if(result == -1){
            Toast.makeText(context, "Delete Fail", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Delete Success", Toast.LENGTH_LONG).show();
        }


    }

    /*
     * Deletes a single entry in the database. No toast given. Used for undoing a completed task from the main page.
     */
    public void deleteEntryNoToast(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        long result = db.delete(TABLE_NAME, " ID = ?", new String[]{String.valueOf(id)});

//        if(result == -1){
//            Toast.makeText(context, "Delete Fail", Toast.LENGTH_LONG).show();
//        } else {
//            Toast.makeText(context, "Delete Success", Toast.LENGTH_LONG).show();
//        }


    }


    /*
     * Deletes all entry in the table. NON-RECOVERABLE
     */
    public void deleteAllEntry(){
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }


    /*
     * Finds last entry. This is used when undoing a completed goal.
     *
     */
    public int lastEntry(){
        Cursor cursor = readAllData();
        cursor.moveToPosition(cursor.getCount() - 1);

        int rowID = Integer.valueOf(cursor.getString(0));

        return rowID;



    }

    /*
     *Populate database with default values so that history has something.
     *
     */
    public void defaultHistory(){
        createEntry("Piano", 30, "10-10-2021");
        createEntry("Piano", 20, "11-10-2021");
        createEntry("Piano", 20, "12-10-2021");
        createEntry("Piano", 10, "13-10-2021");
        createEntry("Exercise", 10, "14-10-2021");
        createEntry("Exercise", 15, "15-10-2021");
        createEntry("Exercise", 5, "16-10-2021");
        createEntry("Family", 30, "17-10-2021");
        createEntry("Family", 30, "18-10-2021");
        createEntry("Family", 30, "19-10-2021");
        createEntry("Watch lecture", 10, "20-10-2021");
        createEntry("Watch lecture", 20, "21-10-2021");
        createEntry("Watch lecture", 10, "22-10-2021");
        createEntry("Android", 300, "23-10-2021");
        createEntry("Android", 300, "24-10-2021");

    }






}

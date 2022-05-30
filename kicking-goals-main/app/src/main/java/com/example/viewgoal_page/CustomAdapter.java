package com.example.viewgoal_page;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.MyViewHolder>{

    private Context context;

    /**
     *
     * Refereshing activity, so that when something is deleted or updated, the recycleView fetches the latest data from
     * SQLite.
     */
    Activity activity;


    private ArrayList idList, goalList, durationList, dateList;

    public CustomAdapter(Activity activity, Context context, ArrayList idList, ArrayList goalList, ArrayList durationList, ArrayList dateList){
        this.activity = activity;
        this.context = context;
        this.idList = idList;
        this.goalList = goalList;
        this.durationList = durationList;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        holder.id.setText(String.valueOf(idList.get(position)));
        holder.goal.setText(String.valueOf(goalList.get(position)));
        holder.duration_mins.setText(String.valueOf(durationList.get(position)));
        holder.date.setText(String.valueOf(dateList.get(position)));

        /*
         * Creating a click listener. When the card is clicked, will open the UpdateActivity.class
         * Also passing values of that card to the next class with ... intent.putExtra()...
         */
        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateActivity.class);

                intent.putExtra("id", String.valueOf(idList.get(position)));
                intent.putExtra("goal",String.valueOf(goalList.get(position)));
                intent.putExtra("duration",String.valueOf(durationList.get(position)));
                intent.putExtra("date",String.valueOf(dateList.get(position)));

//                context.startActivity(intent);
                //Refresh
                activity.startActivityForResult(intent, 1);

            }
        });
    }

    @Override
    public int getItemCount() {

        return idList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView id, goal, duration_mins, date;
        LinearLayout cardLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            id = itemView.findViewById(R.id.id_textView);
            goal = itemView.findViewById(R.id.goal_textView);
            duration_mins = itemView.findViewById(R.id.duration_textView);
            date = itemView.findViewById(R.id.date_textView);
            cardLayout = itemView.findViewById(R.id.cardLayout);
        }
    }
}

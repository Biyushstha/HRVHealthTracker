package com.example.hrvhealthtracker.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.hrvhealthtracker.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.DateViewHolder> {

    private final List<Date> dateList;
    private final Context context;

    public CalendarAdapter(Context context, List<Date> dateList) {
        this.context = context;
        this.dateList = dateList;
    }

    @NonNull
    @Override
    public DateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.calendar_item, parent, false);
        return new DateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DateViewHolder holder, int position) {
        Date date = dateList.get(position);

        SimpleDateFormat dayFormat = new SimpleDateFormat("EEE", Locale.getDefault()); // Mon
        SimpleDateFormat numFormat = new SimpleDateFormat("d", Locale.getDefault());   // 6

        holder.dayLabel.setText(dayFormat.format(date));
        holder.dateNumber.setText(numFormat.format(date));

        Calendar today = Calendar.getInstance();
        Calendar itemDate = Calendar.getInstance();
        itemDate.setTime(date);

        if (today.get(Calendar.DAY_OF_YEAR) == itemDate.get(Calendar.DAY_OF_YEAR) &&
                today.get(Calendar.YEAR) == itemDate.get(Calendar.YEAR)) {
            holder.dateNumber.setBackgroundResource(R.drawable.calendar_circle_selected);
        } else {
            holder.dateNumber.setBackground(null);
        }
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    static class DateViewHolder extends RecyclerView.ViewHolder {
        TextView dayLabel, dateNumber;

        public DateViewHolder(@NonNull View itemView) {
            super(itemView);
            dayLabel = itemView.findViewById(R.id.day_label);
            dateNumber = itemView.findViewById(R.id.date_number);
        }
    }
}

package com.cuvora.smartalarmclock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cuvora.smartalarmclock.R;
import com.cuvora.smartalarmclock.classes.Alarm;

import java.util.ArrayList;

/**
 * Created by Pranjal on 03-01-2017.
 * The BaseAdapter for alarm list displayed in the MainActivity.
 * !! Needs full implementation. !!
 */

public class AlarmListAdapter extends BaseAdapter {

    private ArrayList<Alarm> alarms;
    private Context context;

    public AlarmListAdapter (Context context, ArrayList<Alarm> alarms) {
        this.context = context;
        this.alarms = alarms;
    }

    @Override
    public int getCount() {
        return alarms.size();
    }

    @Override
    public Object getItem(int i) {
        return alarms.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.alarm_list_item, viewGroup, false);
        }
        Alarm alarm = (Alarm) getItem(i);
        ((TextView) view.findViewById(R.id.alarm_time_to_reach))
                .setText(
                        "Time : "
                                + alarm.getTimeToReach()
                );
        return view;
    }
}

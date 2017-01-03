package com.cuvora.smartalarmclock.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cuvora.smartalarmclock.R;
import com.cuvora.smartalarmclock.classes.Alarm;

import java.util.ArrayList;

/**
 * Created by Pranjal on 03-01-2017.
 * The BaseAdapter for alarm list displayed in the MainActivity.
 */

public class AlarmListAdapter extends BaseAdapter {

    private ArrayList<Alarm> alarms;
    private Context context;
    private AlarmSwitchListener listener;

    public AlarmListAdapter (Context context, ArrayList<Alarm> alarms,
                             AlarmSwitchListener listener) {
        this.context = context;
        this.alarms = alarms;
        this.listener = listener;
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
    public View getView(final int i, View view, ViewGroup viewGroup) {
        // ViewHolder design for smoother scrolling.
        ViewHolder viewHolder;

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.alarm_list_item, viewGroup, false);
            viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        // Get the alarm for position i.
        Alarm alarm = (Alarm) getItem(i);

        String time = alarm.getTimeToReach() + "";
        viewHolder.alarmTimeToReach.setText(time);
        viewHolder.destinationName.setText(alarm.getEndLocation().getName());
        boolean[] weekdays = Alarm.breakRepeatCode(alarm.getRepeatCode());
        for (int j = 0; j < 7; j++) {
            if (weekdays[j]) {
                viewHolder.weekdays[j].setTextColor(Color.argb(200, 50, 200, 70));
            } else {
                viewHolder.weekdays[j].setTextColor(Color.argb(200, 150, 150, 150));
            }
        }

        // Set the onCheckedChangeListener and the state of the switch.
        // Set alarmSwitch checked only after setting the new onCheckedChangeListener()
        viewHolder.alarmSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                listener.onSwitchChanged(i, b);
            }
        });
        // This statement should follow the statement above.
        viewHolder.alarmSwitch.setChecked(alarm.isOn());
        return view;
    }

    public interface AlarmSwitchListener {
        void onSwitchChanged (int position, boolean isOn);
    }

    // ViewHolder class to make the ListView experience faster and smoother.
    private static class ViewHolder {
        TextView alarmTimeToReach;
        TextView destinationName;
        TextView[] weekdays;
        SwitchCompat alarmSwitch;

        ViewHolder (View view) {
            alarmTimeToReach = (TextView) view.findViewById(R.id.alarm_time_to_reach);
            destinationName = (TextView) view.findViewById(R.id.alarm_end_location);
            weekdays = new TextView[7];
            LinearLayout weekdaysParent = (LinearLayout) view.findViewById(R.id.weekdays_parent);
            for (int i = 0; i < 7; i++) {
                weekdays[i] = (TextView) weekdaysParent.getChildAt(i);
            }
            alarmSwitch = (SwitchCompat) view.findViewById(R.id.alarm_switch);
        }
    }

}

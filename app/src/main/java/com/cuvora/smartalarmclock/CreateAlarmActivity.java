package com.cuvora.smartalarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cuvora.smartalarmclock.classes.Alarm;
import com.cuvora.smartalarmclock.classes.Location;

public class CreateAlarmActivity extends AppCompatActivity {

    public static final String NEW_ALARM = "New Alarm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        //Dummy!!!!
        Intent data = new Intent();
        data.putExtra(NEW_ALARM, new Alarm(Double.valueOf(Math.random()*1000).longValue(),
                0,
                new Location("Source Address", 0.02, 0.12),
                new Location("Destination Address", 0.01, 0.23),
                12344));
        setResult(RESULT_OK, data);
        finish();
    }
}

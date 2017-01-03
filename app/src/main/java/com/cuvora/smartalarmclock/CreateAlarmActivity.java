package com.cuvora.smartalarmclock;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.cuvora.smartalarmclock.classes.Alarm;

public class CreateAlarmActivity extends AppCompatActivity {

    public static final String NEW_ALARM = "New Alarm";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_alarm);

        //Dummy!!!!
        Intent data = new Intent();
        data.putExtra(NEW_ALARM, new Alarm(Double.valueOf(Math.random()*1000).longValue(),
                0, null, null, 12345));
        setResult(RESULT_OK, data);
        finish();
    }
}

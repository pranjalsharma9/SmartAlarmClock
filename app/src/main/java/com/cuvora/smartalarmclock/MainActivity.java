package com.cuvora.smartalarmclock;

import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.cuvora.smartalarmclock.adapters.AlarmListAdapter;
import com.cuvora.smartalarmclock.classes.Alarm;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static final String ALARM_DATA_FILE = "alarmData";
    public static final String TAG = "MainActivity";
    public static final int ADD_ALARM_REQUEST = 0;

    private ArrayList<Alarm> alarms;
    private AlarmListAdapter alarmListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Display the list of alarms
        alarms = readAlarms();
        alarmListAdapter = new AlarmListAdapter(this, alarms);
        ((ListView) findViewById(R.id.alarm_list)).setAdapter(alarmListAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        final Context thisActivity = this;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                Intent createAlarmIntent = new Intent(thisActivity, CreateAlarmActivity.class);
                startActivityForResult(createAlarmIntent, ADD_ALARM_REQUEST);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private ArrayList<Alarm> readAlarms () {
        ArrayList<Alarm> alarms = new ArrayList<>();
        try {
            ObjectInputStream in = new ObjectInputStream(
                    openFileInput(ALARM_DATA_FILE)
            );
            int alarmCount = in.readInt();
            for (int i = 0; i < alarmCount; i++) {
                alarms.add((Alarm) in.readObject());
            }
            in.close();
        } catch (FileNotFoundException fnfe) {
            Log.v(TAG, "Creating the Alarms file");
            try {
                ObjectOutputStream out = new ObjectOutputStream(
                        openFileOutput(ALARM_DATA_FILE, Context.MODE_PRIVATE)
                );
                out.writeInt(0);
                out.flush();
                out.close();
            } catch (FileNotFoundException fnfe2) {
                Log.v(TAG, "Problem while creating file!\n" + fnfe2.toString());
            } catch (IOException ioe) {
                Log.v(TAG, "IOE while creating file!\n" + ioe.toString());
            }
        } catch (IOException ioe) {
            Log.v(TAG, "IOException!\n" + ioe.toString());
        } catch (ClassNotFoundException cnfe) {
            Log.v(TAG, "CLASS NOT FOUND!\n" + cnfe.toString());
        }
        Log.v("alarm", "returned alarms with size " + alarms.size());
        //Log.v("alarm", "alarm time " + (alarms.get(0) == null));
        return alarms;
    }

    private void writeAlarms (ArrayList<Alarm> alarms) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(
                    openFileOutput(ALARM_DATA_FILE, Context.MODE_PRIVATE)
            );
            int alarmCount = alarms.size();
            out.writeInt(alarmCount);
            for (int i = 0; i < alarmCount; i++) {
                out.writeObject(alarms.get(i));
            }
            out.flush();
            out.close();
        } catch (FileNotFoundException fnfe2) {
            Log.v(TAG, "Problem while writing alarms!\n" + fnfe2.toString());
        } catch (IOException ioe) {
            Log.v(TAG, "IOE while writing alarms!\n" + ioe.toString());
        }
    }

    private void updateAlarmList (Alarm alarm) {
        alarms.add(alarm);
        alarmListAdapter.notifyDataSetChanged();
        writeAlarms(alarms);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if ((requestCode == ADD_ALARM_REQUEST) && (resultCode == RESULT_OK) && (data != null)) {
            Alarm newAlarm = (Alarm) data.getSerializableExtra(CreateAlarmActivity.NEW_ALARM);
            updateAlarmList(newAlarm);
        }
    }

}

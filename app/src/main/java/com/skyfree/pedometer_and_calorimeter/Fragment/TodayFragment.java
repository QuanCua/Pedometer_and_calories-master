package com.skyfree.pedometer_and_calorimeter.Fragment;


import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.skyfree.pedometer_and_calorimeter.HistoryList;
import com.skyfree.pedometer_and_calorimeter.Interface.StepListener;
import com.skyfree.pedometer_and_calorimeter.Key;
import com.skyfree.pedometer_and_calorimeter.MainActivity;
import com.skyfree.pedometer_and_calorimeter.Object.TrainingInformation;
import com.skyfree.pedometer_and_calorimeter.R;
import com.skyfree.pedometer_and_calorimeter.Sensor.StepDetector;
import com.yuan.waveview.WaveView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import me.itangqi.waveloadingview.WaveLoadingView;


/**
 * A simple {@link Fragment} subclass.
 */
public class TodayFragment extends Fragment implements SensorEventListener, StepListener {

    RelativeLayout layoutColor;
    ToggleButton buttonSwitch;
    WaveView waveView;
    FloatingActionMenu buttonMenu;
    FloatingActionButton buttonAchievements, buttonHistory, buttonRefresh, buttonTurnOff;
    SensorManager sensorManager;
    TextView txtNumSteps;
    TextView txtDistance;
    Chronometer chrono;
    TextView txtGoal;

    private long timeWhenStopped = 0;
    private StepDetector simpleStepDetector;
    private Sensor accel;
    private int numSteps = 0;
    private float distance = 0.0f;
    int myHeight = 75;
    private SharedPreferences pre;
    private SharedPreferences.Editor editor;
    private int stepGoal = 0;

    public TodayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_today, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        simpleStepDetector = new StepDetector();
        simpleStepDetector.registerListener(this);

        pre = getActivity().getPreferences(Context.MODE_PRIVATE);
        editor = pre.edit();

        stepGoal = pre.getInt(Key.STEP_GOAL,0);
        numSteps = pre.getInt(Key.NUMBER_STEP,0);


        waveView.setMax(stepGoal);
        txtGoal.setText("Goal: " + stepGoal );
        int progress = (numSteps * 100 )/stepGoal;

        waveView.setProgress(progress);
        txtNumSteps.setText(""+numSteps);

        sensorManager = (SensorManager)this.getActivity().getSystemService(Context.SENSOR_SERVICE);
        accel = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        chrono.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener(){
            @Override
            public void onChronometerTick(Chronometer chronometer) {
                long time = SystemClock.elapsedRealtime() - chronometer.getBase();
                int h   = (int)(time /3600000);
                int m = (int)(time - h*3600000)/60000;
                int s= (int)(time - h*3600000- m*60000)/1000 ;
                String t = (h < 10 ? "0"+h: h)+":"+(m < 10 ? "0"+m: m)+":"+ (s < 10 ? "0"+s: s);
                chronometer.setText(t);
            }
        });
        chrono.setBase(SystemClock.elapsedRealtime());
        chrono.setText("00:00:00");

        buttonSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttonSwitch.isChecked()){
                    sensorManager.registerListener(TodayFragment.this, accel, SensorManager.SENSOR_DELAY_FASTEST);
                    chrono.setBase(SystemClock.elapsedRealtime() + timeWhenStopped);
                    chrono.start();
                }else{
                    sensorManager.unregisterListener(TodayFragment.this);
                    timeWhenStopped = chrono.getBase() - SystemClock.elapsedRealtime();
                    chrono.stop();
                }
            }
        });

        buttonHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), HistoryList.class);
                startActivity(intent);
            }
        });

        buttonRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });

        super.onActivityCreated(savedInstanceState);
    }

    public void initUI(View view){
        waveView = (WaveView) view.findViewById(R.id.waveView);

        buttonSwitch = (ToggleButton) view.findViewById(R.id.toggleButtonSwitch);
        buttonMenu = (FloatingActionMenu) view.findViewById(R.id.buttonMenu);
        buttonAchievements = (FloatingActionButton) view.findViewById(R.id.buttonAchievements);
        buttonHistory = (FloatingActionButton) view.findViewById(R.id.buttonHistory);
        buttonRefresh = (FloatingActionButton) view.findViewById(R.id.buttonRefresh);
        buttonTurnOff = (FloatingActionButton) view.findViewById(R.id.buttonTurnOff);

        chrono  = (Chronometer)view.findViewById(R.id.stopwatchValues);

        txtNumSteps = (TextView) view.findViewById(R.id.number);
        txtDistance = (TextView) view.findViewById(R.id.distanceValues);
        txtGoal = (TextView) view.findViewById(R.id.target);
    }

    public void openDialog() {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setCancelable(false);
        TextView msg = new TextView(getActivity());
        msg.setText("Are you sure you want to reset today's running steps?");
        msg.setPadding(10, 30, 10, 10);
        msg.setTextSize(20);
        msg.setGravity(Gravity.CENTER);
        alertDialog.setView(msg);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL,"YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //set all = 0
            }
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE,"CANCEL", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        new Dialog(getActivity());
        alertDialog.show();

        final Button yesButton = alertDialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        LinearLayout.LayoutParams neutralBtnLP = (LinearLayout.LayoutParams) yesButton.getLayoutParams();
        neutralBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        yesButton.setPadding(50, 10, 10, 10);
        yesButton.setTextColor(Color.BLUE);
        yesButton.setLayoutParams(neutralBtnLP);

        final Button cancelBT = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        LinearLayout.LayoutParams negBtnLP = (LinearLayout.LayoutParams) yesButton.getLayoutParams();
        negBtnLP.gravity = Gravity.FILL_HORIZONTAL;
        cancelBT.setTextColor(Color.RED);
        cancelBT.setLayoutParams(negBtnLP);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            simpleStepDetector.updateAccel(sensorEvent.timestamp, sensorEvent.values[0], sensorEvent.values[1], sensorEvent.values[2]);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void step(long timeNs) {
        //Step
        numSteps++;
        txtNumSteps.setText(""+numSteps);

        editor.putInt(Key.NUMBER_STEP,numSteps);
        editor.commit();

        //Wave view
        int progress = (numSteps * 100) / stepGoal;
        waveView.setProgress(progress);

        //Calories
//        String getGenderValue;
//        getGenderValue = pre.getString(Key.GENDER, "");
//        if(getGenderValue == "Male"){
//            //tính calo
//        }
//        else{
//            //tính calo
//        }

        //Distance
        distance = (myHeight*numSteps)/100000.0f;
        DecimalFormat df = new DecimalFormat("0.00");
        txtDistance.setText(""+df.format(distance));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

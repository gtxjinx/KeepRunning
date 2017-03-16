package com.example.administrator.keeprunning;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

/**
 * Created by Administrator on 2017/1/5.
 */

public class StepActivity extends AppCompatActivity implements SensorEventListener{
    private SensorManager mSensorManager;
    private Sensor mSensorCounter;
    private float Steps;
    private SharedPreferences sharedPreferences;
    private MyView myview;
    private float calories;
    private int aim;
    private TextView caloriestv;
    private TextView aimtv;
    private TextView stepstv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_steps);
        sharedPreferences = getSharedPreferences("useInfo", Context.MODE_PRIVATE);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this,mSensorCounter,SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }



    private void init() {
        caloriestv= (TextView) findViewById(R.id.calories);
        aimtv= (TextView) findViewById(R.id.aim);
        myview= (MyView) findViewById(R.id.progress);
        stepstv= (TextView) findViewById(R.id.textViewsteps);
//        Intent intent =getIntent();
//        Bundle bundle =intent.getBundleExtra("key");
        int length=parseInt(sharedPreferences.getString("walk_length",""));
        //Toast.makeText(this, ""+length, Toast.LENGTH_SHORT).show();
        myview.setmAim(length);
        mSensorManager= (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorCounter=mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Steps=event.values[0];
        myview.setPercent((int)Steps);
        int Stepint = (int) Steps;
        stepstv.setText(Integer.toString(Stepint)+"步");
        calories=(Steps/2000)*parseInt(sharedPreferences.getString("weight",""))*1.036f;
        aim=parseInt(sharedPreferences.getString("walk_length",""));
        if(Steps>aim)
            aimtv.setText("您已完成今日目标");
        caloriestv.setText("今日消耗了"+Float.toString(calories)+"千卡路里");
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

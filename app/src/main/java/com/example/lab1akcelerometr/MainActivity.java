package com.example.lab1akcelerometr;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    int r = 0, g = 0, b = 0;
    TextView textView_xaxis;
    TextView textView_yaxis;
    TextView textView_zaxis;
    Button button;
    Boolean is_clicked = false;
    View view;

    private SensorManager sensorManager;
    private Sensor sensorAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.textView_xaxis = (TextView) findViewById(R.id.textView_xaxis);
        this.textView_yaxis = (TextView) findViewById(R.id.textView_yaxis);
        this.textView_zaxis = (TextView) findViewById(R.id.textView_zaxis);
        this.button = (Button) findViewById(R.id.button);
        this.view = (View) findViewById(R.id.view);

        this.sensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        this.sensorAcc = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        this.sensorManager.registerListener(this, sensorAcc, SensorManager.SENSOR_DELAY_NORMAL);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                is_clicked = !is_clicked;
            }
        });
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (is_clicked == true){
            this.textView_xaxis.setText("X:" + event.values[0]);
            this.textView_yaxis.setText("Y:" + event.values[1]);
            this.textView_zaxis.setText("Z:" + event.values[2]);

            r+=event.values[0]; if( r < 0) r = 0; if ( r > 255) r = 255;
            g+=event.values[1]; if( g < 0) g = 0; if ( g > 255) g = 255;
            b+=event.values[2]; if( b < 0) b = 0; if ( b > 255) b = 255;

            getWindow().getDecorView().setBackgroundColor(Color.rgb(r,g,b));

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) view.getLayoutParams();
            params.horizontalBias = (-event.values[0]+10)/20;
            params.verticalBias = (event.values[1]+10)/20;
            view.setLayoutParams(params);

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
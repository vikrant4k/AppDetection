package vik.com.appdetection.background.listener;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

public class LightSensor implements SensorEventListener {
    public static float illuminance=0;

    @Override
    public void onSensorChanged(SensorEvent event) {
         illuminance= event.values[0];
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}

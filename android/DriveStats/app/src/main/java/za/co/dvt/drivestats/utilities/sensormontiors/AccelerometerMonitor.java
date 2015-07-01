package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;

import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class AccelerometerMonitor
        implements Monitor {

    private SensorManager manager;

    private Sensor accelerometer;

    private SensorState state = SensorState.getInstance();

    public AccelerometerMonitor(Context context) {
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) throw new RuntimeException("SHIT"); //TODO: Handle what happens when they don't have accelerometer
        else manager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void stop() {
        manager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        state.setMaxXDeflection(event.values[0]);
        state.setMaxYDeflection(event.values[1]);
        state.setMaxZDeflection(event.values[2]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO: Find out if this is important
    }
}

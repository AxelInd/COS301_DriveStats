package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import za.co.dvt.drivestats.threadmanagment.exceptions.AccelerometerServiceUnavailableException;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class AccelerometerMonitor
        implements Monitor, SensorEventListener {

    private SensorManager manager;

    private Sensor accelerometer;

    private SensorState state = SensorState.getInstance();

    private final Context context;

    public AccelerometerMonitor(Context context) {
        this.context = context;
        manager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        if (accelerometer == null) throw new AccelerometerServiceUnavailableException("Unable to access accelerometer.");
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

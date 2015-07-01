package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class GpsMonitor implements Monitor, LocationListener {
    private LocationManager manager;

    private Sensor gps;

    private SensorState state = SensorState.getInstance();

    public GpsMonitor(Context context) {
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            //TODO: what happens if the location service is off? Request it to be on
        }
        if (!manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
            //TODO: what happens if the network service is off? Request it to be on
        } else {

        }

    }

    @Override
    public void stop() {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //TODO: What happened with this method?
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

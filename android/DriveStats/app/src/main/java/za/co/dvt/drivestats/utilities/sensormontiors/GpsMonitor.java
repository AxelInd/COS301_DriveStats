package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class GpsMonitor implements Monitor, LocationListener {

    private final LocationManager locationManager;


    private final SensorState state = SensorState.getInstance();

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1;

    public GpsMonitor() {
        Context context = Inject.currentContext();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES
                , MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        Location lastKnowLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(lastKnowLocation != null) {
            state.setLocation(lastKnowLocation.getLatitude(), lastKnowLocation.getLongitude());
            state.setSpeed(lastKnowLocation.getSpeed());
        }
    }

    @Override
    public void stop() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        state.setLocation(location.getLatitude(), location.getLongitude());
        state.setSpeed(location.getSpeed());
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

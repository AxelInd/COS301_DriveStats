package za.co.dvt.drivestats.utilities.sensormontiors;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;

import za.co.dvt.drivestats.threadmanagment.exceptions.LocationServiceUnavailableException;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class GpsMonitor implements Monitor, LocationListener {

    private final LocationManager locationManager;

    private final Context context;

    private final SensorState state = SensorState.getInstance();

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1; // 1 minute

    public GpsMonitor(Context context) {
        this.context = context;
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (canUseGpsService()) {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME_BW_UPDATES
                    , MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
        }
    }

    private boolean canUseGpsService() {
        if (!resourcesAvailable()) {
            askNicely();
            if (!resourcesAvailable()) {
                throw new LocationServiceUnavailableException("Cannot track users location");
            }
        }
        return true;
    }

    private boolean resourcesAvailable() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void askNicely() {
        String title = "";
        String message = "";
        AlertDialog.Builder alert = new AlertDialog.Builder(context);
        alert.setTitle(title);
        alert.setMessage(message);

        alert.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                context.startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
    }

    @Override
    public void stop() {
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    @Override
    public void onLocationChanged(Location location) {
        //TODO: Make sure this is the right way around
        state.setLocation(location.getLatitude(), location.getLongitude());
        state.setSpeed(location.getSpeed());
    }

    @Override
    public void onProviderDisabled(String provider) {
        if(provider.equals(LocationManager.GPS_PROVIDER)) {
            askNicely();
            if (!resourcesAvailable()) {
                throw new LocationServiceUnavailableException("GPS service has been switched off");
            }
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    @Override
    public void onProviderEnabled(String provider) {

    }
}

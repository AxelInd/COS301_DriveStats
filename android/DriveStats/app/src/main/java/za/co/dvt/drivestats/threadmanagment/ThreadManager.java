package za.co.dvt.drivestats.threadmanagment;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import za.co.dvt.drivestats.utilities.SensorUtilities;
import za.co.dvt.drivestats.utilities.Settings;
import za.co.dvt.drivestats.utilities.sensormontiors.Monitor;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class ThreadManager {

    //TODO: Inject the thead state object
    private ThreadState state;

    List<Monitor> monitors = new ArrayList<>(3);

    private void runSensorMonitor(Context context) {
        //TODO: Create and launch the sensor monitoring thread
        monitors.add(SensorUtilities.getAccelerometerMonitor(context));
        monitors.add(SensorUtilities.getLocationMonitor(context));
        monitors.add(SensorUtilities.getSpeedMonitor(context));
    }

    private void runUploadThread() {
        //TODO: Create and launch the upload monitor thread if the wifi-only mode is not active
    }

    public void start(Context context) {
        runSensorMonitor(context);
        if (!Settings.getInstance().isWifiOnlyMode()) {
            runUploadThread();
        }
    }

    public void stop() {
        for(Monitor monitor : monitors) {
            monitor.stop();
        }
    }

}

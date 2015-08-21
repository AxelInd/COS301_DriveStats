package za.co.dvt.drivestats.threadmanagment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.drivestats.utilities.SensorUtilities;
import za.co.dvt.drivestats.utilities.Settings;
import za.co.dvt.drivestats.utilities.exceptions.AccelerometerServiceUnavailableException;
import za.co.dvt.drivestats.utilities.exceptions.LocationServiceUnavailableException;
import za.co.dvt.drivestats.utilities.exceptions.MonitorException;
import za.co.dvt.drivestats.utilities.sensormontiors.Monitor;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class ThreadManager {

    private static final int EXPECTED_NUMBER_OF_MONITORS = 3;

    private List<Monitor> monitors = new ArrayList<>(EXPECTED_NUMBER_OF_MONITORS);

    private Monitor uploadMonitor = null;

    private static final ThreadManager instance = new ThreadManager();

    private ThreadManager() { }

    public static ThreadManager getInstance() {
        return instance;
    }

    private void runSensorMonitor() {
        //TODO: Create and launch the sensor monitoring thread
        try {
            monitors.add(SensorUtilities.getAccelerometerMonitor());
            monitors.add(SensorUtilities.getGpsMonitor());
            monitors.add(SensorUtilities.getOfflineWriter());
        } catch (LocationServiceUnavailableException e) {
            //TODO: User isn't setting GPS to be on?? Show message or something
            Log.d("Exception", "This happened: " + e.getClass());
            stop();
        } catch (AccelerometerServiceUnavailableException e) {
            // TODO: BOLOX user cannot use the application or has done something funny and their accelerometer is not usable
            Log.d("Exception", "This happened: " + e.getMessage());
            stop();
        } catch (MonitorException e) {
            // TODO: At the moment this is something wrong with starting the Offline Writer
            Log.d("Exception", "This happened: " + e.getMessage());
            stop();
        } catch (Throwable t) {
            Log.d("Exception", "This happened: " + t.getClass());
            t.printStackTrace();
        }
    }

    public void runUploadThread() {
        if (ThreadState.isRunning()) {
            uploadMonitor = null; // TODO: create the real monitor and start
        }
    }

    public void stopUploadThread() {
        if (ThreadState.isRunning() && uploadMonitor != null) {
            uploadMonitor.stop();
            uploadMonitor = null;
        }
    }

    public void start() {
        if (ThreadState.tryStart()) {
            runSensorMonitor();
            if (!Settings.getInstance().isWifiOnlyMode()) {
                runUploadThread();
            }
        }
    }

    public void stop() {
        if (ThreadState.tryStop()) {
            stopUploadThread();
            for (Monitor monitor : monitors) {
                try {
                    monitor.stop();
                } catch (Throwable e) {
                    //TODO: If something goes wrong I don't want the loop to terminate
                    //Not sure what to do here
                }
            }
            monitors = new ArrayList<>(EXPECTED_NUMBER_OF_MONITORS);
        }
    }

}

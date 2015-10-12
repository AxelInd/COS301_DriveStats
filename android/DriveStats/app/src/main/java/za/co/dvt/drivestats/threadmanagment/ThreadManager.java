package za.co.dvt.drivestats.threadmanagment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.drivestats.services.sensors.SensorService;
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

    private static final ThreadManager instance = new ThreadManager();

    private ThreadManager() { }

    public static ThreadManager getInstance() {
        return instance;
    }

    private void runSensorMonitor() {
        //TODO: Create and launch the sensor monitoring thread
        try {
            monitors.add(SensorService.getAccelerometerMonitor());
            monitors.add(SensorService.getGpsMonitor());
            monitors.add(SensorService.getOfflineWriter());
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


    public void start() {
        if (ThreadState.tryStart()) {
            runSensorMonitor();
        }
    }

    public void stop() {
        if (ThreadState.tryStop()) {
            for (Monitor monitor : monitors) {
                try {
                    monitor.stop();
                } catch (Throwable e) {
                    // Do nothing
                }
            }
            monitors = new ArrayList<>(EXPECTED_NUMBER_OF_MONITORS);
        }
    }

}

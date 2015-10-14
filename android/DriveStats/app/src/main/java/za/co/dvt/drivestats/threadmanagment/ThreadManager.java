package za.co.dvt.drivestats.threadmanagment;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import za.co.dvt.drivestats.services.sensors.SensorService;
import za.co.dvt.drivestats.utilities.exceptions.MonitorException;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;
import za.co.dvt.drivestats.utilities.sensormontiors.Monitor;
import za.co.dvt.drivestats.utilities.sensormontiors.OfflineWriter;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class ThreadManager {

    private static final int EXPECTED_NUMBER_OF_MONITORS = 3;

    private List<Monitor> monitors = new ArrayList<>(EXPECTED_NUMBER_OF_MONITORS);

    private OfflineWriter offlineWriter;

    private static final ThreadManager instance = new ThreadManager();

    private ThreadManager() {
    }

    public static ThreadManager getInstance() {
        return instance;
    }

    private void runSensorMonitor() {
        try {
            monitors.add(SensorService.getAccelerometerMonitor());
            monitors.add(SensorService.getGpsMonitor());
            offlineWriter = SensorService.getOfflineWriter();
        } catch (MonitorException e) {
            Log.d("Exception", "This happened: " + e.getMessage());
            stop(throwException(e));
        }
    }

    private OfflineWriter.WritingStop throwException(final Throwable t) {
        return new OfflineWriter.WritingStop() {
            @Override
            public void onStopWriting() {
                throw new SystemException("Something has gone wrong with the sensors", t);
            }
        };
    }


    public void start() {
        if (ThreadState.tryStart()) {
            runSensorMonitor();
        }
    }

    public void stop(OfflineWriter.WritingStop callback) {
        if (ThreadState.tryStop()) {
            stopMonitorList();
            offlineWriter.stopWriting(callback);
        }
    }

    private void stopMonitorList() {
        for (Monitor monitor : monitors) {
            try {
                monitor.stop();
            } catch (Throwable e) {
                // Do nothing so that the others are stopped
            }
        }
        monitors = new ArrayList<>(EXPECTED_NUMBER_OF_MONITORS);
    }

}

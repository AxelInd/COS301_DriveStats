package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;
import za.co.dvt.drivestats.utilities.Constants;
import za.co.dvt.drivestats.utilities.OfflineUtilities;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Nicholas on 2015-07-13.
 */
public class OfflineWriter implements Monitor {

    private static final AtomicBoolean RUNNING = new AtomicBoolean(false);

//    private static final int SLEEP_TIME = 1000; // 1 Seconds
    private static final long SLEEP_TIME = SECONDS.toMillis(1); // 1 Seconds

    private static final SensorState SENSOR_STATE = SensorState.getInstance();

    private static FileOutputStream FILE_WRITER;

    public OfflineWriter() {
        startWriting();

        Log.d("Testing", Inject.currentContext().getFilesDir().getAbsolutePath());
        try {
            //TODO: Provide file name
            FILE_WRITER = Inject.currentContext().openFileOutput(Constants.OFFLINE_FILE_NAME, Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            //TODO: Handle File not found
            e.printStackTrace();
        }
    }

    private void startWriting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (RUNNING.compareAndSet(false, true)) {
                    while (RUNNING.get()) {
                        try {
                            Thread.sleep(SLEEP_TIME);
                            String stateString = getStateString();
                            OfflineUtilities.writeToOfflineStorage(FILE_WRITER, stateString);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }

    private double [] lastLocation;

    private String getStateString() {
        StringBuilder builder = new StringBuilder();
        double[] currentLocation = SENSOR_STATE.getLocation();

        //If location hasn't changed then nothing can have changed
        if (lastLocation != null
                && lastLocation[0] == currentLocation[0]
                && lastLocation[1] == currentLocation[1]) {
            return "-";
        }

        builder.append(Arrays.toString(SENSOR_STATE.getLocation()))
                .append(Constants.OFFLINE_FILE_DELINEATION)
                .append(SENSOR_STATE.getSpeed())
                .append(Constants.OFFLINE_FILE_DELINEATION)
                .append(SENSOR_STATE.getCorrectedMaxXDeflection())
                .append(Constants.OFFLINE_FILE_DELINEATION)
                .append(SENSOR_STATE.getCorrectedMaxYDeflection())
                .append(Constants.OFFLINE_FILE_DELINEATION)
                .append(SENSOR_STATE.getCorrectedMaxZDeflection())
                .append(Constants.OFFLINE_FILE_DELINEATION);
        return builder.toString();
    }

    @Override
    public void stop() {
        RUNNING.set(false);
    }

}

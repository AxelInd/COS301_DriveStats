package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;
import za.co.dvt.drivestats.utilities.Constants;
import za.co.dvt.drivestats.utilities.OfflineUtilities;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;

import static java.util.concurrent.TimeUnit.SECONDS;

/**
 * Created by Nicholas on 2015-07-13.
 */
public class OfflineWriter implements Monitor {

    private static final AtomicBoolean RUNNING = new AtomicBoolean(false);

    private static final long SLEEP_TIME = SECONDS.toMillis(1); // 1 Seconds

    private static final SensorState SENSOR_STATE = SensorState.getInstance();

    private static FileOutputStream FILE_WRITER;

    private final AtomicBoolean stillWriting = new AtomicBoolean(false);

    public OfflineWriter() {
        startWriting();
        try {
            FILE_WRITER = Inject.currentContext().openFileOutput(Constants.OFFLINE_FILE_NAME, Context.MODE_APPEND);
        } catch (FileNotFoundException e) {
            throw new SystemException("Error in open offline file", e);
        }
    }

    private void startWriting() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (RUNNING.compareAndSet(false, true)) {
                    stillWriting.set(true);
                    while (RUNNING.get()) {
                        try {
                            Thread.sleep(SLEEP_TIME);
                            String stateString = getStateString();
                            OfflineUtilities.writeToOfflineStorage(FILE_WRITER, stateString);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    try {
                        FILE_WRITER.flush();
                    } catch (IOException e) {
                        throw new SystemException("Could not flush the file", e);
                    } finally {
                        stillWriting.getAndSet(false);
                    }
                }
            }
        }).start();
    }

    private double[] lastLocation;

    private String getStateString() {
        StringBuilder builder = new StringBuilder();
//        double[] currentLocation = SENSOR_STATE.getLocation();
//
//        //If location hasn't changed then nothing can have changed
//        if (lastLocation != null
//                && lastLocation[0] == currentLocation[0]
//                && lastLocation[1] == currentLocation[1]) {
//            return "-";
//        }
//
//        lastLocation = currentLocation;

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

    public void stopWriting(WritingStop callback) {
        stop();
        long giveUp = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(5);
        while (stillWriting.compareAndSet(false, false)) {
            if (System.currentTimeMillis() >= giveUp) {
                throw new SystemException("Writer is not releasing the file");
            }
        }
        callback.onStopWriting();
    }

    @Override
    public void stop() {
        RUNNING.set(false);
    }

    public interface WritingStop {
        void onStopWriting();
    }

}

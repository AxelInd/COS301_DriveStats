package za.co.dvt.drivestats.utilities.sensormontiors;

import android.content.Context;

import java.util.concurrent.atomic.AtomicBoolean;

import za.co.dvt.drivestats.utilities.Constants;

/**
 * Created by Nicholas on 2015-07-07.
 */
public class OfflineWriter implements Monitor {

    private AtomicBoolean running = new AtomicBoolean(false);

    private Context context;

    public OfflineWriter() {
        if (running.compareAndSet(false, true)) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (running.get()) {
                        try {
                            writeState();
                            Thread.sleep(Constants.OFFLINE_WRITE_INTERVAL);
                        } catch (InterruptedException e) {
                            //TODO: Handle this exception
                        }
                    }
                }
            }).start();
        }
    }

    @Override
    public void stop() {
        running.compareAndSet(true, false);
    }

    public void writeState() {
        //TODO: Write the state offline
    }

}

package za.co.dvt.drivestats.threadmanagment;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class ThreadState {

    private AtomicBoolean running = new AtomicBoolean(false);

    public boolean isRunning() {
        return running.get();
    }

    public void start() {
        running.set(true);
    }

    public void stop() {
        running.set(false);
    }

}

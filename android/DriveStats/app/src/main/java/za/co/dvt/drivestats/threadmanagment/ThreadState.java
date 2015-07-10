package za.co.dvt.drivestats.threadmanagment;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class ThreadState {

    private static final AtomicBoolean running = new AtomicBoolean(false);

    public static boolean isRunning() {
        return running.get();
    }

    public static boolean tryStart() {
        return running.compareAndSet(false, true);
    }

    public static boolean tryStop() {
        return running.compareAndSet(true, false);
    }

}

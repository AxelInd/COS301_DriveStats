package za.co.dvt.drivestats.Injection;

import android.content.Context;

import za.co.dvt.drivestats.services.TripTracingService;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;
import za.co.dvt.drivestats.utilities.Settings;
import za.co.dvt.drivestats.utilities.UserProfile;

/**
 * Created by Nicholas on 2015-08-10.
 */
public class Inject {

    private static Context currentContext;

    public static Context currentContext() {
        return currentContext;
    }

    public static void setCurrentContext(Context currentContext) {
        Inject.currentContext = currentContext;
    }

    public static Settings settings() {
        Settings settings = Settings.getInstance();
        settings.loadSettings();
        return settings;
    }

    private static final TripTracingService TRIP_TRACING_SERVICE = new TripTracingService();

    public static TripTracingService tripTracingService() {
        return TRIP_TRACING_SERVICE;
    }

    public static UserProfile userProfile() {
        return UserProfile.getInstance();
    }

    public static SensorState sensorState() {
        return SensorState.getInstance();
    }

}

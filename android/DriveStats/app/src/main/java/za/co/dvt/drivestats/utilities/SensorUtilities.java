package za.co.dvt.drivestats.utilities;

import android.content.Context;

import za.co.dvt.drivestats.utilities.sensormontiors.AccelerometerMonitor;
import za.co.dvt.drivestats.utilities.sensormontiors.Monitor;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class SensorUtilities  {


    public static Monitor getAccelerometerMonitor(Context context) {
        return new AccelerometerMonitor(context);
    }

    public static Monitor getSpeedMonitor(Context context) {
        //TODO: get the speed from the GPS and return it
        return null;
    }

    public static Monitor getLocationMonitor(Context context) {
        //TODO: correct return type if needed and get the location from the GPS and return it
        return null;
    }

    public static int getSpeedLimit() {
        //TODO: Move this method to where it belongs? get speed limit from somewhere and return
        return 120;
    }

}

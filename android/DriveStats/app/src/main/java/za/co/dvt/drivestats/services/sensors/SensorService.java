package za.co.dvt.drivestats.services.sensors;

import za.co.dvt.drivestats.utilities.exceptions.MonitorException;
import za.co.dvt.drivestats.utilities.sensormontiors.AccelerometerMonitor;
import za.co.dvt.drivestats.utilities.sensormontiors.GpsMonitor;
import za.co.dvt.drivestats.utilities.sensormontiors.Monitor;
import za.co.dvt.drivestats.utilities.sensormontiors.OfflineWriter;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class SensorService {


    public static Monitor getAccelerometerMonitor() {
        try {
            return new AccelerometerMonitor();
        } catch (MonitorException e) {
            //TODO: What happens when there is no accelerometers
            //Show message or something
            throw e;
        }
    }

    public static Monitor getGpsMonitor() {
        try {
            return new GpsMonitor();
        } catch (MonitorException e) {
            //TODO: User is not putting the GPS one show message or something.
            throw e;
        }
    }

    public static OfflineWriter getOfflineWriter() {
        try {
            return new OfflineWriter();
        } catch (Throwable e) {
            throw new MonitorException(e);
        }
    }

    public static int getSpeedLimit() {
        //TODO: Move this method to where it belongs? get speed limit from somewhere and return
        return 120;
    }

}

package za.co.dvt.drivestats.utilities.exceptions;

/**
 * Created by Nicholas on 2015-07-07.
 */
public class LocationServiceUnavailableException extends MonitorException {

    public LocationServiceUnavailableException() {
    }

    public LocationServiceUnavailableException(String detailMessage) {
        super(detailMessage);
    }

    public LocationServiceUnavailableException(Throwable throwable) {
        super(throwable);
    }
}

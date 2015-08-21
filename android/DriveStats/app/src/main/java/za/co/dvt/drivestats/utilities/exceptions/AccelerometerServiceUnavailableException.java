package za.co.dvt.drivestats.utilities.exceptions;

/**
 * Created by Nicholas on 2015-07-07.
 */
public class AccelerometerServiceUnavailableException extends MonitorException {
    public AccelerometerServiceUnavailableException() {
    }

    public AccelerometerServiceUnavailableException(String detailMessage) {
        super(detailMessage);
    }

    public AccelerometerServiceUnavailableException(Throwable throwable) {
        super(throwable);
    }
}

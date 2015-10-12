package za.co.dvt.drivestats.utilities.exceptions;

public class MonitorException extends RuntimeException {

    public MonitorException() {
    }

    public MonitorException(String detailMessage) {
        super(detailMessage);
    }

    public MonitorException(Throwable throwable) {
        super(throwable);
    }
}

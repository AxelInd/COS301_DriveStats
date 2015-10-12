package za.co.dvt.drivestats.utilities.exceptions;

/**
 * Created by Nicholas on 2015-08-20.
 */
public class SystemException extends RuntimeException {
    public SystemException() {
    }

    public SystemException(String detailMessage) {
        super(detailMessage);
    }

    public SystemException(String detailMessage, Throwable throwable) {
        super(detailMessage, throwable);
    }

    public SystemException(Throwable throwable) {
        super(throwable);
    }
}

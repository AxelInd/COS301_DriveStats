package za.co.dvt.drivestats.resources.datasending;

/**
 * Created by Nicholas on 2015-08-10.
 */
public enum SoapMethod {

    GET_USER_ID("/mobileClientConnection.asmx");

    private final String method;

    SoapMethod(String method) {
        this.method = method;
    }

    public String getMethod() {
        return method;
    }
}

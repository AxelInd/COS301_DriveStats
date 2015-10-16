package za.co.dvt.drivestats.resources.network;

/**
 * Created by Nicholas on 2015-09-10.
 */
public enum Methods {
    USER_LOGIN("login/"),
    ADD_TRIP("addtrip/");

    private static final String url = "http://drivestatstest.cloudapp.net/RestService.svc/";

    private final String method;

    Methods(String method) {
        this.method = method;
}

    public String getUrl() {
        return url + method;
    }

}

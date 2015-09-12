package za.co.dvt.drivestats.resources.network;

/**
 * Created by Nicholas on 2015-09-10.
 */
public enum Methods {
    USER_LOGIN("login/"),
    ADD_TRIP("addtrip/");

    private static final String url = "http://drivestatsrest.cloudapp.net/RestService.svc/";
//    private static final String url = "http://192.168.1.3:59997/RestService.svc/";

    private final String method;

    Methods(String method) {
        this.method = method;
}

    public String getUrl() {
        return url + method;
    }

}

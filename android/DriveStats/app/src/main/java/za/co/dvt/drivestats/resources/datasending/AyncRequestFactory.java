package za.co.dvt.drivestats.resources.datasending;

/**
 * Created by Nicholas on 2015-08-10.
 */
public class AyncRequestFactory {

    public static AsyncRequest createRequest(SoapMethod method) {
        return new AsyncSoapClient(method.getMethod());
    }
}

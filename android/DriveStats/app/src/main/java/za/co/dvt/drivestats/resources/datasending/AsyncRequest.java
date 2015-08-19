package za.co.dvt.drivestats.resources.datasending;

/**
 * Created by Nicholas on 2015-08-10.
 */
public interface AsyncRequest {

    void setProperty(String name, Object value);

    void send(CallBack callBack);

}

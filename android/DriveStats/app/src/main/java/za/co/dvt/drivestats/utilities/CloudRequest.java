package za.co.dvt.drivestats.utilities;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Nicholas on 2015-07-16.
 */
public class CloudRequest {

    private String URL = "http://drivestats.cloudapp.net/mobileClientConnection.asmx";
//    private String URL = "http://drivestats.cloudapp.net/";

    private final RequestParams params = new RequestParams();

    private final AsyncHttpClient client;

    private final Action action;

    public CloudRequest(Action action) {
        this.action = action;
        client = new AsyncHttpClient();
//        client.removeHeader("Content-Length");
//        client.addHeader("Content-Length", Integer.toString(action.getSoapXml().length()));
        client.addHeader("SOAPAction", action.getSoapAction());
    }

    public void addParameter(Parameter parameter, String value) {
//        params.add(parameter.getName(), value);
        action.addParameter(parameter.getName(), value);
    }

    public void post(ResponseHandlerInterface handler) {
        params.add("<?xml version", action.getSoapXml());
        client.post(URL, params, handler);
    }

    public void get(ResponseHandlerInterface handler) {
        new AsyncHttpClient().get(URL, params, handler);
    }

    public enum Action {
        GET_USER_ID(""),
        HELLO_WORLD("HelloWorld"),
        UPLOAD_DATA_TO_SERVER("");

        private String soapAction;

        private String soapUri = "http://tempuri.org/";

        private final Map<String, String> parameters = new HashMap<>();

        Action(String soapAction) {
            this.soapAction = soapAction;
        }

        public String getSoapAction() {
            return soapUri + soapAction;
        }

        public String getSoapXml() {
            StringBuilder builder = new StringBuilder("\"1.0\" encoding=\"utf-8\"?>" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">" +
                    "  <soap:Body>");
            builder.append("<" + soapAction + "xmlns=\"" + soapUri + "\" >");
            for (Map.Entry<String, String> entry : parameters.entrySet()) {
                builder.append("<" + entry.getKey() + ">" + entry.getValue() + "</" + entry.getKey() + ">");
            }
            builder.append("</" + soapAction + ">");
            builder.append("</soap:Body>" +
                    "</soap:Envelope>");
            return builder.toString();
        }

        public void addParameter(String key, String value) {
            this.parameters.put(key, value);
        }
    }

    public enum Parameter {
        EMAIL("email"),
        DATA("data");

        private String name;

        Parameter(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

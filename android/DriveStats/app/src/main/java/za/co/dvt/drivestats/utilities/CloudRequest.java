package za.co.dvt.drivestats.utilities;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.ResponseHandlerInterface;

/**
 * Created by Nicholas on 2015-07-16.
 */
public class CloudRequest {

    private String URL = "";

    private final RequestParams params = new RequestParams();

    public CloudRequest(Action action) {
        this.URL += action.getAction();
    }

    public void addParameter(Parameter parameter, String value) {
        params.add(parameter.getName(), value);
    }

    public void post(ResponseHandlerInterface handler) {
        new AsyncHttpClient().post(URL, params, handler);
    }

    public void get(ResponseHandlerInterface handler) {
        new AsyncHttpClient().get(URL, params, handler);
    }

    public enum Action {
        GET_USER_ID("");

        private String action;

        Action(String action) {
            this.action = action;
        }

        public String getAction() {
            return action;
        }
    }

    public enum Parameter {
        EMAIL("email");

        private String name;

        Parameter(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}

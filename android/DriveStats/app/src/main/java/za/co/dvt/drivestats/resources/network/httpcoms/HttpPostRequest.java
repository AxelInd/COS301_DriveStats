package za.co.dvt.drivestats.resources.network.httpcoms;

import android.util.Log;

import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.Methods;
import za.co.dvt.drivestats.resources.network.request.Request;
import za.co.dvt.drivestats.resources.network.response.Response;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class HttpPostRequest<R extends Response> extends HttpRequest<R> {

    public HttpPostRequest(Request request, Class<R> responseClass, Methods method, Callback<R> callback) {
        super(request, responseClass, method, callback);
    }

    @Override
    protected R doInBackground(Void... params) {
        Log.d(">>>>>>>> Problem json", request.getParameters());
        return server.postForObject(method.getUrl(), request, responseClass);
    }
}

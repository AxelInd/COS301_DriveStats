package za.co.dvt.drivestats.resources.network.httpcoms;

import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.Methods;
import za.co.dvt.drivestats.resources.network.request.Request;
import za.co.dvt.drivestats.resources.network.response.Response;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class HttpGetRequest<R extends Response> extends HttpRequest<R> {

    public HttpGetRequest(Request request, Class<R> responseClass, Methods method, Callback<R> callback) {
        super(request, responseClass, method, callback);
    }

    @Override
    protected R doInBackground(Void... params) {
        String url = method.getUrl() + request.getParameters();
        return server.getForObject(url, responseClass);
    }
}

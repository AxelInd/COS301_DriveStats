package za.co.dvt.drivestats.resources.network.httpcoms;

import android.os.AsyncTask;

import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;

import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.Methods;
import za.co.dvt.drivestats.resources.network.request.Request;
import za.co.dvt.drivestats.resources.network.response.Response;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;

/**
 * Created by Nicholas on 2015-09-09.
 */
public abstract class HttpRequest<R extends Response> extends AsyncTask<Void, Void, R> {

    protected final Request request;

    protected final Class<R> responseClass;

    protected final Methods method;

    protected final RestTemplate server;

    protected final Callback<R> callback;

    public HttpRequest(Request request, Class<R> responseClass, Methods method, Callback<R> callback) {
        this.request = request;
        this.responseClass = responseClass;
        this.method = method;
        this.callback = callback;

        server = new RestTemplate();
        server.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
    }

    @Override
    protected void onPostExecute(R r) {
        super.onPostExecute(r);
        try {
            callback.invoke(r);
        } catch (IOException e) {
            throw new SystemException("Problem invoking callback", e);
        }
    }
}

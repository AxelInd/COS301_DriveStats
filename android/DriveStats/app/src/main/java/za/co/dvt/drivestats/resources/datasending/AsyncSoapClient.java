package za.co.dvt.drivestats.resources.datasending;

import android.os.AsyncTask;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by Nicholas on 2015-08-10.
 */
public class AsyncSoapClient
        implements AsyncRequest {

    private static final String SOAP_ACTION = "http://tempuri.org/login";
    private static final String NAMESPACE = "http://tempuri.org/";
    private String URL = "http://10.0.0.10:4567";
//    private static final String URL = "http://drivestats.cloudapp.net/";

    private final SoapObject request;

    private CallBack callBack;

    public AsyncSoapClient(String method) {
        URL += method;
        request = new SoapObject(NAMESPACE, method);
    }

    private void t(String message) {
        Log.d(">>>>>>>>>", message);
    }


    @Override
    public void setProperty(String name, Object value) {
        request.addProperty(name, value);
    }

    @Override
    public void send(CallBack callBack) {
        String[] ret = new String[0];
        this.callBack = callBack;
        new SoapTask().execute();
    }

    private class SoapTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... params) {
            t("NPE??? 1");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER12);
            t("NPE??? 2");
            envelope.setOutputSoapObject(request);
            t("NPE??? 3");
            HttpTransportSE httpTransportSE = new HttpTransportSE(URL);
            t("NPE??? 4");
            httpTransportSE.debug = true;
            try {
                t("NPE??? 5 " + httpTransportSE.toString() + "   " + envelope.toString() + "  " + SOAP_ACTION);
                httpTransportSE.call(SOAP_ACTION, envelope);
                t("NPE??? 6");
                SoapObject response = (SoapObject) envelope.getResponse();
                t("NPE??? 7");
                callBack.callBack(new String[]{response.getProperty(1).toString()});
                t("NPE??? 8");
                return null;
            } catch (IOException | XmlPullParserException e) {
                //TODO: Handle exceptions
                e.printStackTrace();
                return null;
            } catch (Throwable t) {
                t(t.toString() + " " + t.getMessage() + "  " + t.getCause());
                return null;
            }
        }
    }
}

package za.co.dvt.drivestats.resources.login;

import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;

/**
 * Created by Nicholas on 2015-09-28.
 */
public interface LoginCallback {

    void onConnected(Bundle bundle);

    void onConnectionSuspended(int i);

    void onConnectionFailed(ConnectionResult connectionResult);

}

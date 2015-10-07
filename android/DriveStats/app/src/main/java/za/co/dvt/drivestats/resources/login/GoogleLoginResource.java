package za.co.dvt.drivestats.resources.login;

import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.PersonBuffer;

import za.co.dvt.drivestats.Injection.Inject;

/**
 * Created by Nicholas on 2015-09-28.
 */
public class GoogleLoginResource implements LoginResource {

    public static GoogleApiClient googleApiClient;

    public static LoginCallback loginCallback;

    @Override
    public void authenticate(LoginCallback callback) {
        loginCallback = callback;
        if (googleApiClient != null) disconnect();
        googleApiClient = new GoogleApiClient.Builder(Inject.currentContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Plus.API)
                .addScope(new Scope(Scopes.PROFILE))
                .build();
        googleApiClient.connect();
    }

    private void disconnect() {
        if (googleApiClient != null) googleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (loginCallback != null) loginCallback.onConnected(bundle);
    }

    @Override
    public void onConnectionSuspended(int i) {
        if (loginCallback != null) loginCallback.onConnectionSuspended(i);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (loginCallback != null) loginCallback.onConnectionFailed(connectionResult);
    }

    @Override
    public void onResult(People.LoadPeopleResult loadPeopleResult) {
        if (loadPeopleResult.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
            PersonBuffer personBuffer = loadPeopleResult.getPersonBuffer();
            try {
                int count = personBuffer.getCount();
                for (int i = 0; i < count; i++) {
                    Log.d(">>>>> Testing login", "Display name: " + personBuffer.get(i).getDisplayName()); //TODO: Remove log
                }
            } finally {
                personBuffer.release();
                disconnect();
            }
        }
    }
}

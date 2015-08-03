package za.co.dvt.drivestats.utilities.sensormontiors;

import android.app.PendingIntent;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.People;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import java.util.Set;

import za.co.dvt.drivestats.threadmanagment.exceptions.MonitorException;

/**
 * Created by Nicholas on 2015-07-21.
 */
public class GoogleUtilities {

    private static final String TAG = "android-plus-quickstart";
    private static final String SERVER_BASE_URL = "SERVER_BASE_URL";


    private static final int STATE_DEFAULT = 0;
    private static final int STATE_IN_PROGRESS = 2;

    private static final String WEB_CLIENT_ID = "WEB_CLIENT_ID";

    private GoogleApiClient mGoogleApiClient;
    private int mSignInProgress;
    private PendingIntent mSignInIntent;
    private int mSignInError;

    private boolean mRequestServerAuthCode = false;

    private final Context context;

    public GoogleUtilities(Context context) {
        this.context = context;
        mGoogleApiClient = buildGoogleApiClient();
    }

    public GoogleApiClient buildGoogleApiClient() {
        Log.d("Testing", "Starting");
        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(getSuccessListener())
                .addOnConnectionFailedListener(getFailListener())
                .addApi(Plus.API, Plus.PlusOptions.builder().build())
                .addScope(Plus.SCOPE_PLUS_LOGIN);

        if (mRequestServerAuthCode) {
            checkServerAuthConfiguration();
            builder = builder.requestServerAuthCode(WEB_CLIENT_ID, getCallbacks());
        }
        return builder.build();
    }

    private GoogleApiClient.ServerAuthCodeCallbacks getCallbacks() {
        return new GoogleApiClient.ServerAuthCodeCallbacks() {
            @Override
            public CheckResult onCheckServerAuthorization(String s, Set<Scope> set) {
                Log.d("Testing", "s");
                return null;
            }

            @Override
            public boolean onUploadServerAuthCode(String s, String s1) {
                return false;
            }
        };
    }

    private GoogleApiClient.OnConnectionFailedListener getFailListener() {
        Log.d("Testing", "Starting failure listener");
        return new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(ConnectionResult connectionResult) {
                Log.i(TAG, "onConnectionFailed: ConnectionResult.getErrorCode() = "
                        + connectionResult.getErrorCode());

                if (connectionResult.getErrorCode() == ConnectionResult.API_UNAVAILABLE) {
                    Log.w(TAG, "API Unavailable.");
                } else if (mSignInProgress != STATE_IN_PROGRESS) {
                    mSignInIntent = connectionResult.getResolution();
                    mSignInError = connectionResult.getErrorCode();
                }
            }
        };
    }

    private GoogleApiClient.ConnectionCallbacks getSuccessListener() {
        Log.d("Testing", "Starting success listener");
        return new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle bundle) {
                Person currentUser = Plus.PeopleApi.getCurrentPerson(mGoogleApiClient);

                Plus.PeopleApi.loadVisible(mGoogleApiClient, null)
                        .setResultCallback(getResultCallback());

                mSignInProgress = STATE_DEFAULT;

                Log.d("UserProfile", "Nick Name: " + currentUser.getNickname());
                Log.d("UserProfile", "Name: " + String.valueOf(currentUser.getName()));
                Log.d("UserProfile", "about me: " + currentUser.getAboutMe());
                Log.d("UserProfile", "ID: " + currentUser.getId());
            }

            private ResultCallback<People.LoadPeopleResult> getResultCallback() {
                return new ResultCallback<People.LoadPeopleResult>() {
                    @Override
                    public void onResult(People.LoadPeopleResult loadPeopleResult) {

                    }
                };
            }

            @Override
            public void onConnectionSuspended(int i) {

            }
        };
    }

    private void checkServerAuthConfiguration() {
        if ("WEB_CLIENT_ID".equals(WEB_CLIENT_ID) ||
                "SERVER_BASE_URL".equals(SERVER_BASE_URL)) {
            Log.w(TAG, "WEB_CLIENT_ID or SERVER_BASE_URL configured incorrectly.");
            throw new MonitorException("WEB_CLIENT_ID or SERVER_BASE_URL configured incorrectly.");
        }
    }
}

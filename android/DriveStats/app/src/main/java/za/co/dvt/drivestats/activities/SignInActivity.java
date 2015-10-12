package za.co.dvt.drivestats.activities;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.plus.Plus;

import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.response.UserId;
import za.co.dvt.drivestats.services.network.NetworkService;
import za.co.dvt.drivestats.utilities.OfflineUtilities;


public class SignInActivity extends Activity
        implements ConnectionCallbacks, OnConnectionFailedListener {

    private static final int RC_SIGN_IN = 0;

    private boolean isResolving = false;

    private boolean shouldResolve = false;

    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);
        if (checkOffline()) {
            gotoTripContext();
        } else {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(Plus.API)
                    .addScope(Plus.SCOPE_PLUS_LOGIN)
                    .addScope(Plus.SCOPE_PLUS_PROFILE)
                    .addScope(new Scope(Scopes.EMAIL))
                    .build();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (googleApiClient != null && googleApiClient.isConnected())
            googleApiClient.disconnect();
    }

    private void gotoTripContext() {
        Intent tripIntent = new Intent(this, TripActivity.class);
        tripIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(tripIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sign_in, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.signInUsingGoogle)
    public void signInUsingGoogle() {
        shouldResolve = true;
        googleApiClient.connect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            if (resultCode != RESULT_OK) {
                shouldResolve = false;
            }

            isResolving = false;
            googleApiClient.connect();
        }
    }

    private boolean checkOffline() {
        return OfflineUtilities.getUserProfile();
    }

    @Override
    public void onConnected(Bundle bundle) {
        shouldResolve = false;
        String email = Plus.AccountApi.getAccountName(googleApiClient);
        NetworkService.login(email, new Callback<UserId>() {
            @Override
            public void invoke(UserId result) {
                gotoTripContext();
            }
        });
    }

    @Override
    public void onConnectionSuspended(int i) {
        shouldResolve = true;
        googleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!isResolving && shouldResolve) {
            if (connectionResult.hasResolution()) {
                try {
                    connectionResult.startResolutionForResult(this, RC_SIGN_IN);
                    isResolving = true;
                } catch (IntentSender.SendIntentException e) {
                    isResolving = false;
                    googleApiClient.connect();
                }
            } else {
                Toast.makeText(this, "Could not sign in using Google+", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(this, "Signed Out", Toast.LENGTH_LONG).show();
        }
    }
}

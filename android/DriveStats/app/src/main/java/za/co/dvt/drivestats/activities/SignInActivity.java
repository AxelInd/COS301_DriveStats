package za.co.dvt.drivestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.common.api.GoogleApiClient;

import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.response.UserId;
import za.co.dvt.drivestats.services.network.NetworkService;
import za.co.dvt.drivestats.utilities.OfflineUtilities;
import za.co.dvt.drivestats.utilities.sensormontiors.GoogleUtilities;


public class SignInActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);
        if (checkOffline()) {
            gotoTripContext();
        }
    }

    private void gotoTripContext() {
        Intent tripIntent = new Intent(Inject.currentContext(), TripActivity.class);
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

    @Override
    protected void onResume() {
        finish();
    }

    @OnClick(R.id.signInUsingGoogle)
    public void signInUsingGoogle() {
        //TODO: Make the could utils sign in using google method return the email address
//        return CloudUtilities.signUpUsingGoogle();
        GoogleUtilities utilities = new GoogleUtilities(getApplicationContext());
        GoogleApiClient client = utilities.buildGoogleApiClient();
        client.connect();
//        String emailAddress = "ntrpilot@gmail.com";
//        singUp(emailAddress);
    }

    //TODO: Remove this method
    @OnClick(R.id.byPass)
    public void goThrough() {
        NetworkService.login("ntrpilot@gmail.com", new Callback<UserId>() {
            @Override
            public void invoke(UserId result) {
                gotoTripContext();
            }
        });
    }

    private boolean checkOffline() {
        return OfflineUtilities.getUserProfile();
    }

}

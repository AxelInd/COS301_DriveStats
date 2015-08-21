package za.co.dvt.drivestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.http.Header;

import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.utilities.CloudRequest;
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
        startActivity(new Intent(this, TripActivity.class));
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
        gotoTripContext();
    }

    private boolean checkOffline() {
        return OfflineUtilities.getUserProfile();
    }

    private void singUp(String emailAddress) {
        CloudRequest request = new CloudRequest(CloudRequest.Action.GET_USER_ID);
        request.addParameter(CloudRequest.Parameter.EMAIL, emailAddress);
        request.post(createHandler());
    }

    private AsyncHttpResponseHandler createHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String response = new String(responseBody);
                successfulLogin(statusCode, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                String response = "There is no message";
                if (responseBody != null) {
                    response = new String(responseBody);
                }
                unsuccessfulLogin(statusCode, response);
            }
        };
    }

    private void successfulLogin(int code, String response) {
        //TODO: extract the ID, make it int and do stuff with it.
        Toast.makeText(getApplicationContext(), "Success (" + code + ")! this server said: " + response, Toast.LENGTH_LONG).show();
    }

    private void unsuccessfulLogin(int code, String response) {
        //TODO: extract the ID, make it int and do stuff with it.
        Toast.makeText(getApplicationContext(), "Failure(" + code + ")! this server said: " + response, Toast.LENGTH_LONG).show();
    }
}

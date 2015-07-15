package za.co.dvt.drivestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.R;
//import za.co.dvt.drivestats.utilities.CloudUtilities;
import za.co.dvt.drivestats.utilities.OfflineUtilities;
import za.co.dvt.drivestats.utilities.UserProfile;


public class SignInActivity extends AppCompatActivity {
    //    private static final int STATE_DEFAULT = 0;
//    private static final int STATE_SIGN_IN = 1;
//    private static final int STATE_IN_PROGRESS = 2;
//
//    private static final int RC_SIGN_IN = 0;
//
//    private static final String SAVED_PROGRESS = "sign_in_progress";
//
//    // Client ID for a web server that will receive the auth code and exchange it for a
//    // refresh token if offline access is requested.
//    private static final String WEB_CLIENT_ID = "WEB_CLIENT_ID";
//
//    // Base URL for your token exchange server, no trailing slash.
//    private static final String SERVER_BASE_URL = "SERVER_BASE_URL";
//
//    // URL where the client should GET the scopes that the server would like granted
//    // before asking for a serverAuthCode
//    private static final String EXCHANGE_TOKEN_URL = SERVER_BASE_URL + "/exchangetoken";
//
//    // URL where the client should POST the serverAuthCode so that the server can exchange
//    // it for a refresh token,
//    private static final String SELECT_SCOPES_URL = SERVER_BASE_URL + "/selectscopes";
//
//
//    // GoogleApiClient wraps our service connection to Google Play services and
//    // provides access to the users sign in state and Google's APIs.
//    private GoogleApiClient mGoogleApiClient;
//
//    // We use mSignInProgress to track whether user has clicked sign in.
//    // mSignInProgress can be one of three values:
//    //
//    //       STATE_DEFAULT: The default state of the application before the user
//    //                      has clicked 'sign in', or after they have clicked
//    //                      'sign out'.  In this state we will not attempt to
//    //                      resolve sign in errors and so will display our
//    //                      Activity in a signed out state.
//    //       STATE_SIGN_IN: This state indicates that the user has clicked 'sign
//    //                      in', so resolve successive errors preventing sign in
//    //                      until the user has successfully authorized an account
//    //                      for our app.
//    //   STATE_IN_PROGRESS: This state indicates that we have started an intent to
//    //                      resolve an error, and so we should not start further
//    //                      intents until the current intent completes.
//    private int mSignInProgress;
//
//    // Used to store the PendingIntent most recently returned by Google Play
//    // services until the user clicks 'sign in'.
//    private PendingIntent mSignInIntent;
//
//    // Used to store the error code most recently returned by Google Play services
//    // until the user clicks 'sign in'.
//    private int mSignInError;
//
//    // Used to determine if we should ask for a server auth code when connecting the
//    // GoogleApiClient.  False by default so that this sample can be used without configuring
//    // a WEB_CLIENT_ID and SERVER_BASE_URL.
//    private boolean mRequestServerAuthCode = false;
//
//    // Used to mock the state of a server that would receive an auth code to exchange
//    // for a refresh token,  If true, the client will assume that the server has the
//    // permissions it wants and will not send an auth code on sign in.  If false,
//    // the client will request offline access on sign in and send and new auth code
//    // to the server.  True by default because this sample does not implement a server
//    // so there would be nowhere to send the code.
//    private boolean mServerHasToken = true;
//
//    private SignInButton mSignInButton;
//    private Button mSignOutButton;
//    private Button mRevokeButton;
//    private TextView mStatus;
//    private ListView mCirclesListView;
//    private ArrayAdapter<String> mCirclesAdapter;
//    private ArrayList<String> mCirclesList;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        ButterKnife.bind(this);
        if (checkOffline()) {
            startActivity(new Intent(this, TripActivity.class));
        }
    }

//    private GoogleApiClient buildGoogleApiClient() {
//        // When we build the GoogleApiClient we specify where connected and
//        // connection failed callbacks should be returned, which Google APIs our
//        // app uses and which OAuth 2.0 scopes our app requests.
//        GoogleApiClient.Builder builder = new GoogleApiClient.Builder(this)
//                .addConnectionCallbacks(this)
//                .addOnConnectionFailedListener(this)
//                .addApi(Plus.API, Plus.PlusOptions.builder().build())
//                .addScope(Plus.SCOPE_PLUS_LOGIN);
//
//        if (mRequestServerAuthCode) {
//            checkServerAuthConfiguration();
//            builder = builder.requestServerAuthCode(WEB_CLIENT_ID, this);
//        }
//
//        return builder.build();
//    }


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
        if (!singUp()) this.finish(); //TODO: What happens if you cannot sign up
        else this.finish();
    }

    private boolean checkOffline() {
        return OfflineUtilities.getUserProfile();
    }

    private boolean singUp() {
//        return CloudUtilities.signUpUsingGoogle();
        return true;
    }
}

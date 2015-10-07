package za.co.dvt.drivestats.resources.login;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People;

/**
 * Created by Nicholas on 2015-09-28.
 */
public interface LoginResource
        extends GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<People.LoadPeopleResult> {

    void authenticate(LoginCallback loginCallback);

}

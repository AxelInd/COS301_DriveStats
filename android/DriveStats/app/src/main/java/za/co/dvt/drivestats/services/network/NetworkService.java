package za.co.dvt.drivestats.services.network;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.Methods;
import za.co.dvt.drivestats.resources.network.httpcoms.HttpGetRequest;
import za.co.dvt.drivestats.resources.network.httpcoms.HttpPostRequest;
import za.co.dvt.drivestats.resources.network.httpcoms.HttpRequest;
import za.co.dvt.drivestats.resources.network.request.Email;
import za.co.dvt.drivestats.resources.network.request.TripInfo;
import za.co.dvt.drivestats.resources.network.response.TripScore;
import za.co.dvt.drivestats.resources.network.response.UserId;
import za.co.dvt.drivestats.utilities.Constants;
import za.co.dvt.drivestats.utilities.UserProfile;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class NetworkService {

    public static boolean login(final String emailAddress, final Callback<UserId> callback) {
        final HttpRequest<UserId> loginRequest = new HttpGetRequest<>(new Email(emailAddress), UserId.class, Methods.USER_LOGIN, new Callback<UserId>() {
            @Override
            public void invoke(UserId result) {
                UserProfile profile = Inject.userProfile();
                profile.setUserId(result.getUserId());
                profile.saveUserProfile();
                callback.invoke(result);
            }
        });
        loginRequest.execute();
        return true;
    }

    public static void uploadTrip(final Callback<TripScore> callback) {
        final HttpRequest<TripScore> uploadRequest = new HttpPostRequest<>(new TripInfo(), TripScore.class, Methods.ADD_TRIP, new Callback<TripScore>() {
            @Override
            public void invoke(TripScore result) {
                Inject.currentContext().deleteFile(Constants.OFFLINE_FILE_NAME);
                callback.invoke(result);
            }
        });
        uploadRequest.execute();
    }

}

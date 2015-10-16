package za.co.dvt.drivestats.services.network;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;

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
            public void invoke(UserId result) throws IOException {
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
        final TripInfo tripInfo = new TripInfo();
        final HttpRequest<TripScore> uploadRequest = new HttpPostRequest<>(tripInfo, TripScore.class, Methods.ADD_TRIP, new Callback<TripScore>() {
            @Override
            public void invoke(TripScore result) throws IOException {
                try{
                    Inject.currentContext().deleteFile(Constants.OFFLINE_FILE_NAME);
                } catch (Throwable t) {}
                final FileOutputStream stream = Inject.currentContext().openFileOutput(Constants.OFFLINE_SCORE_FILE, Context.MODE_APPEND);
                Inject.tripTracingService().saveTripScore(tripInfo.getTripDate(), tripInfo.getStartTime(), result.getAddTripResult(), stream);
                callback.invoke(result);
            }
        });
        uploadRequest.execute();
    }

}

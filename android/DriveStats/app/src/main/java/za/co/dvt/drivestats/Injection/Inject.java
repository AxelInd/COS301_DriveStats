package za.co.dvt.drivestats.Injection;

import android.content.Context;

import za.co.dvt.drivestats.services.CloudService;
import za.co.dvt.drivestats.services.SoapCloudService;
import za.co.dvt.drivestats.utilities.Settings;
import za.co.dvt.drivestats.utilities.UserProfile;

/**
 * Created by Nicholas on 2015-08-10.
 */
public class Inject {

    private static Context currentContext;

    public static Context currentContext() {
        return currentContext;
    }

    public static void setCurrentContext(Context currentContext) {
        Inject.currentContext = currentContext;
    }

    public static CloudService cloudService() {
        return new SoapCloudService();
    }

    public static Settings settings() {
        return Settings.getInstance();
    }

    public static UserProfile userProfile() {
        return UserProfile.getInstance();
    }


}

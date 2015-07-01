package za.co.dvt.drivestats.utilities;

/**
 * The representation of the user profile.
 * <p/>
 * This class is a singleton so when either the offline reader of the cloud utilities
 * updated it it will reflect to anyone that uses the user profile
 */
public class UserProfile {

    //TODO: Find out what info is returned from google and make this to fit

    private static final UserProfile instance = new UserProfile();

    private UserProfile() {
    }

    public static UserProfile getInstance() {
        return instance;
    }

}

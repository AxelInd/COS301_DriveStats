package za.co.dvt.drivestats.utilities;

/**
 * The representation of the user profile.
 * <p/>
 * This class is a singleton so when either the offline reader of the cloud utilities
 * updated it it will reflect to anyone that uses the user profile
 */
public class UserProfile {

    private Long userId;

    private String emailAddress;

    private static final UserProfile instance = new UserProfile();

    private UserProfile() {
    }

    public static UserProfile getInstance() {
        return instance;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

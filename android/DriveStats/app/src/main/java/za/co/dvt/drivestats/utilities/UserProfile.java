package za.co.dvt.drivestats.utilities;

import android.content.Context;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;

/**
 * The representation of the user profile.
 * <p/>
 * This class is a singleton so when either the offline reader of the cloud utilities
 * updated it it will reflect to anyone that uses the user profile
 */
public class UserProfile {

    private Long userId;

    private String userName;

    private String profilePicture;

    private long numberOfTrips;

    private double averageScore;

    private static final String USER_PROFILE_FILENAME = "userprofile.prf";

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getProfilePicture() {
        return profilePicture;
    }

    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    public long getNumberOfTrips() {
        return numberOfTrips;
    }

    public void setNumberOfTrips(long numberOfTrips) {
        this.numberOfTrips = numberOfTrips;
    }

    public double getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(double averageScore) {
        this.averageScore = averageScore;
    }

    public void addTrip(double score) {
        double oldAverage = averageScore * (numberOfTrips++);
        averageScore = Math.floor(((oldAverage + score) / numberOfTrips) * 10) / 10;
        saveUserProfile();
    }

    public void readUserProfile() throws IOException {
        FileInputStream stream = Inject.currentContext().openFileInput(USER_PROFILE_FILENAME);
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        setUserId(Long.parseLong(reader.readLine()));
        setUserName(reader.readLine());
        setProfilePicture(reader.readLine());
        setNumberOfTrips(Long.parseLong(reader.readLine()));
        setAverageScore(Double.parseDouble(reader.readLine()));
        reader.close();
    }

    public void saveUserProfile() {
        BufferedWriter writer = null;
        try {
            FileOutputStream fileOutputStream = Inject.currentContext().openFileOutput(USER_PROFILE_FILENAME, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(fileOutputStream));
            writer.write(Long.toString(getUserId()));
            writer.newLine();
            writer.write(getUserName());
            writer.newLine();
            writer.write(getProfilePicture());
            writer.newLine();
            writer.write(Long.toString(getNumberOfTrips()));
            writer.newLine();
            writer.write(Double.toString(getAverageScore()));
        } catch (IOException e) {
            throw new SystemException("Error saving User profile", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                throw new SystemException("Error closing user profile", e);
            }
        }
    }

}

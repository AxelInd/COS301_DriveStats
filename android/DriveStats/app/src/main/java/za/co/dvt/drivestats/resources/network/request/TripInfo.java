package za.co.dvt.drivestats.resources.network.request;

import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.Date;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.utilities.Constants;
import za.co.dvt.drivestats.utilities.OfflineUtilities;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;

/**
 * Created by Nicholas on 2015-09-10.
 */
public class TripInfo implements Request {

    private Long userID;

    private String tripDate;

    private String startTime;

    private String data;

    private static final String TIME_PATTERN = "HH:mm";

    private static final String DATE_PATTERN = "dd/MM/yy";

    public TripInfo() {
        this.userID = Inject.userProfile().getUserId();
        String fileName = Constants.OFFLINE_FILE_NAME;
        try {
            this. data = OfflineUtilities.getUncomplessedOfflineFile(Inject.currentContext().openFileInput(fileName));
        } catch (FileNotFoundException e) {
            throw new SystemException("Could not read file", e);
        }
        long timeStamp = Long.parseLong(fileName.split("-")[1].replace(".dat", ""));
        this.startTime = timeStampToString(TIME_PATTERN, timeStamp);
        this.tripDate = timeStampToString(DATE_PATTERN, timeStamp);
    }

    private String timeStampToString(String pattern, long timeStamp) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        return format.format(new Date(timeStamp));
    }

    public Long getUserID() {
        return userID;
    }

    public String getTripDate() {
        return tripDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getData() {
        return data;
    }

    @Override
    public String getParameters() {
        StringBuilder builder = new StringBuilder("{");
        builder.append("userID : " + userID);
        builder.append(",startTime : " + startTime);
        builder.append(",tripDate : " + tripDate);
        builder.append(",data : " + data);
        return builder.toString() + "}";
    }
}

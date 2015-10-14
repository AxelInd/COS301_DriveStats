package za.co.dvt.drivestats.services;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by Nicholas on 2015-10-14.
 */
public class TripTracingService {

    public void saveTripScore(String date, String time, String score, FileOutputStream writer) throws IOException {
        String line = date + "," + time + "," + score + "\n";
        writer.write(line.getBytes());
    }

    public List<Trip> getTripList(int numberResults, FileOutputStream stream) {
        return null;
    }

    public class Trip {
        public String date;
        public String time;
        public String score;

        public Trip(String date, String time, String score) {
            this.date = date;
            this.time = time;
            this.score = score;
        }
    }

}

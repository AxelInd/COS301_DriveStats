package za.co.dvt.drivestats.services;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.List;

import za.co.dvt.drivestats.Injection.Inject;

/**
 * Created by Nicholas on 2015-10-14.
 */
public class TripTracingService {

    public void saveTripScore(String date, String time, String score, FileOutputStream writer) throws IOException {
        String line = date + "," + time + "," + score + "\n";
        writer.write(line.getBytes());
        Inject.userProfile().addTrip(Double.parseDouble(score.replace(",", ".")));
    }

    public void getTripList(List<String> scores, FileInputStream stream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        for (String line = reader.readLine(); line != null; line = reader.readLine()) {
            String[] parts = line.split(",");
            scores.add(parts[0] + " at " + parts[1] + " : " + parts[2]);
        }
        Collections.reverse(scores);
        reader.close();
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

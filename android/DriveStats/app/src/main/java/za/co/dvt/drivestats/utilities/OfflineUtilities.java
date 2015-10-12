package za.co.dvt.drivestats.utilities;

import android.util.Log;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;

public class OfflineUtilities {

    /**
     * Takes in the file stream to the offline file storage that is going to be written to and
     * the latest trip data that is going to be written to the offline storage file.
     * <p/>
     * The new trip data will then be appended to the rest of the trip data.
     *
     * @param writer The output stream of the offline file for storage. Get this from {@link android.content.Context#openFileOutput}
     * @param line   The new trip data as a String
     */
    public synchronized static void writeToOfflineStorage(FileOutputStream writer, String line) {
        try {
            line += "\n";
            writer.write(line.getBytes());
        } catch (IOException e) {
            //TODO: Handle IO exception
            Log.d("ERROR", "IO Exception in OfflineUtilities#writeToOfflineStorage: " + e.getMessage());
        }
    }


    public synchronized static String getUncomplessedOfflineFile(FileInputStream file) {
        try {
            String dataString = new String(fileToArr(file));
            Log.d(">>>>>>>>>> ", dataString);
            return dataString;
        } catch (IOException e) {
            throw new SystemException("Problem reading file", e);
        }
    }

    /**
     * Helper method
     * <p/>
     * Used by {@link OfflineUtilities#getUncomplessedOfflineFile(FileInputStream)}
     * Takes in the input stream for the offline storage and reads the contents into a byte array
     *
     * @param file The input stream for the offline storage {@see android.content.Context#openFileInput(String)}
     * @return The byte array representation of the offline file
     * @throws IOException If  something goes wrong with the file reading
     */
    private static byte[] fileToArr(FileInputStream file) throws IOException {
        List<Byte> tripFile = new ArrayList<>();
        for (byte b; (b = (byte) file.read()) != -1; ) {
            tripFile.add(b);
        }
        byte[] tripBytes = new byte[tripFile.size()];
        int pos = 0;
        for (Byte b : tripFile) {
            tripBytes[pos++] = b.byteValue();
        }
        return tripBytes;
    }

    //==============================================

    public static boolean getUserProfile() {
        try {
            Inject.userProfile().readUserProfile();
            return true;
        } catch (IOException e) {
            return false;
        }
    }

}

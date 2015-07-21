package za.co.dvt.drivestats.utilities;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Properties;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class OfflineUtilities {


    public synchronized static void writeToOfflineStorage(FileOutputStream writer, String line) {

        try {
            line += "\n";
            writer.write(line.getBytes());
        } catch (IOException e) {
            //TODO: Handle IO exception
            Log.d("ERROR", "IO Exception " + e.getMessage());
        }
    }

    public synchronized static List<String> readFromOfflineStorage(FileInputStream file) {
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new FileReader(file));
//            String line;
//            List<String> stateList = new ArrayList<>();
//            while((line = reader.readLine()) != null) {
//                stateList.add(line);
//            }
//            return stateList;
//        } catch (IOException e) {
//            //TODO: Handle exception
//            e.printStackTrace();
//        } finally {
//            if (reader != null) try {
//                reader.close();
//            } catch (IOException e) {
//                //TODO: Handle exception
//                e.printStackTrace();
//            }
//        }
        //TODO: read byte array into string array and see how that works
        return null;
    }

    public static void readSettings() {
        InputStream stream = null;
        try {
             stream = new FileInputStream(new File(Constants.SETTINGS_FILE_PATH));
            Properties properties = new Properties();
            properties.load(stream);
            Settings.getInstance().initSettings(properties);
        } catch (IOException e) {
            //TODO: handle the IOException
            e.printStackTrace();
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                //TODO: Handle this IOException
                e.printStackTrace();
            }
        }
    }

    public static void writeSettings() {
        OutputStream stream = null;
        try {
            Properties properties = Settings.getInstance().toProperty();
            stream = new FileOutputStream(new File(Constants.SETTINGS_FILE_PATH));
            properties.store(stream, "");
        } catch (IOException e) {
                // TODO: handle IOException
            e.printStackTrace();
        } finally {
            if (stream != null) try {
                stream.close();
            } catch (IOException e) {
                // TODO: handle IOException
                e.printStackTrace();
            }
        }
    }

    public static boolean getUserProfile() {
        //TODO: update the user profile and return true or return false if there is no offline user profile
        return false;
    }

}

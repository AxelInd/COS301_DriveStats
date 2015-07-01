package za.co.dvt.drivestats.utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Properties;

import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class OfflineUtilities {

    public static void readSettings() {
        InputStream stream = null;
        try {
             stream = new FileInputStream(new File(Constants.settingsFilePath));
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
            stream = new FileOutputStream(new File(Constants.settingsFilePath));
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
        return true;
    }

    public static void writeToFile(SensorState sensorState) {
        //TODO: Write the sate to the file system using the path on the constants class
    }

    public static String readFile() {
        //TODO: Read the file contents and return the string value of the contents
        return "";
    }

}

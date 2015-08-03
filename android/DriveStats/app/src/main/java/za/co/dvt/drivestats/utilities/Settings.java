package za.co.dvt.drivestats.utilities;

import android.content.Context;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import za.co.dvt.drivestats.threadmanagment.ThreadManager;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class Settings {

    public static final String SETTINGS_PROPERTIES_FILE_NAME = "settings.properties";

    private static final Settings instance = new Settings();

    private static final String WIFI_ONLY_MODE = "wifiOnlyMode";

    private boolean wifiOnlyMode = false;

    private Settings() {}

    public boolean isWifiOnlyMode() {
        return wifiOnlyMode;
    }

    public void setWifiOnlyMode(boolean wifiOnlyMode) {
        this.wifiOnlyMode = wifiOnlyMode;
        if (wifiOnlyMode) ThreadManager.getInstance().runUploadThread();
        else ThreadManager.getInstance().stopUploadThread();
    }

    public static Settings getInstance() {
        return instance;
    }

    public void loadSettings(Context context) {
        try {
            InputStream reader = context.openFileInput(Settings.SETTINGS_PROPERTIES_FILE_NAME);
            Properties properties = new Properties();
            properties.load(reader);
            Settings.getInstance().initSettings(properties);
        } catch (IOException e) {
            //TODO: Handle IOException
            e.printStackTrace();
        }
    }

    public void saveSettings(Context context) {
        try {
            FileOutputStream writer = context.openFileOutput(Settings.SETTINGS_PROPERTIES_FILE_NAME, context.MODE_PRIVATE);
            Settings.getInstance().toProperty().store(writer, "Save settings");
        } catch (IOException e) {
            //TODO: Handle IOException
            e.printStackTrace();
        }
    }


    private Properties toProperty() {
        Properties properties = new Properties();
        properties.setProperty(WIFI_ONLY_MODE, Boolean.toString(wifiOnlyMode));
        return properties;
    }

    private void initSettings(Properties properties) {
        wifiOnlyMode = Boolean.valueOf(properties.getProperty(WIFI_ONLY_MODE));
    }
}

package za.co.dvt.drivestats.utilities;

import java.util.Properties;

import za.co.dvt.drivestats.threadmanagment.ThreadManager;

/**
 * Created by Nicholas on 2015-07-01.
 */
public class Settings {

    private static final Settings instance = new Settings();

    private static final String WIFI_ONLY_MODE = "wifiOnlyMode";

    private boolean wifiOnlyMode = false;

    private Settings() {
        OfflineUtilities.readSettings();
    }

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

    public Properties toProperty() {
        Properties properties = new Properties();
        properties.setProperty(WIFI_ONLY_MODE, wifiOnlyMode ? "T" : "F");
        return properties;
    }

    public void initSettings(Properties properties) {
        wifiOnlyMode = properties.getProperty(WIFI_ONLY_MODE, "T").equals("T");
    }
}

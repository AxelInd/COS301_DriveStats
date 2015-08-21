package za.co.dvt.drivestats;

import junit.framework.Assert;

import org.junit.Test;

import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.utilities.Settings;
import za.co.dvt.drivestats.utilities.exceptions.SystemException;

/**
 * Created by Nicholas on 2015-08-20.
 */
public class SettingsTest {

    @Test
    public void testThatOnlyOneInstanceOfSettingsIsReturnedUsingGetInstance() {
        Settings settingsOne = Settings.getInstance();
        Settings settingsTwo = Settings.getInstance();
        Assert.failNotEquals("Settings objects injected are not the same.", settingsOne, settingsTwo);
    }

    @Test
    public void testThatOnlyOneInstanceOfSettingsIsReturnedUsingInject() {
        Settings settingsOne = Inject.settings();
        Settings settingsTwo = Inject.settings();
        Assert.failNotEquals("Settings objects injected are not the same.", settingsOne, settingsTwo);
    }


    @Test
    public boolean testLoadPropertiesReadsPropertiesFileAndInstantiatesSettingsVariable() {
        try {
            Settings settings = Settings.getInstance();
            settings.loadSettings();
            return true;
        } catch (SystemException e) {
            Assert.fail("Failed to read in the system settings");
            return false;
        }
    }

    private Settings settings = Inject.settings();

    @Test
    public void testSaveFileToTheFileSystem() {
        try {
            settings.saveSettings();
        } catch (SystemException e) {
            Assert.fail("Failed to save settings to the file system");
        }
    }

    @Test
    public void testSettingWifiOnlyMode() {
        boolean current = settings.isWifiOnlyMode();
        settings.setWifiOnlyMode(!current);
        Assert.failNotEquals("Wifi only mode should be " + !current, !settings.isWifiOnlyMode(), current);
    }

    @Test
    public void testSavingChangesToSettingsSavesTheChanges() {
        boolean current = settings.isWifiOnlyMode();
        settings.setWifiOnlyMode(!current);
        settings.saveSettings();
        settings.loadSettings();
        Assert.failNotEquals("Saving settings not working", settings.isWifiOnlyMode(), !current);
        settings.setWifiOnlyMode(current);
        settings.saveSettings();
    }

}

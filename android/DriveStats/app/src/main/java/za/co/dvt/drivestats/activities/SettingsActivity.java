package za.co.dvt.drivestats.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.utilities.Settings;


public class SettingsActivity extends Activity {

    private static final Settings SETTINGS = Settings.getInstance();

    @Bind(R.id.chkWifiOnly)
    CheckBox wifiOnlyCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);
        initUi();
    }

    private void initUi() {
        SETTINGS.loadSettings();
        wifiOnlyCheckBox.setChecked(SETTINGS.isWifiOnlyMode());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        saveSettings();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        saveSettings();
        super.onDestroy();
    }

    public void saveSettings() {
        SETTINGS.saveSettings();
    }

    @OnClick(R.id.chkWifiOnly)
    public void changeWifiOnlyMode(View view) {
        SETTINGS.setWifiOnlyMode(((CheckBox) view).isChecked());
    }
}

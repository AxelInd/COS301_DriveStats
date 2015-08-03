package za.co.dvt.drivestats.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.utilities.Settings;


public class SettingsActivity extends AppCompatActivity {

    private static final Settings SETTINGS = Settings.getInstance();

    @Bind(R.id.chkWifiOnly)
    CheckBox wifiOnlyCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        initUi();
    }

    private void initUi() {
        SETTINGS.loadSettings(getApplicationContext());
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
        SETTINGS.saveSettings(getApplicationContext());
    }

    @OnClick(R.id.chkWifiOnly)
    public void changeWifiOnlyMode(View view) {
        SETTINGS.setWifiOnlyMode(((CheckBox) view).isChecked());
    }
}

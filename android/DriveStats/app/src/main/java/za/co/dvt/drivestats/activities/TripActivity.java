package za.co.dvt.drivestats.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.threadmanagment.ThreadManager;
import za.co.dvt.drivestats.threadmanagment.ThreadState;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;
import za.co.dvt.drivestats.utilities.OfflineUtilities;
import za.co.dvt.drivestats.utilities.Settings;


public class TripActivity extends AppCompatActivity {

    private ThreadManager manager = ThreadManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        ButterKnife.bind(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_trip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) return true;
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.toggleTrip)
    public void toggleTrip(View view) {
        //TODO: confirm this is working
        if (((ToggleButton) view).isChecked()) manager.start(getApplicationContext());
        else manager.stop();
    }

    @Bind(R.id.xMotion)
    TextView xMotionText;
    @Bind(R.id.yMotion)
    TextView yMotionText;
    @Bind(R.id.zMotion)
    TextView zMotionText;
    @Bind(R.id.location)
    TextView locationText;
    @Bind(R.id.speed)
    TextView speedText;
    @Bind(R.id.isRunning)
    TextView runningText;

    private SensorState state = SensorState.getInstance();

    @OnClick(R.id.refresh)
    public void refresh() {
        runningText.setText(ThreadState.isRunning() ? "Running" : "Not Running");
        if (ThreadState.isRunning()) {
            xMotionText.setText(Float.toString(state.getMaxXDeflection()));
            yMotionText.setText(Float.toString(state.getMaxYDeflection()));
            zMotionText.setText(Float.toString(state.getMaxZDeflection()));
            speedText.setText(Double.toString(state.getSpeed()));
            double[] location = state.getLocation();
            if (location != null && location.length <= 2) {
                locationText.setText(Double.toString(location[0]) + ", " + Double.toString(location[1]));
            }
        } else {
            xMotionText.setText("");
            yMotionText.setText("");
            zMotionText.setText("");
            speedText.setText("");
            locationText.setText("");
        }
    }

    @OnClick(R.id.settings)
    public void settingsClick() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onStop() {
        //TODO: Confirm this happens when it has to
        super.onStop();
        Log.d("Testing", "Stopping Trip Activity");
        manager.stop();
        OfflineUtilities.writeSettings();
    }
}

package za.co.dvt.drivestats.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.apache.http.Header;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.threadmanagment.ThreadManager;
import za.co.dvt.drivestats.threadmanagment.ThreadState;
import za.co.dvt.drivestats.threadmanagment.sensorthread.SensorState;
import za.co.dvt.drivestats.threadmanagment.uploadingthread.UploadUtility;
import za.co.dvt.drivestats.utilities.CloudRequest;
import za.co.dvt.drivestats.utilities.Constants;

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
        if (((ToggleButton) view).isChecked() && checkGpsService()) {
            Constants.OFFLINE_FILE_NAME = "offlineStorage-" + System.currentTimeMillis() + ".dat";
            manager.start(getApplicationContext());
        } else {
            try {
                if (((ToggleButton) view).isChecked()) {
                    ((ToggleButton) view).setChecked(false);
                }
                if (ThreadState.isRunning()) {
                    manager.stop();
                    UploadUtility.uploadTrip(getApplicationContext(), createHandler());
                }
            } catch (IOException e) {
                //TODO: handle IOException
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.testConnection)
    public void testConnection() {
        CloudRequest request = new CloudRequest(CloudRequest.Action.HELLO_WORLD);
        request.post(createHandler());
    }

    private ResponseHandlerInterface createHandler() {
        return new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Toast.makeText(getApplicationContext(), "Trip uploaded to the server.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                for (Header header : headers) {
                    Log.d("Testing", header.getName() + " : " + header.getValue());
                }
                Log.d("Testing", new String(responseBody));
                Toast.makeText(getApplicationContext(), "Trip not uploaded to the server. Returned with code: " + statusCode, Toast.LENGTH_LONG).show();
            }
        };
    }

    // This is because for some reason any alert message and activity change can only take place in another activity
    private boolean checkGpsService() {
        LocationManager locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            askNicely();
            return false;
        }
        return true;
    }

    private void askNicely() {
        String title = "Location";
        String message = "This application required that your location service is on.\n" +
                "Go to settings";
        AlertDialog.Builder builder = new AlertDialog.Builder(TripActivity.this);
        builder.setTitle(title);
        builder.setMessage(message);

        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                getApplicationContext().startActivity(intent);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
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
    public void refreshClick(View view) {
        runner();
    }

    private void runner() {
        runningText.setText(ThreadState.isRunning() ? "Running" : "Not Running");
        if (ThreadState.isRunning()) {
            xMotionText.setText(Float.toString(state.getCorrectedMaxXDeflection()));
            yMotionText.setText(Float.toString(state.getCorrectedMaxYDeflection()));
            zMotionText.setText(Float.toString(state.getCorrectedMaxZDeflection()));
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
        za.co.dvt.drivestats.utilities.Settings.getInstance().saveSettings(getApplicationContext());
    }
}

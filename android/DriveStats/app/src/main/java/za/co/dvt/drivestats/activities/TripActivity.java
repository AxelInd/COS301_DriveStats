package za.co.dvt.drivestats.activities;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ToggleButton;

import butterknife.ButterKnife;
import butterknife.OnClick;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.resources.network.Callback;
import za.co.dvt.drivestats.resources.network.response.TripScore;
import za.co.dvt.drivestats.services.network.NetworkService;
import za.co.dvt.drivestats.threadmanagment.ThreadManager;
import za.co.dvt.drivestats.threadmanagment.ThreadState;
import za.co.dvt.drivestats.utilities.Constants;

public class TripActivity extends AppCompatActivity {

    private ThreadManager manager = ThreadManager.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);
        checkMustStop();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        checkMustStop();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        checkMustStop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkMustStop();
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

    @Override
    protected void onStop() {
        //TODO: Confirm this happens when it has to
        super.onStop();
        za.co.dvt.drivestats.utilities.Settings.getInstance().saveSettings();
    }

    @OnClick(R.id.toggleTrip)
    public void toggleTrip(View view) {
        if (((ToggleButton) view).isChecked() && checkGpsService()) {
            start();
        } else {
            if (((ToggleButton) view).isChecked()) {
                ((ToggleButton) view).setChecked(false);
            }
            if (ThreadState.isRunning()) {
                stop();
            }
        }
    }

    @OnClick(R.id.settings)
    public void settingsClick() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    private void start() {
        Constants.OFFLINE_FILE_NAME = "offlineStorage-" + System.currentTimeMillis() + ".dat";
        manager.start();
        Notification notification = new Notification.Builder(Inject.currentContext())
                .setContentTitle("Drive Stats is running")
                .setContentText("Your trip is being recorded.")
                .setSmallIcon(Inject.currentContext().getApplicationInfo().icon)
                .setContentIntent(getPendingIntent(false))
                .addAction(17301551, "Stop recording", getPendingIntent(true))
                .setOngoing(true)
                .build();
        ((NotificationManager) getSystemService(Inject.currentContext().NOTIFICATION_SERVICE)).notify(0, notification);
    }

    private PendingIntent getPendingIntent(boolean stop) {
        Intent launchIntent = new Intent(Inject.currentContext(), SignInActivity.class);
        launchIntent.putExtra("stop", stop);
        return PendingIntent.getActivity(
                Inject.currentContext(),
                (int) System.currentTimeMillis(),
                launchIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void stop() {
        manager.stop();
        NetworkService.uploadTrip(new Callback<TripScore>() {
            @Override
            public void invoke(TripScore result) {
                Intent intent = new Intent(Inject.currentContext(), SignInActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(
                        Inject.currentContext(),
                        (int) System.currentTimeMillis(),
                        intent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                Notification notification = new Notification.Builder(Inject.currentContext())
                        .setContentTitle("Trip score: " + result.getAddTripResult())
                        .setContentText("Your trip was successfully uploaded")
                        .setSmallIcon(Inject.currentContext().getApplicationInfo().icon)
                        .setContentIntent(pendingIntent)
                        .build();
                ((NotificationManager) getSystemService(Inject.currentContext().NOTIFICATION_SERVICE)).notify(0, notification);
            }
        });
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


    private void checkMustStop() {
        if (getIntent().getBooleanExtra("stop", false)) {
            stop();
        }
    }
}

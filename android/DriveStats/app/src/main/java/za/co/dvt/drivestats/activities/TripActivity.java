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
            manager.start();
        } else {
            if (((ToggleButton) view).isChecked()) {
                ((ToggleButton) view).setChecked(false);
            }
            if (ThreadState.isRunning()) {
                manager.stop();
                NetworkService.uploadTrip(new Callback<TripScore>() {
                    @Override
                    public void invoke(TripScore result) {
                        PendingIntent intent = PendingIntent.getActivity(
                                Inject.currentContext(),
                                (int) System.currentTimeMillis(),
                                new Intent(Inject.currentContext(), SignInActivity.class),
                                0);

                        Notification notification = new Notification.Builder(Inject.currentContext())
                                .setContentTitle("Trip score: " + result.getAddTripResult())
                                .setContentText("Your trip was successfully uploaded")
                                .setSmallIcon(Inject.currentContext().getApplicationInfo().icon)
                                .setContentIntent(intent)
                                .setAutoCancel(true)
                                .build();
                        ((NotificationManager) getSystemService(Inject.currentContext().NOTIFICATION_SERVICE)).notify(0, notification);
                    }
                });
            }
        }
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

    @OnClick(R.id.settings)
    public void settingsClick() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    protected void onStop() {
        //TODO: Confirm this happens when it has to
        super.onStop();
        za.co.dvt.drivestats.utilities.Settings.getInstance().saveSettings();
    }
}

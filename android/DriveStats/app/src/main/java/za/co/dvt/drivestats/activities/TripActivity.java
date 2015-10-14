package za.co.dvt.drivestats.activities;

import android.app.Activity;
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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;

import butterknife.Bind;
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
import za.co.dvt.drivestats.utilities.sensormontiors.OfflineWriter;

public class TripActivity extends Activity {

    private ThreadManager manager = ThreadManager.getInstance();

    private static final String EXTRA_VALUE = "mustStop";

    private static boolean running = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trip);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);
        checkMustStop();
        Log.d("onCreate", getIntent().getStringExtra(EXTRA_VALUE) + "");
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
    protected void onStart() {
        super.onStart();
        tripButton.setImageResource(running ? R.drawable.stop_trip : R.drawable.start_trip);
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
        if (checkGpsService()) {
            if (!running) {
                start();
                running = true;
                mainCoinFlip(view, running);
            } else {
                if (ThreadState.isRunning()) {
                    mainCoinFlip(view, !running);
                    stop();
                    running = false;
                }
            }
        }
    }

    @OnClick(R.id.settings)
    public void settingsClick(View view) {
        final Intent intent = new Intent(this, SettingsActivity.class);
        coinFlip(view, new afterFlip() {
            @Override
            public void perform() {
                startActivity(intent);
            }
        });
    }

    @OnClick(R.id.profile)
    public void profileClick(View view) {
        final Intent intent = new Intent(this, ProfileActivity.class);
        coinFlip(view, new afterFlip() {
            @Override
            public void perform() {
                startActivity(intent);
            }
        });
    }

    @Bind(R.id.toggleTrip)
    ImageButton tripButton;

    private void mainCoinFlip(final View view, final boolean running) {
        final Animation flipBack = AnimationUtils.loadAnimation(this, R.anim.anim_flip_to_back);
        final Animation back = AnimationUtils.loadAnimation(this, R.anim.anim_back);
        final Animation flipFront = AnimationUtils.loadAnimation(this, R.anim.anim_flip_to_front);
        final Animation front = AnimationUtils.loadAnimation(this, R.anim.anim_front);
        final Animation in = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        final Animation out = AnimationUtils.loadAnimation(this, R.anim.fade_out);

        flipBack.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(back);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        back.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(flipFront);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flipFront.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(front);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        front.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(out);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        out.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                tripButton.setImageResource(running ? R.drawable.stop_trip : R.drawable.start_trip);
                view.startAnimation(in);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(flipBack);
    }

    private void coinFlip(final View view, final afterFlip callback) {
        final Animation flipBack = AnimationUtils.loadAnimation(this, R.anim.anim_flip_to_back);
        final Animation back = AnimationUtils.loadAnimation(this, R.anim.anim_back);
        final Animation flipFront = AnimationUtils.loadAnimation(this, R.anim.anim_flip_to_front);
        final Animation front = AnimationUtils.loadAnimation(this, R.anim.anim_front);

        flipBack.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(back);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        back.setAnimationListener(new Animation.AnimationListener() {
            int count = 0;

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(flipFront);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        flipFront.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.startAnimation(front);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        front.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                callback.perform();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(flipBack);
    }

    private void start() {
        Constants.OFFLINE_FILE_NAME = "offlineStorage-" + System.currentTimeMillis() + ".dat";
        manager.start();
        Notification notification = new Notification.Builder(Inject.currentContext())
                .setContentTitle("Drive Stats is running")
                .setContentText("Your trip is being recorded.")
                .setSmallIcon(Inject.currentContext().getApplicationInfo().icon)
                .setContentIntent(getPendingIntent(false))
                .addAction(17301552, "Stop recording", getPendingIntent(true))
                .setOngoing(true)
                .build();
        ((NotificationManager) getSystemService(Inject.currentContext().NOTIFICATION_SERVICE)).notify(0, notification);
    }

    private PendingIntent getPendingIntent(boolean stop) {
        Intent launchIntent = new Intent(Inject.currentContext(), SignInActivity.class);
        launchIntent.setAction(Intent.ACTION_SEND);
        launchIntent.putExtra(EXTRA_VALUE, "stop");
        return PendingIntent.getActivity(
                Inject.currentContext(),
                (int) System.currentTimeMillis(),
                launchIntent,
                PendingIntent.FLAG_CANCEL_CURRENT);
    }

    private void stop() {
        manager.stop(new OfflineWriter.WritingStop() {
            @Override
            public void onStopWriting() {
                NetworkService.uploadTrip(new Callback<TripScore>() {
                    @Override
                    public void invoke(TripScore result) {
                        Intent intent = new Intent(Inject.currentContext(), SignInActivity.class);
                        PendingIntent pendingIntent = PendingIntent.getActivity(
                                Inject.currentContext(),
                                12026442,
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
        Log.d(">>> Stopping: ", getIntent().getStringExtra(EXTRA_VALUE) + "");
        if (getIntent().getBooleanExtra("stop", false) && running) {
            if (ThreadState.isRunning()) {
                mainCoinFlip(tripButton, !running);
                stop();
                running = false;
            }
        }
    }

    private interface afterFlip {
        void perform();
    }
}

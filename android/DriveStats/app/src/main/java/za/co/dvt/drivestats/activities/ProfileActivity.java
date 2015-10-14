package za.co.dvt.drivestats.activities;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import butterknife.Bind;
import butterknife.ButterKnife;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.utilities.UserProfile;

public class ProfileActivity extends Activity {


    @Bind(R.id.userid)
    TextView userid;

    @Bind(R.id.username)
    TextView username;

    @Bind(R.id.averageScore)
    TextView averageScore;

    @Bind(R.id.profilePicture)
    ImageView profilePicture;

    @Bind(R.id.numberTrips)
    TextView numberTrips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);

        final UserProfile profile = Inject.userProfile();

        try {
            profilePicture.setImageBitmap(getBitmapThreaded(profile.getProfilePicture() + "0"));
        } catch (ExecutionException | InterruptedException e) {
            profilePicture.setImageResource(R.drawable.account);
        }


        username.setText(profile.getUserName());
        userid.setText("User ID: " + profile.getUserId());
        averageScore.setText(Double.toString(profile.getAverageScore()));
        numberTrips.setText(Long.toString(profile.getNumberOfTrips()));
    }

    private static Bitmap getBitmapThreaded(final String url) throws ExecutionException, InterruptedException {
        return Executors.newSingleThreadExecutor().submit(new Callable<Bitmap>() {
            @Override
            public Bitmap call() throws Exception {
                return getBitmap(url);
            }
        }).get();
    }

    private static Bitmap getBitmap(String url) throws IOException {
        InputStream stream = openStream(url);
        Bitmap bitmap = BitmapFactory.decodeStream(stream, null, null);
        stream.close();
        return bitmap;
    }

    private static InputStream openStream(String url) throws IOException {
        InputStream stream = null;
        URLConnection connection = new URL(url).openConnection();
        HttpURLConnection httpURLConnection = (HttpURLConnection) connection;
        httpURLConnection.setRequestMethod("GET");
        httpURLConnection.connect();

        if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
            stream = httpURLConnection.getInputStream();
        }
        return stream;
    }


}

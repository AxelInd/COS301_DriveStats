package za.co.dvt.drivestats.activities;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import za.co.dvt.drivestats.Injection.Inject;
import za.co.dvt.drivestats.R;
import za.co.dvt.drivestats.utilities.Constants;

public class Summary extends ListActivity {

    private ArrayAdapter<String> arrayAdapter;

    private List<String> scores = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        ButterKnife.bind(this);
        Inject.setCurrentContext(this);

        try {
            Inject.tripTracingService().getTripList(scores, this.openFileInput(Constants.OFFLINE_SCORE_FILE));
            arrayAdapter = new ArrayAdapter<>(this, R.layout.list_items, R.id.list_content, scores);
            setListAdapter(arrayAdapter);
        } catch (IOException e) {
            Toast.makeText(this, "Could not retrieve scores", Toast.LENGTH_LONG).show();
        }
    }

}

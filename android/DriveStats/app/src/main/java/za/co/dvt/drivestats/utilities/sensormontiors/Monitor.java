package za.co.dvt.drivestats.utilities.sensormontiors;

import android.hardware.SensorEventListener;

/**
 * Created by Nicholas on 2015-07-01.
 */
public interface Monitor extends SensorEventListener {

    void stop();

}

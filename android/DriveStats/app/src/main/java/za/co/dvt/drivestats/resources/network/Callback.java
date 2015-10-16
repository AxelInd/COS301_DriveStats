package za.co.dvt.drivestats.resources.network;

import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by Nicholas on 2015-09-10.
 */
public interface Callback<T> {

    void invoke(T result) throws IOException;

}

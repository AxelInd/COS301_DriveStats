package za.co.dvt.drivestats.threadmanagment.uploadingthread;

import android.content.Context;

import com.loopj.android.http.ResponseHandlerInterface;

import java.io.IOException;

import za.co.dvt.drivestats.utilities.CloudRequest;
import za.co.dvt.drivestats.utilities.Constants;
import za.co.dvt.drivestats.utilities.OfflineUtilities;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class UploadUtility {

    public static void uploadTrip(Context context, ResponseHandlerInterface callbacks)
            throws IOException {
        String data = OfflineUtilities.getCompressedOfflineFile(context.openFileInput(Constants.OFFLINE_FILE_NAME));
        callbacks.sendSuccessMessage(0, null, null);
//        upload(data, callbacks);
    }

    private static void upload(String data, ResponseHandlerInterface callbacks) {
        CloudRequest request = new CloudRequest(CloudRequest.Action.UPLOAD_DATA_TO_SERVER);
        request.addParameter(CloudRequest.Parameter.DATA, data);
        request.post(callbacks);
    }


}

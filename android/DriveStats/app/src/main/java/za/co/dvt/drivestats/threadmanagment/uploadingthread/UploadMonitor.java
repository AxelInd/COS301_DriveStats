package za.co.dvt.drivestats.threadmanagment.uploadingthread;

import java.io.IOException;

import za.co.dvt.drivestats.utilities.Compressor;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class UploadMonitor {

    public void read() {
        //TODO: Read from the offline file, compress the data and send it to the server
        compress("");
        uplaod();
    }

    private String compress(String fileContents) {
        try {
            return Compressor.compress(fileContents.getBytes());
        } catch (IOException e) {
            //TODO: Handle IOException
            e.printStackTrace();
            return "";
        }
    }

    private void uplaod() {
        //TODO: Upload the compressed data to the server
    }


}

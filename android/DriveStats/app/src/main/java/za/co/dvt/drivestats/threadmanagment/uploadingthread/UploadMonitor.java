package za.co.dvt.drivestats.threadmanagment.uploadingthread;

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
        return Compressor.compress(fileContents);
    }

    private void uplaod() {
        //TODO: Upload the compressed data to the server
    }


}

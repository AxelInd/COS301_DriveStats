package za.co.dvt.drivestats.utilities;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;

/**
 * Created by Nicholas on 2015-06-29.
 */
public class Compressor {

    public static String compress(byte[] tripBytes) throws IOException {
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION);
        deflater.setInput(tripBytes);
        deflater.finish();
        ByteArrayOutputStream byteArrayOutputStream = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream(tripBytes.length);
            byte[] buffer = new byte[8192];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                byteArrayOutputStream.write(buffer, 0, count);
            }
            byte[] compressedTrip = byteArrayOutputStream.toByteArray();
            return new String(compressedTrip);
        } finally {
            if (byteArrayOutputStream != null)
                byteArrayOutputStream.close();
        }
    }

}


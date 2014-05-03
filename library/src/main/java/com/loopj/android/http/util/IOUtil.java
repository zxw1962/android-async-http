package com.loopj.android.http.util;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class IOUtil {

    public static final String LOG_TAG = "IOUtil";

    /**
     * A utility function to close an input stream without raising an exception.
     *
     * @param is input stream to close safely
     */
    public static void silentCloseInputStream(InputStream is) {
        try {
            if (is != null) {
                is.close();
            }
        } catch (IOException e) {
            Log.w(LOG_TAG, "Cannot close input stream", e);
        }
    }

    /**
     * A utility function to close an output stream without raising an exception.
     *
     * @param os output stream to close safely
     */
    public static void silentCloseOutputStream(OutputStream os) {
        try {
            if (os != null) {
                os.close();
            }
        } catch (IOException e) {
            Log.w(LOG_TAG, "Cannot close output stream", e);
        }
    }

}

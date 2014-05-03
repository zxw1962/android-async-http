package com.loopj.android.http.util;

import com.loopj.android.http.interfaces.IRequestParams;

import java.io.InputStream;

public class StreamWrapper {
    public final InputStream inputStream;
    public final String name;
    public final String contentType;
    public final boolean autoClose;

    public StreamWrapper(InputStream inputStream, String name, String contentType, boolean autoClose) {
        this.inputStream = inputStream;
        this.name = name;
        this.contentType = contentType;
        this.autoClose = autoClose;
    }

    public static StreamWrapper newInstance(InputStream inputStream, String name, String contentType, boolean autoClose) {
        return new StreamWrapper(
                inputStream,
                name,
                contentType == null ? IRequestParams.APPLICATION_OCTET_STREAM : contentType,
                autoClose);
    }
}
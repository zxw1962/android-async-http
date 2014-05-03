package com.loopj.android.http.util;

import java.io.File;

public class FileWrapper {
    public final File file;
    public final String contentType;

    public FileWrapper(File file, String contentType) {
        this.file = file;
        this.contentType = contentType;
    }
}
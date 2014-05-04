package com.loopj.android.http.sample;

import org.jetbrains.annotations.NotNull;

public class GzipSample extends JsonSample {

    @Override
    public int getSampleTitle() {
        return R.string.title_gzip_sample;
    }

    @NotNull
    @Override
    public String getDefaultURL() {
        return "http://httpbin.org/gzip";
    }
}

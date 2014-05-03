package com.loopj.android.http.interfaces;

import org.apache.http.HttpEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface IRequestParams {

    public final static String APPLICATION_OCTET_STREAM =
            "application/octet-stream";

    IRequestParams setContentEncoding(@NotNull final String encoding);

    IRequestParams put(@NotNull final String key, @NotNull final String value);

    IRequestParams put(@NotNull final String key, @NotNull final File value) throws FileNotFoundException;

    IRequestParams put(@NotNull final String key, @NotNull final File value, @NotNull final String contentType) throws FileNotFoundException;

    IRequestParams put(@NotNull final String key, @NotNull final InputStream value) throws IllegalArgumentException;

    IRequestParams put(@NotNull final String key, @NotNull final InputStream value, @NotNull final String name) throws IllegalArgumentException;

    IRequestParams put(@NotNull final String key, @NotNull final InputStream value, @NotNull final String name, @NotNull final String contentType) throws IllegalArgumentException;

    IRequestParams put(@NotNull final String key, @NotNull final InputStream value, @NotNull final String name, @NotNull final String contentType, boolean autoCloseStream) throws IllegalArgumentException;

    IRequestParams put(@NotNull final String key, @NotNull final Object value);

    IRequestParams put(@NotNull final String key, @NotNull final Integer value);

    IRequestParams put(@NotNull final String key, @NotNull final Long value);

    IRequestParams add(@NotNull final String key, @NotNull final String value);

    IRequestParams remove(@NotNull String key);

    IRequestParams setHttpEntityIsRepeatable(boolean isRepeatable);

    IRequestParams setUseJsonStreamer(boolean useJsonStreamer);

    IRequestParams setAutoCloseInputStreams(boolean flag);

    @NotNull
    HttpEntity getEntity(@Nullable final IResponseHandler progressHandler) throws IOException;

    @NotNull
    String getParamString();

}

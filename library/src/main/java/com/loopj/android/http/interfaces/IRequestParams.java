package com.loopj.android.http.interfaces;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public interface IRequestParams {

    void setContentEncoding(@NotNull final String encoding);

    void put(@NotNull String key, @NotNull String value);

    void put(@NotNull String key, @NotNull File value) throws FileNotFoundException;

    void put(@NotNull String key, @NotNull File value, @NotNull String contentType) throws FileNotFoundException;

    void put(@NotNull String key, @NotNull InputStream value);

    void put(@NotNull String key, @NotNull InputStream value, @NotNull String name);

    void put(@NotNull String key, @NotNull InputStream value, @NotNull String name, @NotNull String contentType);

    void put(@NotNull String key, @NotNull InputStream value, @NotNull String name, @NotNull String contentType, boolean autoCloseStream);

}

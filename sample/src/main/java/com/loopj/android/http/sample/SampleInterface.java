package com.loopj.android.http.sample;

import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IAsyncHttpClientOptions;
import com.loopj.android.http.interfaces.IRequestHandle;
import com.loopj.android.http.interfaces.IResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public interface SampleInterface {

    @NotNull
    List<IRequestHandle> getRequestHandles();

    void addRequestHandle(IRequestHandle handle);

    void onRunButtonPressed();

    void onCancelButtonPressed();

    @Nullable
    Header[] getRequestHeaders();

    @Nullable
    HttpEntity getRequestEntity();

    @NotNull
    IAsyncHttpClient getAsyncHttpClient();

    void setAsyncHttpClient(@NotNull IAsyncHttpClient client);

    @Nullable
    IResponseHandler getResponseHandler();

    @NotNull
    String getDefaultURL();

    boolean isRequestHeadersAllowed();

    boolean isRequestBodyAllowed();

    int getSampleTitle();

    boolean isCancelButtonAllowed();

    @NotNull
    IRequestHandle executeSample(@NotNull IAsyncHttpClient client, @NotNull String URL, @Nullable Header[] headers, @Nullable HttpEntity entity, @Nullable IResponseHandler responseHandler);

    @NotNull
    IAsyncHttpClientOptions getAsyncHttpClientOptions();
}



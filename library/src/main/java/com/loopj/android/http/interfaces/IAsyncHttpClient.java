package com.loopj.android.http.interfaces;

import android.content.Context;

import org.apache.http.Header;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;

public interface IAsyncHttpClient {

    public boolean isSynchronous();

    @NotNull
    public CloseableHttpClient getHttpClient();

    @NotNull
    public ExecutorService getThreadPool();

    public void setThreadPool(@NotNull ExecutorService executorService);

    @NotNull
    public IRequestHandle head(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle get(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle delete(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle options(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle trace(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    public IRequestHandle execute(@NotNull Context context, @NotNull RequestBuilder request, @Nullable IResponseHandler responseHandler);

    boolean cancelAllRequests(boolean mayInterruptRunningRequests);

    @NotNull IAsyncHttpClientOptions getConfigurationOptions();
}

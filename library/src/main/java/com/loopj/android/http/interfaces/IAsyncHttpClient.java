package com.loopj.android.http.interfaces;

import android.content.Context;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;

public interface IAsyncHttpClient {

    boolean isSynchronous();

    @NotNull
    CloseableHttpClient getHttpClient();

    @NotNull
    ExecutorService getThreadPool();

    void setThreadPool(@NotNull ExecutorService executorService);

    @NotNull
    IRequestHandle head(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle get(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle delete(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle options(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle trace(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    @NotNull
    IRequestHandle execute(@NotNull Context context, @NotNull RequestBuilder request, @Nullable IResponseHandler responseHandler);

    boolean cancelAllRequests(boolean mayInterruptRunningRequests);

    @NotNull
    IAsyncHttpClientOptions getConfigurationOptions();
}

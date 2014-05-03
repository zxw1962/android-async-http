package com.loopj.android.http.abstracts;

import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IAsyncHttpClientOptions;

import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AbstractAsyncHttpClient implements IAsyncHttpClient {

    @NotNull
    protected CloseableHttpClient mHttpClient;

    @NotNull
    protected ExecutorService mExecutorService = Executors.newCachedThreadPool();

    @NotNull
    protected IAsyncHttpClientOptions mOptions;

    public AbstractAsyncHttpClient(@NotNull final IAsyncHttpClientOptions asyncHttpClientOptions) {
        HttpClientBuilder mBuilder = HttpClientBuilder.create();
        mHttpClient = asyncHttpClientOptions.buildHttpClient(mBuilder);
        mOptions = asyncHttpClientOptions;
    }

    @NotNull
    @Override
    public CloseableHttpClient getHttpClient() {
        return mHttpClient;
    }

    @NotNull
    @Override
    public ExecutorService getThreadPool() {
        return mExecutorService;
    }

    @Override
    public void setThreadPool(@NotNull ExecutorService executorService) {
        this.mExecutorService = executorService;
    }

    @Override
    public boolean isSynchronous() {
        return mOptions.isSynchronous();
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions getConfigurationOptions() {
        return mOptions;
    }

}

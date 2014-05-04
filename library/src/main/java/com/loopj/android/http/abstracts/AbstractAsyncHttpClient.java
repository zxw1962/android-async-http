package com.loopj.android.http.abstracts;

import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IAsyncHttpClientOptions;

import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContextHC4;
import org.apache.http.protocol.HttpContext;
import org.apache.http.protocol.SyncBasicHttpContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Abstract default implementation of IAsyncHttpClient interface, working with basic {@link
 * org.apache.http.protocol.HttpContext}
 *
 * @see com.loopj.android.http.interfaces.IAsyncHttpClient
 * @see org.apache.http.impl.client.CloseableHttpClient
 * @see java.util.concurrent.ExecutorService
 * @see com.loopj.android.http.interfaces.IAsyncHttpClientOptions
 */
public abstract class AbstractAsyncHttpClient implements IAsyncHttpClient {

    @NotNull
    protected CloseableHttpClient mHttpClient;

    @NotNull
    protected ExecutorService mExecutorService = Executors.newCachedThreadPool();

    @NotNull
    protected IAsyncHttpClientOptions mOptions;

    @NotNull
    private HttpContext mHttpContext = new SyncBasicHttpContext(new BasicHttpContextHC4());

    /**
     * Creates basic instance of AbstractAsyncHttpClient, configured with provided {@link
     * com.loopj.android.http.interfaces.IAsyncHttpClientOptions}
     *
     * @param asyncHttpClientOptions options to be used in configuration (must not be null)
     * @see com.loopj.android.http.interfaces.IAsyncHttpClientOptions
     * @see org.apache.http.impl.client.HttpClientBuilder
     * @see org.apache.http.impl.client.CloseableHttpClient
     */
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

    @NotNull
    @Override
    public HttpContext getHttpContext() {
        return mHttpContext;
    }

    @Override
    public void setHttpContext(@NotNull HttpContext httpContext) {
        this.mHttpContext = httpContext;
    }

    @Nullable
    @Override
    public CredentialsProvider getCredentialsProvider() {
        return mOptions.getDefaultCredentialsProvider();
    }

    @Override
    public void addCredentials(@NotNull AuthScope scope, @NotNull UsernamePasswordCredentials usernamePasswordCredentials) {
        if (getCredentialsProvider() != null) {
            getCredentialsProvider().setCredentials(scope, usernamePasswordCredentials);
        }
    }
}

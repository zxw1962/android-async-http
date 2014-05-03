package com.loopj.android.http.impl;

import com.loopj.android.http.interfaces.IAsyncHttpClientOptions;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.DefaultConnectionReuseStrategyHC4;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategyHC4;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AsyncHttpClientOptions implements IAsyncHttpClientOptions {

    public boolean mIsSynchronous = false;
    public SSLSocketFactory mSslSocketFactory = null;
    public boolean mIsUrlEncodingEnabled = true;
    public String mUserAgent = null;
    public boolean mEnableRedirects = true;
    public boolean mEnableRelativeRedirects = true;
    public boolean mEnableCircularRedirects = true;
    public boolean mEnableContentCompression = false;
    public boolean mKeepConnectionsAlive = true;

    public static IAsyncHttpClientOptions defaults() {
        return new AsyncHttpClientOptions();
    }

    public static IAsyncHttpClientOptions synchronousDefaults() {
        return new AsyncHttpClientOptions().setIsSynchronous(true);
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setIsSynchronous(boolean isSynchronous) {
        this.mIsSynchronous = isSynchronous;
        return this;
    }

    @Override
    public boolean isSynchronous() {
        return mIsSynchronous;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setSSLSocketFactory(@NotNull SSLSocketFactory sslSocketFactory) {
        this.mSslSocketFactory = sslSocketFactory;
        return this;
    }

    @Nullable
    @Override
    public SSLSocketFactory getSSLSocketFactory() {
        return mSslSocketFactory;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setURLEncodingEnabled(boolean isUrlEncodingEnabled) {
        this.mIsUrlEncodingEnabled = isUrlEncodingEnabled;
        return this;
    }

    @Override
    public boolean isURLEncodingEnabled() {
        return mIsUrlEncodingEnabled;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setBasicAuth() {
        return this;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setUserAgent(@NotNull String userAgent) {
        this.mUserAgent = userAgent;
        return this;
    }

    @Nullable
    @Override
    public String getUserAgent() {
        return mUserAgent;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setIsHandlingRedirects(boolean enableRedirects, boolean enableRelativeRedirects, boolean enableCircularRedirects) {
        this.mEnableRedirects = enableRedirects;
        this.mEnableCircularRedirects = enableCircularRedirects;
        this.mEnableRelativeRedirects = enableRelativeRedirects;
        return this;
    }

    @Override
    public boolean isHandlingRedirects() {
        return mEnableRedirects;
    }

    @Override
    public boolean isHandlingCircularRedirects() {
        return mEnableCircularRedirects;
    }

    @Override
    public boolean isHandlingRelativeRedirects() {
        return mEnableRelativeRedirects;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setEnableContentCompression(boolean enableContentCompression) {
        this.mEnableContentCompression = enableContentCompression;
        return this;
    }

    @Override
    public boolean isContentCompressionEnabled() {
        return mEnableContentCompression;
    }

    @NotNull
    @Override
    public IAsyncHttpClientOptions setKeepConnectionsAlive(boolean keepAlive) {
        this.mKeepConnectionsAlive = keepAlive;
        return this;
    }

    @Override
    public boolean areConnectionsKeptAlive() {
        return mKeepConnectionsAlive;
    }

    @NotNull
    @Override
    public CloseableHttpClient buildHttpClient(@NotNull HttpClientBuilder httpClientBuilder) {
        RequestConfig.Builder defaultConfigBuilder = RequestConfig.custom();
        if (mUserAgent != null) {
            httpClientBuilder.setUserAgent(mUserAgent);
        }
        defaultConfigBuilder.setCircularRedirectsAllowed(mEnableCircularRedirects);
        defaultConfigBuilder.setRelativeRedirectsAllowed(mEnableRelativeRedirects);
        defaultConfigBuilder.setRedirectsEnabled(mEnableRedirects);
        if (!mEnableContentCompression) {
            httpClientBuilder.disableContentCompression();
        }
        if (mKeepConnectionsAlive) {
            httpClientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategyHC4.INSTANCE);
        }
        httpClientBuilder.setConnectionReuseStrategy(DefaultConnectionReuseStrategyHC4.INSTANCE);
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100);
        connectionManager.setDefaultMaxPerRoute(100);
        defaultConfigBuilder.setExpectContinueEnabled(true);
        httpClientBuilder.setConnectionManager(connectionManager);
        httpClientBuilder.setKeepAliveStrategy(DefaultConnectionKeepAliveStrategyHC4.INSTANCE);
        httpClientBuilder.setDefaultRequestConfig(defaultConfigBuilder.build());
        return httpClientBuilder.build();
    }
}

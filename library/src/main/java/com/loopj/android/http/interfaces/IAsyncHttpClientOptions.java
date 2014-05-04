package com.loopj.android.http.interfaces;

import org.apache.http.client.CredentialsProvider;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Configuration class, which instance must be provided, to configure library instance. Uses
 * fluent-interface model, so you can call methods consecutively, not just in old procedural way.
 * Default values for each option can be seen in default implementation or it is up to you to set
 * defaults. You can obtain default configuration for asynchronous and synchronous usage within
 * fields {@link com.loopj.android.http.impl.AsyncHttpClientOptions#DEFAULTS} and {@link
 * com.loopj.android.http.impl.AsyncHttpClientOptions#SYNCHRONOUS_DEFAULTS}.
 *
 * @see com.loopj.android.http.abstracts.AbstractAsyncHttpClient#AbstractAsyncHttpClient(IAsyncHttpClientOptions)
 * @see com.loopj.android.http.impl.AsyncHttpClientOptions
 * @see com.loopj.android.http.impl.AsyncHttpClientOptions#DEFAULTS
 * @see com.loopj.android.http.impl.AsyncHttpClientOptions#SYNCHRONOUS_DEFAULTS
 * @see IAsyncHttpClient#getConfigurationOptions()
 */
public interface IAsyncHttpClientOptions {

    /**
     * Sets synchronous mode (requests are executed on caller thread)
     *
     * @param isSynchronous whether requests are executed synchronously or not
     * @return self (for fluent API usage)
     * @see IAsyncHttpClient#isSynchronous()
     * @see #isSynchronous()
     */
    @NotNull
    IAsyncHttpClientOptions setIsSynchronous(final boolean isSynchronous);

    public boolean isSynchronous();

    /**
     * Sets custom provided SSLSocketFactory, to handle SSL requests
     *
     * @param sslSocketFactory SSLSocketFactory to be used instead of default one
     * @return self (for fluent API usage)
     * @see org.apache.http.conn.ssl.SSLSocketFactory
     * @see #getSSLSocketFactory()
     */
    @NotNull
    IAsyncHttpClientOptions setSSLSocketFactory(@NotNull SSLSocketFactory sslSocketFactory);

    @Nullable
    public SSLSocketFactory getSSLSocketFactory();

    /**
     * Sets URL encoding enabled or disabled (replacing spaces with %20)
     *
     * @param isUrlEncodingEnabled whether library should encode URLs or not
     * @return self (for fluent API usage)
     * @see #isURLEncodingEnabled()
     */
    @NotNull
    IAsyncHttpClientOptions setURLEncodingEnabled(final boolean isUrlEncodingEnabled);

    public boolean isURLEncodingEnabled();

    /**
     * Sets User-Agent header to be sent with all requests
     *
     * @param userAgent User-Agent String (may be null)
     * @return self (for fluent API usage)
     * @see #getUserAgent()
     */
    @NotNull
    IAsyncHttpClientOptions setUserAgent(@Nullable final String userAgent);

    @Nullable
    public String getUserAgent();

    /**
     * Allows or disallows handling redirects (all redirects and state of relative and circular
     * redirects)
     *
     * @param enableRedirects         will enable or disable automatic handling of redirects
     * @param enableRelativeRedirects will enable or disable redirects relative to previous URL
     * @param enableCircularRedirects will enable or disable redirects to URL already present in
     *                                redirect chain
     * @return self (for fluent API usage)
     * @see org.apache.http.impl.client.DefaultRedirectStrategy
     * @see org.apache.http.client.CircularRedirectException
     * @see org.apache.http.client.RedirectException
     * @see org.apache.http.client.RedirectStrategy
     * @see #isHandlingCircularRedirects()
     * @see #isHandlingRedirects()
     * @see #isHandlingRelativeRedirects()
     */
    @NotNull
    IAsyncHttpClientOptions setIsHandlingRedirects(final boolean enableRedirects, final boolean enableRelativeRedirects, final boolean enableCircularRedirects);

    public boolean isHandlingRedirects();

    public boolean isHandlingCircularRedirects();

    public boolean isHandlingRelativeRedirects();

    /**
     * Enables or disables compression/decompression of transferred data
     *
     * @param enableContentCompression whether to enable content
     * @return self (for fluent API usage)
     * @see org.apache.http.impl.client.HttpClientBuilder#disableContentCompression()
     */
    @NotNull
    IAsyncHttpClientOptions setEnableContentCompression(final boolean enableContentCompression);

    public boolean isContentCompressionEnabled();

    /**
     * Sets KeepAlive strategy for all requests
     *
     * @return self (for fluent API usage)
     * @see org.apache.http.conn.ConnectionKeepAliveStrategy
     * @see org.apache.http.impl.client.DefaultConnectionKeepAliveStrategyHC4
     * @see #areConnectionsKeptAlive()
     */
    @NotNull
    IAsyncHttpClientOptions setKeepConnectionsAlive(boolean keepAlive);

    public boolean areConnectionsKeptAlive();

    /**
     * Sets {@link com.loopj.android.http.interfaces.IConfigurationInterceptor} which can simply
     * provide all necessary configuration to {@link org.apache.http.impl.client.HttpClientBuilder},
     * {@link org.apache.http.client.config.RequestConfig.Builder} and {@link
     * org.apache.http.impl.conn.PoolingHttpClientConnectionManager}
     *
     * @param configurationInterceptor IConfigurationInterceptor instance (must not be null)
     * @return self (for fluent API usage)
     * @see org.apache.http.impl.client.HttpClientBuilder
     * @see org.apache.http.client.config.RequestConfig.Builder
     * @see org.apache.http.impl.conn.PoolingHttpClientConnectionManager
     * @see com.loopj.android.http.interfaces.IConfigurationInterceptor
     * @see #getConfigurationInterceptor()
     */
    @NotNull
    IAsyncHttpClientOptions setConfigurationInterceptor(@NotNull final IConfigurationInterceptor configurationInterceptor);

    @Nullable
    public IConfigurationInterceptor getConfigurationInterceptor();

    /**
     * Sets maximum allowed parallel connections on library instance
     *
     * @param maxParallelConnections maximum allowed parallel connections (must be equal or bigger
     *                               than 1)
     * @return self (for fluent API usage)
     * @see #getMaxParallelConnectionsTotal()
     */
    @NotNull
    IAsyncHttpClientOptions setMaxParallelConnectionsTotal(final int maxParallelConnections);

    public int getMaxParallelConnectionsTotal();

    /**
     * Sets maximum allowed parallel connections on specific route (host port combination) on
     * library instance
     *
     * @param maxParallelConnectionsPerRoute maximum parallel connections (must be equal or bigger
     *                                       than 1)
     * @return self (for fluent API usage)
     * @see #getMaxParallelConnectionsPerRoute()
     */
    @NotNull
    IAsyncHttpClientOptions setMaxParallelConnectionsPerRoute(final int maxParallelConnectionsPerRoute);

    public int getMaxParallelConnectionsPerRoute();

    /**
     * Sets {@link org.apache.http.impl.client.SystemDefaultCredentialsProvider} instance to be used
     * over the {@link org.apache.http.protocol.HttpContext} of requests, credentials can be added
     * later eg. by using {@link com.loopj.android.http.interfaces.IAsyncHttpClient#addCredentials(org.apache.http.auth.AuthScope,
     * org.apache.http.auth.UsernamePasswordCredentials)}
     *
     * @param credentialsProvider CredentialsProvider instance (must not be null)
     * @return self (for fluent API usage)
     * @see org.apache.http.client.CredentialsProvider
     * @see com.loopj.android.http.interfaces.IAsyncHttpClient#addCredentials(org.apache.http.auth.AuthScope,
     * org.apache.http.auth.UsernamePasswordCredentials)
     * @see IAsyncHttpClient#getCredentialsProvider()
     * @see #getDefaultCredentialsProvider()
     */
    @NotNull
    IAsyncHttpClientOptions setDefaultCredentialsProvider(@NotNull CredentialsProvider credentialsProvider);

    @Nullable
    public CredentialsProvider getDefaultCredentialsProvider();

    /**
     * Builds {@link org.apache.http.impl.client.CloseableHttpClient} instance from given options
     * over provided {@link org.apache.http.impl.client.HttpClientBuilder} instance
     *
     * @param httpClientBuilder HttpClientBuilder instance (must not be null)
     * @return self (for fluent API usage)
     * @see org.apache.http.impl.client.HttpClientBuilder
     * @see org.apache.http.impl.client.CloseableHttpClient
     */
    @NotNull
    CloseableHttpClient buildHttpClient(@NotNull HttpClientBuilder httpClientBuilder);

}

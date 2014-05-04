package com.loopj.android.http.interfaces;

import android.content.Context;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.protocol.HttpContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ExecutorService;

/**
 * Main interface of the library, provides all HTTP protocol methods and some helper functions to
 * configuration of library and requests. See the implementation in {@link
 * com.loopj.android.http.abstracts.AbstractAsyncHttpClient} and {@link
 * com.loopj.android.http.AsyncHttpClient}
 *
 * @see com.loopj.android.http.abstracts.AbstractAsyncHttpClient
 * @see com.loopj.android.http.AsyncHttpClient
 */
public interface IAsyncHttpClient {

    /**
     * Returns whether instance of library operates in synchronous mode, thus invoking requests on
     * caller thread.
     *
     * @return whether is in synchronous mode or not
     * @see android.os.NetworkOnMainThreadException
     */
    boolean isSynchronous();

    /**
     * Returns configured HttpContext instance for library, defaults to {@link
     * org.apache.http.protocol.SyncBasicHttpContext} over {@link org.apache.http.protocol.BasicHttpContextHC4}
     *
     * @return HttpContext used for requests
     * @see org.apache.http.protocol.SyncBasicHttpContext
     * @see
     */
    @NotNull
    public HttpContext getHttpContext();

    /**
     * Sets HttpContext for all future requests
     *
     * @param httpContext must be non-null instance of HttpContext
     * @see org.apache.http.protocol.HttpContext
     */
    public void setHttpContext(@NotNull HttpContext httpContext);

    /**
     * Returns underlying CloseableHttpClient instance
     *
     * @see org.apache.http.impl.client.CloseableHttpClient
     */
    @NotNull
    CloseableHttpClient getHttpClient();

    /**
     * Returns CredentialsProvider if configured
     *
     * @return CredentialsProvider if configured or null
     * @see org.apache.http.client.CredentialsProvider
     */
    @Nullable
    CredentialsProvider getCredentialsProvider();

    /**
     * Adds Username/Password credentials for given AuthScope (Host and port)
     *
     * @param scope                       Authorization scope (HttpHost and Port), (must not be
     *                                    null)
     * @param usernamePasswordCredentials Username and Password credentials (must not be null)
     * @see org.apache.http.auth.AuthScope
     * @see org.apache.http.auth.UsernamePasswordCredentials
     */
    void addCredentials(@NotNull AuthScope scope, @NotNull UsernamePasswordCredentials usernamePasswordCredentials);

    /**
     * Returns used {@link java.util.concurrent.ExecutorService}, defaults to {@link
     * java.util.concurrent.Executors#newCachedThreadPool()}
     *
     * @return ExecutorService used for dispatching requests into separate threads
     * @see java.util.concurrent.ExecutorService
     * @see java.util.concurrent.Executors
     */
    @NotNull
    ExecutorService getThreadPool();

    /**
     * Sets provided ExecutorService as threads source for future requests, ommiting those already
     * requested
     *
     * @param executorService ExecutorService to be used (must not be null)
     * @see java.util.concurrent.ExecutorService
     */
    void setThreadPool(@NotNull ExecutorService executorService);

    /**
     * Dispatches HTTP HEAD request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle head(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP POST request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP POST request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param httpEntity      HttpEntity instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see org.apache.http.HttpEntity
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP PATCH request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP PATCH request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param httpEntity      HttpEntity instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see org.apache.http.HttpEntity
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP PUT request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP PUT request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param httpEntity      HttpEntity instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see org.apache.http.HttpEntity
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP GET request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle get(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP DELETE request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle delete(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP OPTIONS request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle options(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Dispatches HTTP TRACE request over given parameters
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param url             String URL (must not be null)
     * @param headers         array of headers (may be null)
     * @param params          IRequestParams instance (may be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.Header
     * @see com.loopj.android.http.interfaces.IRequestParams
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle trace(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler);

    /**
     * Executes given request directly, can be used for non-standard usage or not provided methods
     *
     * @param context         Android context to assign request to  (must not be null)
     * @param request         RequestBuilder instance, to be configured and used (must not be null)
     * @param responseHandler IResponseHandler instance (may be null)
     * @return IRequestHandle to control life-cycle of request and provide way to cancel the request
     * @see android.content.Context
     * @see org.apache.http.client.methods.RequestBuilder
     * @see com.loopj.android.http.interfaces.IResponseHandler
     * @see com.loopj.android.http.interfaces.IRequestHandle
     */
    @NotNull
    IRequestHandle execute(@NotNull Context context, @NotNull RequestBuilder request, @Nullable IResponseHandler responseHandler);

    boolean cancelAllRequests(boolean mayInterruptRunningRequests);

    /**
     * Retrieves {@link com.loopj.android.http.interfaces.IAsyncHttpClientOptions} instance used to
     * configure library instance
     *
     * @return IAsyncHttpClientOptions instance (not null)
     * @see com.loopj.android.http.interfaces.IAsyncHttpClientOptions
     */
    @NotNull
    IAsyncHttpClientOptions getConfigurationOptions();
}

package com.loopj.android.http;

import android.content.Context;

import com.loopj.android.http.abstracts.AbstractAsyncHttpClient;
import com.loopj.android.http.core.AsyncHttpRequest;
import com.loopj.android.http.impl.AsyncHttpClientOptions;
import com.loopj.android.http.impl.RequestHandle;
import com.loopj.android.http.interfaces.IAsyncHttpClientOptions;
import com.loopj.android.http.interfaces.IRequestHandle;
import com.loopj.android.http.interfaces.IRequestParams;
import com.loopj.android.http.interfaces.IResponseHandler;
import com.loopj.android.http.util.HttpUtil;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDeleteHC4;
import org.apache.http.client.methods.HttpGetHC4;
import org.apache.http.client.methods.HttpHeadHC4;
import org.apache.http.client.methods.HttpOptionsHC4;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPostHC4;
import org.apache.http.client.methods.HttpPutHC4;
import org.apache.http.client.methods.HttpTraceHC4;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AsyncHttpClient extends AbstractAsyncHttpClient {

    public AsyncHttpClient() {
        super(AsyncHttpClientOptions.DEFAULTS);
    }

    public AsyncHttpClient(@NotNull IAsyncHttpClientOptions asyncHttpClientOptions) {
        super(asyncHttpClientOptions);
    }

    @NotNull
    @Override
    public IRequestHandle head(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpHeadHC4.METHOD_NAME, getConfigurationOptions(), url, headers, null, params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpPostHC4.METHOD_NAME, getConfigurationOptions(), url, headers, HttpUtil.paramsToEntity(params, responseHandler), params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle post(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpPostHC4.METHOD_NAME, getConfigurationOptions(), url, headers, httpEntity, null);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle get(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpGetHC4.METHOD_NAME, getConfigurationOptions(), url, headers, null, params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle delete(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpDeleteHC4.METHOD_NAME, getConfigurationOptions(), url, headers, null, params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle options(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpOptionsHC4.METHOD_NAME, getConfigurationOptions(), url, headers, null, params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpPatch.METHOD_NAME, getConfigurationOptions(), url, headers, HttpUtil.paramsToEntity(params, responseHandler), params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle patch(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpPatch.METHOD_NAME, getConfigurationOptions(), url, headers, httpEntity, null);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpPutHC4.METHOD_NAME, getConfigurationOptions(), url, headers, HttpUtil.paramsToEntity(params, responseHandler), params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle put(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpPutHC4.METHOD_NAME, getConfigurationOptions(), url, headers, httpEntity, null);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle trace(@NotNull Context context, @NotNull String url, @Nullable Header[] headers, @Nullable IRequestParams params, @Nullable IResponseHandler responseHandler) {
        RequestBuilder uriRequestBuilder = HttpUtil.createRequestBuilder(HttpTraceHC4.METHOD_NAME, getConfigurationOptions(), url, headers, null, params);
        return execute(context, uriRequestBuilder, responseHandler);
    }

    @NotNull
    @Override
    public IRequestHandle execute(@NotNull Context context, @NotNull RequestBuilder request, @Nullable IResponseHandler responseHandler) {

        HttpUriRequest uriRequest = request.build();

        if (responseHandler != null) {
            responseHandler.setRequestHeaders(uriRequest.getAllHeaders());
        }

        AsyncHttpRequest asyncRequest = new AsyncHttpRequest(getHttpClient(), getHttpContext(), uriRequest, responseHandler);

        if (getConfigurationOptions().isSynchronous()) {
            asyncRequest.run();
        } else {
            getThreadPool().submit(asyncRequest);
        }

        return new RequestHandle(asyncRequest);
    }

    @Override
    public boolean cancelAllRequests(boolean mayInterruptRunningRequests) {
        getHttpClient().getConnectionManager().closeExpiredConnections();
        return true;
    }


}

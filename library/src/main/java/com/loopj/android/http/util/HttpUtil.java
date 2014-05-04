package com.loopj.android.http.util;

import com.loopj.android.http.interfaces.IAsyncHttpClientOptions;
import com.loopj.android.http.interfaces.IRequestParams;
import com.loopj.android.http.interfaces.IResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.RequestBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HttpUtil {

    @Nullable
    public static HttpEntity paramsToEntity(IRequestParams params, IResponseHandler responseHandler) {
        HttpEntity entity = null;
        try {
            if (params != null) {
                entity = params.getEntity(responseHandler);
            }
        } catch (Throwable t) {
            if (responseHandler != null) {
                responseHandler.sendFailureMessage(0, null, null, t);
            } else {
                t.printStackTrace();
            }
        }
        return entity;
    }

    @NotNull
    public static RequestBuilder createRequestBuilder(@NotNull String method, @NotNull IAsyncHttpClientOptions options, @NotNull String url, @Nullable Header[] headers, @Nullable HttpEntity httpEntity, @Nullable IRequestParams requestParams) {
        RequestBuilder mBuilder = RequestBuilder.create(method);
        if (requestParams != null) {
            String paramString = requestParams.getParamString().trim();

            if (!paramString.equals("") && !paramString.equals("?")) {
                url += url.contains("?") ? "&" : "?";
                url += paramString;
            }
        }
        if (options.isURLEncodingEnabled()) {
            url = url.replace(" ", "%20");
        }
        mBuilder.setUri(url);
        if (headers != null) {
            for (Header header : headers) {
                mBuilder.addHeader(header);
            }
        }
        if (httpEntity != null) {
            mBuilder.setEntity(httpEntity);
        }
        return mBuilder;
    }

}

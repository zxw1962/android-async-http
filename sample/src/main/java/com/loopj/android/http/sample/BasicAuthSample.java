package com.loopj.android.http.sample;


import android.net.Uri;
import android.os.Bundle;

import com.loopj.android.http.handlers.TextHttpResponseHandler;
import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IRequestHandle;
import com.loopj.android.http.interfaces.IResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.jetbrains.annotations.NotNull;

public class BasicAuthSample extends SampleParentActivity {
    private static final String LOG_TAG = "BasicAuthSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @NotNull
    @Override
    public IRequestHandle executeSample(@NotNull IAsyncHttpClient client, @NotNull String URL, Header[] headers, HttpEntity entity, IResponseHandler responseHandler) {
        try {
            client.getCredentialsProvider().clear();
            Uri uri = Uri.parse(URL);
            client.addCredentials(new AuthScope(uri.getHost(), uri.getPort()), new UsernamePasswordCredentials("username", "password"));
        } catch (Throwable t) {
            debugThrowable(LOG_TAG, t);
        }
        return client.head(this, URL, headers, null, responseHandler);
    }

    @Override
    public int getSampleTitle() {
        return R.string.title_basic_auth_sample;
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return false;
    }

    @NotNull
    @Override
    public String getDefaultURL() {
        return "http://httpbin.org/basic-auth/username/password";
    }

    @Override
    public IResponseHandler getResponseHandler() {
        return new TextHttpResponseHandler() {

            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, throwable);
                debugResponse(LOG_TAG, responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugResponse(LOG_TAG, responseString);
            }
        };
    }

}

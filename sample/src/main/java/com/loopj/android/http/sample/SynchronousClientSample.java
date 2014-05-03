package com.loopj.android.http.sample;

import android.os.Bundle;
import android.util.Log;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.handlers.AsyncHttpResponseHandler;
import com.loopj.android.http.impl.AsyncHttpClientOptions;
import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IRequestHandle;
import com.loopj.android.http.interfaces.ResponseHandlerInterface;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class SynchronousClientSample extends GetSample {
    private static final String LOG_TAG = "SyncSample";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setAsyncHttpClient(new AsyncHttpClient(AsyncHttpClientOptions.synchronousDefaults()));
    }

    @Override
    public int getSampleTitle() {
        return R.string.title_synchronous;
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return true;
    }

    @Override
    public String getDefaultURL() {
        return "https://httpbin.org/delay/6";
    }

    @Override
    public IRequestHandle executeSample(final IAsyncHttpClient client, final String URL, final Header[] headers, HttpEntity entity, final ResponseHandlerInterface responseHandler) {
        if (client.isSynchronous()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.d(LOG_TAG, "Before Request");
                    client.get(SynchronousClientSample.this, URL, headers, null, responseHandler);
                    Log.d(LOG_TAG, "After Request");
                }
            }).start();
        } else {
            Log.e(LOG_TAG, "Error, not using synchronous mode");
        }
        /**
         * SyncHttpClient does not return RequestHandle,
         * it executes each request directly,
         * therefore those requests are not in cancelable threads
         * */
        return null;
    }

    @Override
    public ResponseHandlerInterface getResponseHandler() {
        return new AsyncHttpResponseHandler() {

            @Override
            public void onStart() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        clearOutputs();
                    }
                });
            }

            @Override
            public void onSuccess(final int statusCode, final Header[] headers, final byte[] response) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        debugHeaders(LOG_TAG, headers);
                        debugStatusCode(LOG_TAG, statusCode);
                        debugResponse(LOG_TAG, new String(response));
                    }
                });
            }

            @Override
            public void onFailure(final int statusCode, final Header[] headers, final byte[] errorResponse, final Throwable e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        debugHeaders(LOG_TAG, headers);
                        debugStatusCode(LOG_TAG, statusCode);
                        debugThrowable(LOG_TAG, e);
                        if (errorResponse != null) {
                            debugResponse(LOG_TAG, new String(errorResponse));
                        }
                    }
                });
            }
        };
    }
}

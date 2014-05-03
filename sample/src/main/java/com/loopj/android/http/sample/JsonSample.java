package com.loopj.android.http.sample;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.handlers.BaseJsonHttpResponseHandler;
import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IRequestHandle;
import com.loopj.android.http.interfaces.IResponseHandler;
import com.loopj.android.http.sample.util.SampleJSON;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

public class JsonSample extends SampleParentActivity {

    private static final String LOG_TAG = "JsonSample";

    @Override
    public IRequestHandle executeSample(IAsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, IResponseHandler responseHandler) {
        return client.get(this, URL, headers, null, responseHandler);
    }

    @Override
    public int getSampleTitle() {
        return R.string.title_json_sample;
    }

    @Override
    public boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    public boolean isRequestHeadersAllowed() {
        return false;
    }

    @Override
    public String getDefaultURL() {
        return "http://httpbin.org/headers";
    }

    @Override
    public IResponseHandler getResponseHandler() {
        return new BaseJsonHttpResponseHandler<SampleJSON>() {

            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawJsonResponse, SampleJSON response) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                if (response != null) {
                    debugResponse(LOG_TAG, rawJsonResponse);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, String rawJsonData, SampleJSON errorResponse) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, throwable);
                if (errorResponse != null) {
                    debugResponse(LOG_TAG, rawJsonData);
                }
            }

            @Override
            protected SampleJSON parseResponse(String rawJsonData, boolean isFailure) throws Throwable {
                return new ObjectMapper().readValues(new JsonFactory().createParser(rawJsonData), SampleJSON.class).next();
            }

        };
    }
}

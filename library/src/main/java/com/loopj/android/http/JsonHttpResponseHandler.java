/*
    Android Asynchronous Http Client
    Copyright (c) 2011 James Smith <james@loopj.com>
    http://loopj.com

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
*/

package com.loopj.android.http;

import android.util.Log;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

/**
 * Used to intercept and handle the responses from requests made using {@link AsyncHttpClient}, with
 * automatic parsing into a {@link JSONObject} or {@link JSONArray}. <p>&nbsp;</p> This class is
 * designed to be passed to get, post, put and delete requests with the {@link #onSuccess(int,
 * org.apache.http.Header[], org.json.JSONArray)} or {@link #onSuccess(int,
 * org.apache.http.Header[], org.json.JSONObject)} methods anonymously overridden. <p>&nbsp;</p>
 * Additionally, you can override the other event methods from the parent class.
 */
public class JsonHttpResponseHandler extends AsyncGenericResponseHandler<Object> {

    private static final String LOG_TAG = "JsonHttpResponseHandler";

    /**
     * Creates new JsonHttpResponseHandler, with JSON String encoding UTF-8
     */
    public JsonHttpResponseHandler() {
        this(DEFAULT_CHARSET);
    }

    /**
     * Creates new JsonHttpRespnseHandler with given JSON String encoding
     *
     * @param encoding String encoding to be used when parsing JSON
     */
    public JsonHttpResponseHandler(String encoding) {
        super();
        setCharset(encoding);
    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        Log.w(LOG_TAG, "onSuccess(int, Header[], JSONObject) was not overriden, but callback was received");
    }

    /**
     * Returns when request succeeds
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     */
    public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
        Log.w(LOG_TAG, "onSuccess(int, Header[], JSONArray) was not overriden, but callback was received");
    }

    @Override
    public final void onSuccess(int statusCode, Header[] headers, Object response) {
        if (response instanceof JSONObject) {
            onSuccess(statusCode, headers, (JSONObject) response);
        } else if (response instanceof JSONArray) {
            onSuccess(statusCode, headers, (JSONArray) response);
        } else {
            onFailure(statusCode, headers, response, new JSONException("Response cannot be parsed as JSON data"));
        }
    }

    /**
     * Calls when request failed
     *
     * @param statusCode http response status line
     * @param headers    response headers if any
     * @param response   parsed response if any
     * @param error      details failed error
     */
    @Override
    public void onFailure(int statusCode, Header[] headers, Object response, Throwable error) {
        Log.w(LOG_TAG, "onFailure(int, Header[], Object, Throwable) was not overriden, but callback was received", error);
    }

    /**
     * Returns Object of type {@link JSONObject}, {@link JSONArray}, String, Boolean, Integer, Long,
     * Double or {@link JSONObject#NULL}, see {@link org.json.JSONTokener#nextValue()}
     *
     * @param responseBody response bytes to be assembled in String and parsed as JSON
     * @return Object parsedResponse
     * @throws org.json.JSONException exception if thrown while parsing JSON
     */
    protected Object parseResponse(byte[] responseBody) throws JSONException {
        if (null == responseBody)
            return null;
        Object result = null;
        // trim the string to prevent start with blank, and test if the string is valid JSON, because the
        // parser don't do this :(. If JSON is not valid this will return null
        String jsonString = getResponseString(responseBody, getCharset());
        if (jsonString != null) {
            jsonString = jsonString.trim();
            if (jsonString.startsWith(UTF8_BOM)) {
                jsonString = jsonString.substring(1);
            }
            if (jsonString.startsWith("{") || jsonString.startsWith("[")) {
                result = new JSONTokener(jsonString).nextValue();
            }
        }
        if (result == null) {
            result = jsonString;
        }
        return result;
    }

    @Override
    public Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        try {
            return parseResponse(getResponseData(response.getEntity()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}

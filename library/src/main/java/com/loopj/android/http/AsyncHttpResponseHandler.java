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

import android.os.Looper;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;

/**
 * Used to intercept and handle the responses from requests made using {@link AsyncHttpClient}. The
 * {@link #onSuccess(int, org.apache.http.Header[], byte[])} method is designed to be anonymously
 * overridden with your own response handling code. <p>&nbsp;</p> Additionally, you can override the
 * {@link #onFailure(int, org.apache.http.Header[], byte[], Throwable)}, {@link #onStart()}, {@link
 * #onFinish()}, {@link #onRetry(int)} and {@link #onProgress(int, int)} methods as required.
 * <p>&nbsp;</p> For example: <p>&nbsp;</p>
 * <pre>
 * AsyncHttpClient client = new AsyncHttpClient();
 * client.get("http://www.google.com", new AsyncHttpResponseHandler() {
 *     &#064;Override
 *     public void onStart() {
 *         // Initiated the request
 *     }
 *
 *     &#064;Override
 *     public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
 *         // Successfully got a response
 *     }
 *
 *     &#064;Override
 *     public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable
 * error)
 * {
 *         // Response failed :(
 *     }
 *
 *     &#064;Override
 *     public void onRetry(int retryNo) {
 *         // Request was retried
 *     }
 *
 *     &#064;Override
 *     public void onProgress(int bytesWritten, int totalSize) {
 *         // Progress notification
 *     }
 *
 *     &#064;Override
 *     public void onFinish() {
 *         // Completed the request (either success or failure)
 *     }
 * });
 * </pre>
 */
public abstract class AsyncHttpResponseHandler extends AsyncGenericResponseHandler<byte[]> {

    /**
     * Creates a new AsyncHttpResponseHandler
     */
    public AsyncHttpResponseHandler() {
        this(null);
    }

    /**
     * Creates a new AsyncHttpResponseHandler with a user-supplied looper. If
     * the passed looper is null, the looper attached to the current thread will
     * be used.
     *
     * @param looper The looper to work with
     */
    public AsyncHttpResponseHandler(Looper looper) {
        super(looper);
    }

    /**
     * Fired when a request returns successfully, override to handle in your own code
     *
     * @param statusCode   the status code of the response
     * @param headers      return headers, if any
     * @param responseBody the body of the HTTP response from the server
     */
    @Override
    public abstract void onSuccess(int statusCode, Header[] headers, byte[] responseBody);

    /**
     * Fired when a request fails to complete, override to handle in your own code
     *
     * @param statusCode   return HTTP status code
     * @param headers      return headers, if any
     * @param responseBody the response body, if any
     * @param error        the underlying cause of the failure
     */
    @Override
    public abstract void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error);

    @Override
    public byte[] handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
        return getResponseData(response.getEntity());
    }
}
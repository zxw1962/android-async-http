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

package com.loopj.android.http.impl;

import android.util.Log;

import com.loopj.android.http.interfaces.IRequestParams;
import com.loopj.android.http.interfaces.IResponseHandler;
import com.loopj.android.http.util.FileWrapper;
import com.loopj.android.http.util.JsonStreamerEntity;
import com.loopj.android.http.util.SimpleMultipartEntity;
import com.loopj.android.http.util.StreamWrapper;

import org.apache.http.HttpEntity;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * A collection of string request parameters or files to send along with requests made from an
 * {@link com.loopj.android.http.AsyncHttpClient} instance. <p>&nbsp;</p> For example:
 * <p>&nbsp;</p>
 * <pre>
 * RequestParams params = new RequestParams();
 * params.put("username", "james");
 * params.put("password", "123456");
 * params.put("email", "my&#064;email.com");
 * params.put("profile_picture", new File("pic.jpg")); // Upload a File
 * params.put("profile_picture2", someInputStream); // Upload an InputStream
 * params.put("profile_picture3", new ByteArrayInputStream(someBytes)); // Upload some bytes
 *
 * Map&lt;String, String&gt; map = new HashMap&lt;String, String&gt;();
 * map.put("first_name", "James");
 * map.put("last_name", "Smith");
 * params.put("user", map); // url params: "user[first_name]=James&amp;user[last_name]=Smith"
 *
 * Set&lt;String&gt; set = new HashSet&lt;String&gt;(); // unordered collection
 * set.add("music");
 * set.add("art");
 * params.put("like", set); // url params: "like=music&amp;like=art"
 *
 * List&lt;String&gt; list = new ArrayList&lt;String&gt;(); // Ordered collection
 * list.add("Java");<>
 * list.add("C");
 * params.put("languages", list); // url params: "languages[]=Java&amp;languages[]=C"
 *
 * String[] colors = { "blue", "yellow" }; // Ordered collection
 * params.put("colors", colors); // url params: "colors[]=blue&amp;colors[]=yellow"
 *
 * List&lt;Map&lt;String, String&gt;&gt; listOfMaps = new ArrayList&lt;Map&lt;String,
 * String&gt;&gt;();
 * Map&lt;String, String&gt; user1 = new HashMap&lt;String, String&gt;();
 * user1.put("age", "30");
 * user1.put("gender", "male");
 * Map&lt;String, String&gt; user2 = new HashMap&lt;String, String&gt;();
 * user2.put("age", "25");
 * user2.put("gender", "female");
 * listOfMaps.add(user1);
 * listOfMaps.add(user2);
 * params.put("users", listOfMaps); // url params: "users[][age]=30&amp;users[][gender]=male&amp;users[][age]=25&amp;users[][gender]=female"
 *
 * AsyncHttpClient client = new AsyncHttpClient();
 * client.post("http://myendpoint.com", params, responseHandler);
 * </pre>
 */
public class RequestParams implements IRequestParams {

    protected final static String LOG_TAG = "RequestParams";
    protected boolean isRepeatable;
    protected boolean useJsonStreamer;
    protected boolean autoCloseInputStreams;
    protected final ConcurrentHashMap<String, String> urlParams = new ConcurrentHashMap<String, String>();
    protected final ConcurrentHashMap<String, StreamWrapper> streamParams = new ConcurrentHashMap<String, StreamWrapper>();
    protected final ConcurrentHashMap<String, FileWrapper> fileParams = new ConcurrentHashMap<String, FileWrapper>();
    protected final ConcurrentHashMap<String, Object> urlParamsWithObjects = new ConcurrentHashMap<String, Object>();
    protected String contentEncoding = HTTP.UTF_8;

    /**
     * Sets content encoding for return value of {@link #getParamString()} and {@link
     * #createFormEntity()} <p>&nbsp;</p> Default encoding is "UTF-8"
     *
     * @param encoding String constant from {@link org.apache.http.protocol.HTTP}
     */
    @Override
    public IRequestParams setContentEncoding(@NotNull final String encoding) {
        if (encoding != null)
            this.contentEncoding = encoding;
        else
            Log.d(LOG_TAG, "setContentEncoding called with null attribute");
        return this;
    }

    /**
     * Constructs a new empty {@code RequestParams} instance.
     */
    public RequestParams() {
        this((Map<String, String>) null);
    }

    /**
     * Constructs a new RequestParams instance containing the key/value string params from the
     * specified map.
     *
     * @param source the source key/value string map to add.
     */
    public RequestParams(Map<String, String> source) {
        if (source != null) {
            for (Map.Entry<String, String> entry : source.entrySet()) {
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * Constructs a new RequestParams instance and populate it with a single initial key/value
     * string param.
     *
     * @param key   the key name for the intial param.
     * @param value the value string for the initial param.
     */
    public RequestParams(final String key, final String value) {
        this(new HashMap<String, String>() {{
            put(key, value);
        }});
    }

    /**
     * Constructs a new RequestParams instance and populate it with multiple initial key/value
     * string param.
     *
     * @param keysAndValues a sequence of keys and values. Objects are automatically converted to
     *                      Strings (including the value {@code null}).
     * @throws IllegalArgumentException if the number of arguments isn't even.
     */
    public RequestParams(Object... keysAndValues) {
        int len = keysAndValues.length;
        if (len % 2 != 0)
            throw new IllegalArgumentException("Supplied arguments must be even");
        for (int i = 0; i < len; i += 2) {
            String key = String.valueOf(keysAndValues[i]);
            String val = String.valueOf(keysAndValues[i + 1]);
            put(key, val);
        }
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull String value) {
        if (key != null && value != null) {
            urlParams.put(key, value);
        }
        return this;
    }

    /**
     * Adds a file to the request.
     *
     * @param key  the key name for the new param.
     * @param file the file to add.
     * @throws java.io.FileNotFoundException throws if wrong File argument was passed
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull File file) throws FileNotFoundException {
        put(key, file, null);
        return this;
    }

    /**
     * Adds a file to the request.
     *
     * @param key         the key name for the new param.
     * @param file        the file to add.
     * @param contentType the content type of the file, eg. application/json
     * @throws java.io.FileNotFoundException throws if wrong File argument was passed
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull File file, @NotNull String contentType) throws FileNotFoundException {
        if (file == null || !file.exists()) {
            throw new FileNotFoundException();
        }
        if (key != null) {
            fileParams.put(key, new FileWrapper(file, contentType));
        }
        return this;
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull InputStream stream) {
        return put(key, stream, null);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key    the key name for the new param.
     * @param stream the input stream to add.
     * @param name   the name of the stream.
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull InputStream stream, @NotNull String name) {
        return put(key, stream, name, null);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull InputStream stream, @NotNull String name, @NotNull String contentType) {
        return put(key, stream, name, contentType, autoCloseInputStreams);
    }

    /**
     * Adds an input stream to the request.
     *
     * @param key         the key name for the new param.
     * @param stream      the input stream to add.
     * @param name        the name of the stream.
     * @param contentType the content type of the file, eg. application/json
     * @param autoClose   close input stream automatically on successful upload
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull InputStream stream, @NotNull String name, @NotNull String contentType, boolean autoClose) {
        if (key != null && stream != null) {
            streamParams.put(key, StreamWrapper.newInstance(stream, name, contentType, autoClose));
        }
        return this;
    }

    /**
     * Adds param with non-string value (e.g. Map, List, Set).
     *
     * @param key   the key name for the new param.
     * @param value the non-string value object for the new param.
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull Object value) {
        if (key != null && value != null) {
            urlParamsWithObjects.put(key, value);
        }
        return this;
    }

    /**
     * Adds a int value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value int for the new param.
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull Integer value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
        return this;
    }

    /**
     * Adds a long value to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value long for the new param.
     */
    @Override
    public IRequestParams put(@NotNull String key, @NotNull Long value) {
        if (key != null) {
            urlParams.put(key, String.valueOf(value));
        }
        return this;
    }

    /**
     * Adds string value to param which can have more than one value.
     *
     * @param key   the key name for the param, either existing or new.
     * @param value the value string for the new param.
     */
    @Override
    public IRequestParams add(@NotNull String key, @NotNull String value) {
        if (key != null && value != null) {
            Object params = urlParamsWithObjects.get(key);
            if (params == null) {
                // Backward compatible, which will result in "k=v1&k=v2&k=v3"
                params = new HashSet<String>();
                this.put(key, params);
            }
            if (params instanceof List) {
                ((List<Object>) params).add(value);
            } else if (params instanceof Set) {
                ((Set<Object>) params).add(value);
            }
        }
        return this;
    }

    /**
     * Removes a parameter from the request.
     *
     * @param key the key name for the parameter to remove.
     */
    @Override
    public IRequestParams remove(@NotNull String key) {
        urlParams.remove(key);
        streamParams.remove(key);
        fileParams.remove(key);
        urlParamsWithObjects.remove(key);
        return this;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append(entry.getValue());
        }

        for (ConcurrentHashMap.Entry<String, StreamWrapper> entry : streamParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("STREAM");
        }

        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            if (result.length() > 0)
                result.append("&");

            result.append(entry.getKey());
            result.append("=");
            result.append("FILE");
        }

        List<BasicNameValuePair> params = getParamsList(null, urlParamsWithObjects);
        for (BasicNameValuePair kv : params) {
            if (result.length() > 0)
                result.append("&");

            result.append(kv.getName());
            result.append("=");
            result.append(kv.getValue());
        }

        return result.toString();
    }

    @Override
    public IRequestParams setHttpEntityIsRepeatable(boolean isRepeatable) {
        this.isRepeatable = isRepeatable;
        return this;
    }

    @Override
    public IRequestParams setUseJsonStreamer(boolean useJsonStreamer) {
        this.useJsonStreamer = useJsonStreamer;
        return this;
    }

    /**
     * Set global flag which determines whether to automatically close input streams on successful
     * upload.
     *
     * @param flag boolean whether to automatically close input streams
     */
    @Override
    public IRequestParams setAutoCloseInputStreams(boolean flag) {
        autoCloseInputStreams = flag;
        return this;
    }

    /**
     * Returns an HttpEntity containing all request parameters.
     *
     * @param progressHandler HttpResponseHandler for reporting progress on entity submit
     * @return HttpEntity resulting HttpEntity to be included along with {@link
     * org.apache.http.client.methods.HttpEntityEnclosingRequestBase}
     * @throws IOException if one of the streams cannot be read
     */
    @NotNull
    @Override
    public HttpEntity getEntity(@Nullable final IResponseHandler progressHandler) throws IOException {
        if (useJsonStreamer) {
            return createJsonStreamerEntity(progressHandler);
        } else if (streamParams.isEmpty() && fileParams.isEmpty()) {
            return createFormEntity();
        } else {
            return createMultipartEntity(progressHandler);
        }
    }

    private HttpEntity createJsonStreamerEntity(IResponseHandler progressHandler) {
        JsonStreamerEntity entity = new JsonStreamerEntity(progressHandler,
                !fileParams.isEmpty() || !streamParams.isEmpty());

        // Add string params
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            entity.addPart(entry.getKey(), entry.getValue());
        }

        // Add non-string params
        for (ConcurrentHashMap.Entry<String, Object> entry : urlParamsWithObjects.entrySet()) {
            entity.addPart(entry.getKey(), entry.getValue());
        }

        // Add file params
        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            entity.addPart(entry.getKey(), entry.getValue());
        }

        // Add stream params
        for (ConcurrentHashMap.Entry<String, StreamWrapper> entry : streamParams.entrySet()) {
            StreamWrapper stream = entry.getValue();
            if (stream.inputStream != null) {
                entity.addPart(entry.getKey(),
                        StreamWrapper.newInstance(
                                stream.inputStream,
                                stream.name,
                                stream.contentType,
                                stream.autoClose)
                );
            }
        }

        return entity;
    }

    private HttpEntity createFormEntity() {
        try {
            return new UrlEncodedFormEntity(getParamsList(), contentEncoding);
        } catch (UnsupportedEncodingException e) {
            Log.e(LOG_TAG, "createFormEntity failed", e);
            return null; // Can happen, if the 'contentEncoding' won't be HTTP.UTF_8
        }
    }

    @NotNull
    private HttpEntity createMultipartEntity(IResponseHandler progressHandler) throws IOException {
        SimpleMultipartEntity entity = new SimpleMultipartEntity(progressHandler);
        entity.setIsRepeatable(isRepeatable);

        // Add string params
        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            entity.addPart(entry.getKey(), entry.getValue());
        }

        // Add non-string params
        List<BasicNameValuePair> params = getParamsList(null, urlParamsWithObjects);
        for (BasicNameValuePair kv : params) {
            entity.addPart(kv.getName(), kv.getValue());
        }

        // Add stream params
        for (ConcurrentHashMap.Entry<String, StreamWrapper> entry : streamParams.entrySet()) {
            StreamWrapper stream = entry.getValue();
            if (stream.inputStream != null) {
                entity.addPart(entry.getKey(), stream.name, stream.inputStream,
                        stream.contentType);
            }
        }

        // Add file params
        for (ConcurrentHashMap.Entry<String, FileWrapper> entry : fileParams.entrySet()) {
            FileWrapper fileWrapper = entry.getValue();
            entity.addPart(entry.getKey(), fileWrapper.file, fileWrapper.contentType);
        }

        return entity;
    }

    protected List<BasicNameValuePair> getParamsList() {
        List<BasicNameValuePair> lparams = new LinkedList<BasicNameValuePair>();

        for (ConcurrentHashMap.Entry<String, String> entry : urlParams.entrySet()) {
            lparams.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }

        lparams.addAll(getParamsList(null, urlParamsWithObjects));

        return lparams;
    }

    private List<BasicNameValuePair> getParamsList(String key, Object value) {
        List<BasicNameValuePair> params = new LinkedList<BasicNameValuePair>();
        if (value instanceof Map) {
            Map map = (Map) value;
            List list = new ArrayList<Object>(map.keySet());
            // Ensure consistent ordering in query string
            Collections.sort(list);
            for (Object nestedKey : list) {
                if (nestedKey instanceof String) {
                    Object nestedValue = map.get(nestedKey);
                    if (nestedValue != null) {
                        params.addAll(getParamsList(key == null ? (String) nestedKey : String.format("%s[%s]", key, nestedKey),
                                nestedValue));
                    }
                }
            }
        } else if (value instanceof List) {
            List list = (List) value;
            int listSize = list.size();
            for (int nestedValueIndex = 0; nestedValueIndex < listSize; nestedValueIndex++) {
                params.addAll(getParamsList(String.format("%s[%d]", key, nestedValueIndex), list.get(nestedValueIndex)));
            }
        } else if (value instanceof Object[]) {
            Object[] array = (Object[]) value;
            int arrayLength = array.length;
            for (int nestedValueIndex = 0; nestedValueIndex < arrayLength; nestedValueIndex++) {
                params.addAll(getParamsList(String.format("%s[%d]", key, nestedValueIndex), array[nestedValueIndex]));
            }
        } else if (value instanceof Set) {
            Set set = (Set) value;
            for (Object nestedValue : set) {
                params.addAll(getParamsList(key, nestedValue));
            }
        } else {
            params.add(new BasicNameValuePair(key, value.toString()));
        }
        return params;
    }

    protected String getParamString() {
        return URLEncodedUtils.format(getParamsList(), contentEncoding);
    }
}
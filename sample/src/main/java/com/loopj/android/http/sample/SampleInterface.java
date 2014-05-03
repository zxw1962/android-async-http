package com.loopj.android.http.sample;

import com.loopj.android.http.interfaces.IAsyncHttpClient;
import com.loopj.android.http.interfaces.IRequestHandle;
import com.loopj.android.http.interfaces.IResponseHandler;

import org.apache.http.Header;
import org.apache.http.HttpEntity;

import java.util.List;

public interface SampleInterface {

    List<IRequestHandle> getRequestHandles();

    void addRequestHandle(IRequestHandle handle);

    void onRunButtonPressed();

    void onCancelButtonPressed();

    Header[] getRequestHeaders();

    HttpEntity getRequestEntity();

    IAsyncHttpClient getAsyncHttpClient();

    void setAsyncHttpClient(IAsyncHttpClient client);

    IResponseHandler getResponseHandler();

    String getDefaultURL();

    boolean isRequestHeadersAllowed();

    boolean isRequestBodyAllowed();

    int getSampleTitle();

    boolean isCancelButtonAllowed();

    IRequestHandle executeSample(IAsyncHttpClient client, String URL, Header[] headers, HttpEntity entity, IResponseHandler responseHandler);
}

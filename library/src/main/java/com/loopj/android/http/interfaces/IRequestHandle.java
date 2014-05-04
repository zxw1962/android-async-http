package com.loopj.android.http.interfaces;

/**
 * Request handle, which can be used to query request state (in meaning of thread-pool allocation)
 * and request cancelation
 *
 * @see com.loopj.android.http.interfaces.IAsyncHttpClient#execute(android.content.Context,
 * org.apache.http.client.methods.RequestBuilder, IResponseHandler)
 */
public interface IRequestHandle {

    boolean cancel(boolean mayInterruptIfRunning);

    boolean isFinished();

    boolean isCancelled();

    boolean shouldBeGarbageCollected();

}

package com.loopj.android.http.interfaces;

public interface IRequestHandle {

    boolean cancel(boolean mayInterruptIfRunning);
    boolean isFinished();
    boolean isCancelled();
    boolean shouldBeGarbageCollected();

}

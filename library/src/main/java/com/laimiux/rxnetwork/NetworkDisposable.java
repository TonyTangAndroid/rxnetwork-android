package com.laimiux.rxnetwork;

import android.content.BroadcastReceiver;
import android.content.Context;

import io.reactivex.disposables.Disposable;

class NetworkDisposable implements Disposable {
    private final Context context;
    private final BroadcastReceiver broadcastReceiver;
    private boolean disposed;

    public NetworkDisposable(Context context, BroadcastReceiver broadcastReceiver) {
        this.context = context;
        this.broadcastReceiver = broadcastReceiver;
    }

    @Override
    public void dispose() {
        this.disposed = true;
        this.context.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}

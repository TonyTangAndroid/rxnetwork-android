package com.laimiux.rxnetwork;

import android.content.BroadcastReceiver;

import io.reactivex.disposables.Disposable;

class MyDisposable implements Disposable {
    private final OnSubscribeBroadcastRegister onSubscribeBroadcastRegister;
    private final BroadcastReceiver broadcastReceiver;
    private boolean disposed;

    public MyDisposable(OnSubscribeBroadcastRegister onSubscribeBroadcastRegister, BroadcastReceiver broadcastReceiver) {
        this.onSubscribeBroadcastRegister = onSubscribeBroadcastRegister;
        this.broadcastReceiver = broadcastReceiver;
    }

    @Override
    public void dispose() {
        this.disposed = true;
        onSubscribeBroadcastRegister.context.unregisterReceiver(broadcastReceiver);
    }

    @Override
    public boolean isDisposed() {
        return disposed;
    }
}

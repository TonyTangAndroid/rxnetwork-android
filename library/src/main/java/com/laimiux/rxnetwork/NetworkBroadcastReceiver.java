package com.laimiux.rxnetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.reactivex.ObservableEmitter;

class NetworkBroadcastReceiver extends BroadcastReceiver {
    private final ObservableEmitter<Intent> observableEmitter;

    public NetworkBroadcastReceiver(ObservableEmitter<Intent> observableEmitter) {
        this.observableEmitter = observableEmitter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        observableEmitter.onNext(intent);
    }
}

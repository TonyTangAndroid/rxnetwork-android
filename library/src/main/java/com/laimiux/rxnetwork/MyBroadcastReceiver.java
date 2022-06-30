package com.laimiux.rxnetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.reactivex.ObservableEmitter;

class MyBroadcastReceiver extends BroadcastReceiver {
    private final ObservableEmitter<Intent> observableEmitter;

    public MyBroadcastReceiver(ObservableEmitter<Intent> observableEmitter) {
        this.observableEmitter = observableEmitter;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        observableEmitter.onNext(intent);
    }
}

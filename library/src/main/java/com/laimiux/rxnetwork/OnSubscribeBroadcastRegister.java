package com.laimiux.rxnetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.annotations.NonNull;


class OnSubscribeBroadcastRegister implements ObservableOnSubscribe<Intent> {

    public final Context context;
    private final IntentFilter intentFilter;
    private final String broadcastPermission;
    private final Handler schedulerHandler;

    OnSubscribeBroadcastRegister(Context context, IntentFilter intentFilter, String broadcastPermission, Handler schedulerHandler) {
        this.context = context;
        this.intentFilter = intentFilter;
        this.broadcastPermission = broadcastPermission;
        this.schedulerHandler = schedulerHandler;
    }


    @Override
    public void subscribe(final @NonNull ObservableEmitter<Intent> observableEmitter) {
        final BroadcastReceiver broadcastReceiver = new MyBroadcastReceiver(observableEmitter);
        observableEmitter.setDisposable(new MyDisposable(this, broadcastReceiver));

        context.registerReceiver(broadcastReceiver, intentFilter, broadcastPermission, schedulerHandler);
    }

}
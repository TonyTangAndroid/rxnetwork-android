package com.laimiux.rxnetwork;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import io.reactivex.Observable;
import io.reactivex.subjects.BehaviorSubject;

public class ConnectivityBroadcastReceiver extends BroadcastReceiver {

    private final BehaviorSubject<Intent> subject = BehaviorSubject.create();

    public ConnectivityBroadcastReceiver() {
    }

    public Observable<Intent> receivedIntent() {
        return subject.hide();
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        subject.onNext(intent);
    }

}

package com.laimiux.rxnetwork;


import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import io.reactivex.Observable;


public class RxNetwork {
    private RxNetwork() {
    }

    public static Observable<Boolean> stream(Context context) {
        return streamDetail(context).map(status -> status != Status.ABSENT);
    }

    public static Observable<Status> streamDetail(Context applicationContext) {
        final IntentFilter action = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        return ContentObservable.fromBroadcast(applicationContext, action)
                .map(intent -> RxNetworkMapper.asStatus(applicationContext))
                .distinctUntilChanged()
                .startWith(RxNetworkMapper.asStatus(applicationContext));
    }
}

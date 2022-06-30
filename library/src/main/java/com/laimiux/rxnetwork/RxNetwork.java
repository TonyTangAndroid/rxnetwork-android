package com.laimiux.rxnetwork;


import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import io.reactivex.Observable;


public class RxNetwork {
    private RxNetwork() {
    }

    static NetworkInfo activeNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null ? cm.getActiveNetworkInfo() : null;
    }

    private static Status getNetworkType(Context context) {
        NetworkInfo networkInfo = activeNetworkInfo(context);
        if (networkInfo != null && networkInfo.isConnected()) {
            int type = networkInfo.getType();
            if (type == ConnectivityManager.TYPE_WIFI) {
                return Status.WIFI;
            } else if (type == ConnectivityManager.TYPE_MOBILE) {
                return Status.MOBILE;
            } else {
                return Status.ABSENT;
            }


        } else {
            return Status.ABSENT;
        }
    }

    public static Observable<Boolean> stream(Context context) {
        return streamDetail(context)
                .map(status -> status != Status.ABSENT);
    }

    public static Observable<Status> streamDetail(Context applicationContext) {
        final IntentFilter action = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        return ContentObservable.fromBroadcast(applicationContext, action)
                .map(intent -> getNetworkType(applicationContext))
                .distinctUntilChanged()
                .startWith(getNetworkType(applicationContext));
    }
}

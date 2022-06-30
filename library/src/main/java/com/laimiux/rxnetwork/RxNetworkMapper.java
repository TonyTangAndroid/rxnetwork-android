package com.laimiux.rxnetwork;


import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


class RxNetworkMapper {

    private RxNetworkMapper() {
    }

    static NetworkInfo extractActiveNetworkInfo(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm != null ? cm.getActiveNetworkInfo() : null;
    }

    static Status asStatus(Context context) {
        NetworkInfo networkInfo = extractActiveNetworkInfo(context);
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
}

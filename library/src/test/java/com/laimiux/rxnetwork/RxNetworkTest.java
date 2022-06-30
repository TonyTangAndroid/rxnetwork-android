package com.laimiux.rxnetwork;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static android.net.ConnectivityManager.TYPE_MOBILE;
import static android.net.ConnectivityManager.TYPE_WIFI;
import static android.net.NetworkInfo.DetailedState.CONNECTED;
import static org.robolectric.Shadows.shadowOf;

import android.app.Application;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Looper;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class RxNetworkTest {


    private TestObserver<Status> testObserver;

    @Before
    public void setup() {
        testObserver = RxNetwork.streamDetail(ApplicationProvider.getApplicationContext()).test();
        testObserver.assertValueCount(1);
    }


    @Test
    public void whenWifiConnectedAndBroadcastSent_shouldReturnWifi() {

        //given
        shadowConnectivityManager().setActiveNetworkInfo(wifiConnectedNetworkInfo());

        //when
        broadcast();
        shadowOf(Looper.getMainLooper()).idle();

        //then
        testObserver.assertValueCount(2);
        testObserver.assertValueAt(1, Status.WIFI);

    }

    @Test
    public void whenMobileConnectedAndBroadcastSent_shouldReturnMobile() {

        //given
        shadowConnectivityManager().setActiveNetworkInfo(mobileConnectedNetworkInfo());

        //when
        broadcast();
        shadowOf(Looper.getMainLooper()).idle();

        //then
        testObserver.assertValueCount(2);
        testObserver.assertValueAt(1, Status.MOBILE);

    }


    @Test
    public void whenConnectionMutated_shouldReturnAllMutatedValues() {

        //given
        shadowConnectivityManager().setActiveNetworkInfo(mobileConnectedNetworkInfo());

        //when
        broadcast();
        shadowOf(Looper.getMainLooper()).idle();

        //then
        testObserver.assertValueCount(2);
        testObserver.assertValueAt(1, Status.MOBILE);

        shadowConnectivityManager().setActiveNetworkInfo(wifiConnectedNetworkInfo());
        broadcast();
        shadowOf(Looper.getMainLooper()).idle();

        testObserver.assertValueCount(3);
        testObserver.assertValueAt(2, Status.WIFI);


    }

    private void broadcast() {
        ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(CONNECTIVITY_ACTION));
    }

    private NetworkInfo wifiConnectedNetworkInfo() {
        return ShadowNetworkInfo.newInstance(CONNECTED, TYPE_WIFI, 0, true, NetworkInfo.State.CONNECTED);
    }

    private NetworkInfo mobileConnectedNetworkInfo() {
        return ShadowNetworkInfo.newInstance(CONNECTED, TYPE_MOBILE, 0, true, NetworkInfo.State.CONNECTED);
    }


    private static ShadowConnectivityManager shadowConnectivityManager() {
        ConnectivityManager connectivityManager = connectivityManager();
        return shadowOf(connectivityManager);
    }

    private static ConnectivityManager connectivityManager() {
        Application application = ApplicationProvider.getApplicationContext();
        return (ConnectivityManager) application.getSystemService(CONNECTIVITY_SERVICE);
    }
}
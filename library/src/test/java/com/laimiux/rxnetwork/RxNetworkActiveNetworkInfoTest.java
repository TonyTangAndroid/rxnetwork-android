package com.laimiux.rxnetwork;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.Shadows.shadowOf;

import android.app.Application;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

/**
 * https://stackoverflow.com/a/41678005
 */
@RunWith(AndroidJUnit4.class)
public class RxNetworkActiveNetworkInfoTest {

    @Test
    public void setActiveNetworkInfo_shouldBeHonored() {
        Application application = ApplicationProvider.getApplicationContext();
        ShadowConnectivityManager shadowConnectivityManager = shadowConnectivityManager();

        //when
        NetworkInfo instructedNetworkInfo = wifiConnectedNetworkInfo();
        shadowConnectivityManager.setActiveNetworkInfo(instructedNetworkInfo);

        //then
        NetworkInfo receivedNetworkInfo = RxNetworkMapper.extractActiveNetworkInfo(application);
        assertThat(receivedNetworkInfo).isNotNull();
        assertThat(receivedNetworkInfo.getType()).isEqualTo(ConnectivityManager.TYPE_WIFI);
        assertThat(receivedNetworkInfo.getDetailedState()).isEqualTo(NetworkInfo.DetailedState.CONNECTED);
    }

    private NetworkInfo wifiConnectedNetworkInfo() {
        return ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, NetworkInfo.State.CONNECTED);
    }

    private static ShadowConnectivityManager shadowConnectivityManager() {
        return shadowOf(connectivityManager());
    }

    private static ConnectivityManager connectivityManager() {
        Application application = ApplicationProvider.getApplicationContext();
        return (ConnectivityManager) application.getSystemService(CONNECTIVITY_SERVICE);
    }

}
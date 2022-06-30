package com.example.demo;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.net.ConnectivityManager.CONNECTIVITY_ACTION;
import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.Shadows.shadowOf;

import android.app.Application;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.DetailedState;
import android.net.NetworkInfo.State;
import android.os.Looper;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.laimiux.samples.SampleActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.shadows.ShadowConnectivityManager;
import org.robolectric.shadows.ShadowNetworkInfo;

/**
 * https://stackoverflow.com/a/41678005
 */
@RunWith(AndroidJUnit4.class)
public class SampleActivityTest {
    @Rule
    public ActivityScenarioRule<SampleActivity> rule = new ActivityScenarioRule<>(SampleActivity.class);


    @Test
    public void case_0_byDefault_shouldHaveSendButtonWithMobileNetwork() {
        ActivityScenario<SampleActivity> scenario = rule.getScenario();
        scenario.onActivity(instance -> assertThat(instance.getSendButtonText()).isEqualTo("Send"));
        scenario.onActivity(instance -> assertThat(instance.getNetworkType()).isEqualTo("mobile"));


    }

    @Test
    public void case_1_whenWifiIsSetupUp_shouldHaveSendButtonWithWifi() {
        ActivityScenario<SampleActivity> scenario = rule.getScenario();

        ShadowConnectivityManager shadowConnectivityManager = shadowConnectivityManager();
        NetworkInfo instructedNetworkInfo = wifiConnectedNetworkInfo();
        shadowConnectivityManager.setActiveNetworkInfo(instructedNetworkInfo);

        broadcast();
        shadowOf(Looper.getMainLooper()).idle();

        scenario.onActivity(instance -> assertThat(instance.getSendButtonText()).isEqualTo("Send"));
        scenario.onActivity(instance -> assertThat(instance.getNetworkType()).isEqualTo("wifi"));

    }


    @Test
    public void case_2_whenNoNetworkIsSetupUp_shouldHaveDisconnectedButton() {
        ActivityScenario<SampleActivity> scenario = rule.getScenario();

        ShadowConnectivityManager shadowConnectivityManager = shadowConnectivityManager();
        NetworkInfo instructedNetworkInfo = disconnectedNetworkInfo();
        shadowConnectivityManager.setActiveNetworkInfo(instructedNetworkInfo);

        broadcast();
        shadowOf(Looper.getMainLooper()).idle();

        scenario.onActivity(instance -> assertThat(instance.getSendButtonText()).isEqualTo("Not Connected"));
        scenario.onActivity(instance -> assertThat(instance.getNetworkType()).isEqualTo("absent"));

    }


    private void broadcast() {
        ApplicationProvider.getApplicationContext().sendBroadcast(new Intent(CONNECTIVITY_ACTION));
    }

    private NetworkInfo wifiConnectedNetworkInfo() {
        return ShadowNetworkInfo.newInstance(NetworkInfo.DetailedState.CONNECTED, ConnectivityManager.TYPE_WIFI, 0, true, NetworkInfo.State.CONNECTED);
    }

    private NetworkInfo disconnectedNetworkInfo() {
        return ShadowNetworkInfo.newInstance(DetailedState.DISCONNECTED, ConnectivityManager.TYPE_DUMMY, 0, true, State.DISCONNECTED);
    }

    private static ShadowConnectivityManager shadowConnectivityManager() {
        return shadowOf(connectivityManager());
    }

    private static ConnectivityManager connectivityManager() {
        Application application = ApplicationProvider.getApplicationContext();
        return (ConnectivityManager) application.getSystemService(CONNECTIVITY_SERVICE);
    }

}
package com.laimiux.rxnetwork;

import static com.google.common.truth.Truth.assertThat;
import static org.robolectric.Shadows.shadowOf;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Looper;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import io.reactivex.observers.TestObserver;

@RunWith(AndroidJUnit4.class)
public class ConnectivityBroadcastReceiverTest {


    @Test
    public void case_01_whenBroadcastSendNonRegisteredAction_shouldReceivedTheIntent() {

        //given
        Context context = ApplicationProvider.getApplicationContext();
        ConnectivityBroadcastReceiver broadcastReceiver = new ConnectivityBroadcastReceiver();
        TestObserver<Intent> test = broadcastReceiver.receivedIntent().test();
        test.assertNoValues();

        //when
        context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.ACTION_CAPTIVE_PORTAL_SIGN_IN), null, null);
        Intent intentToBeBroadcast = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        context.sendBroadcast(intentToBeBroadcast);

        shadowOf(Looper.getMainLooper()).idle();


        //then
        test.assertValueCount(0);

    }


    @Test
    public void case_02_whenBroadcastSendRegisteredActionButMainLooperNotCalled_shouldNotReceivedTheIntent() {
        //given
        Context context = ApplicationProvider.getApplicationContext();
        ConnectivityBroadcastReceiver broadcastReceiver = new ConnectivityBroadcastReceiver();
        TestObserver<Intent> test = broadcastReceiver.receivedIntent().test();
        test.assertNoValues();

        //when
        context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION), null, null);
        Intent intentToBeBroadcast = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        context.sendBroadcast(intentToBeBroadcast);
        //Purposefully keeping the commented method here to highlight what it is doing.
//        shadowOf(Looper.getMainLooper()).idle();

        //then
        test.assertValueCount(0);
    }

    @Test
    public void case_1_whenBroadcastSendRegisteredAction_shouldReceivedTheIntent() {

        //given
        Context context = ApplicationProvider.getApplicationContext();
        ConnectivityBroadcastReceiver broadcastReceiver = new ConnectivityBroadcastReceiver();
        TestObserver<Intent> test = broadcastReceiver.receivedIntent().test();
        test.assertNoValues();

        //when
        context.registerReceiver(broadcastReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION), null, null);
        Intent intentToBeBroadcast = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        context.sendBroadcast(intentToBeBroadcast);
        shadowOf(Looper.getMainLooper()).idle();

        //then
        test.assertValueCount(1);
        Intent intentReceived = test.values().get(0);
        assertThat(intentReceived.getAction()).isEqualTo(ConnectivityManager.CONNECTIVITY_ACTION);
    }

}
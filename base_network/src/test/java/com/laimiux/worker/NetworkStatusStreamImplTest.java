package com.laimiux.worker;

import static com.google.common.truth.Truth.assertThat;

import com.laimiux.rxnetwork.Status;
import com.laimiux.worker.NetworkStatusStreamImpl;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;

public class NetworkStatusStreamImplTest {

  private NetworkStatusStreamImpl impl;

  @Before
  public void setup() {
    impl = new NetworkStatusStreamImpl();
  }

  @Test
  public void connected_shouldReturnFalseByDefault() {
    assertThat(impl.connected()).isFalse();
  }

  @Test
  public void connected_shouldReturnFalseIfAbsentStatusIsEmitted() {
    impl.accept(Status.ABSENT);
    assertThat(impl.connected()).isFalse();
  }

  @Test
  public void connected_shouldReturnTrueIfWifiStatusIsEmitted() {
    impl.accept(Status.WIFI);
    assertThat(impl.connected()).isTrue();
  }

  @Test
  public void connected_shouldReturnTrueIfMobileStatusIsEmitted() {
    impl.accept(Status.MOBILE);
    assertThat(impl.connected()).isTrue();
  }

  @Test
  public void connected_shouldReturnTrueThenFalseIfMobileStatusIsEmittedThenAbsentStausEmitted() {
    impl.accept(Status.MOBILE);
    assertThat(impl.connected()).isTrue();
    impl.accept(Status.ABSENT);
    assertThat(impl.connected()).isFalse();
  }

  @Test
  public void networkConnectedStatusStream() {
    TestObserver<Boolean> test = impl.networkConnectedStatusStream().test();
    test.assertValueCount(0);
    impl.accept(Status.MOBILE);
    test.assertValues(true);
    impl.accept(Status.ABSENT);
    test.assertValues(true, false);
  }

  @Test
  public void networkStatusStream() {
    TestObserver<Status> test = impl.networkStatusStream().test();
    test.assertValueCount(0);
    impl.accept(Status.MOBILE);
    test.assertValues(Status.MOBILE);
    impl.accept(Status.ABSENT);
    test.assertValues(Status.MOBILE, Status.ABSENT);
  }
}

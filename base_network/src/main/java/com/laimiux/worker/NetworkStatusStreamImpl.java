package com.laimiux.worker;

import com.jakewharton.rxrelay2.BehaviorRelay;
import com.laimiux.rxnetwork.Status;
import io.reactivex.Observable;
import timber.log.Timber;

public class NetworkStatusStreamImpl
    implements NetworkStatusStreamImmutable, NetworkStatusStreamMutable {

  BehaviorRelay<Status> booleanBehaviorRelay = BehaviorRelay.create();

  @Override
  public boolean connected() {
    Status status = booleanBehaviorRelay.getValue();
    Timber.v("请求访问当前网络状态status:%s", status);
    return connected(status);
  }

  @Override
  public Observable<Boolean> networkConnectedStatusStream() {
    return booleanBehaviorRelay.hide().map(this::connected);
  }

  @Override
  public Observable<Status> networkStatusStream() {
    return booleanBehaviorRelay.hide();
  }

  private boolean connected(Status status) {
    return Status.WIFI.equals(status) || Status.MOBILE.equals(status);
  }

  @Override
  public void accept(Status status) {
    Timber.v("当前网络状态status:%s", status);
    booleanBehaviorRelay.accept(status);
  }
}

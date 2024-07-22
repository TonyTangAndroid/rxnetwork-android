package com.laimiux.worker;

import com.laimiux.rxnetwork.Status;
import io.reactivex.Observable;

public interface NetworkStatusStreamImmutable extends NetworkStatusProvider {

  Observable<Boolean> networkConnectedStatusStream();

  Observable<Status> networkStatusStream();
}

package com.laimiux.worker;

import android.content.Context;
import com.laimiux.rxnetwork.RxNetwork;
import com.laimiux.contract.AppWorker;
import com.uber.autodispose.AutoDispose;
import com.uber.autodispose.ScopeProvider;
import javax.inject.Inject;

public class NetworkStatusWorker implements AppWorker {

  private final Context context;
  private final NetworkStatusStreamMutable networkStatusStreamMutable;

  @Inject
  public NetworkStatusWorker(
      Context context, NetworkStatusStreamMutable networkStatusStreamMutable) {
    this.context = context;
    this.networkStatusStreamMutable = networkStatusStreamMutable;
  }

  public void attach(ScopeProvider scopeProvider) {
    RxNetwork.streamDetail(context)
        .as(AutoDispose.autoDisposable(scopeProvider))
        .subscribe(networkStatusStreamMutable::accept);
  }
}

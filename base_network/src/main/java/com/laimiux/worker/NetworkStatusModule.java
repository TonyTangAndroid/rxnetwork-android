package com.laimiux.worker;

import com.laimiux.contract.AppScope;
import dagger.Binds;
import dagger.Module;
import dagger.Provides;

@Module
public abstract class NetworkStatusModule {

  @Provides
  @AppScope
  static NetworkStatusStreamImpl impl() {
    return new NetworkStatusStreamImpl();
  }

  @Binds
  @AppScope
  abstract NetworkStatusStreamImmutable immutable(NetworkStatusStreamImpl impl);

  @Binds
  @AppScope
  abstract NetworkStatusProvider bindNetworkStatusProvider(NetworkStatusStreamImpl impl);

  @Binds
  @AppScope
  abstract NetworkStatusStreamMutable mutable(NetworkStatusStreamImpl impl);
}

package com.laimiux.worker;

import com.laimiux.contract.AppWorker;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoSet;

@Module
public abstract class NetworkWorkerModule {

  @Binds
  @IntoSet
  abstract AppWorker networkStatusWorker(NetworkStatusWorker worker);
}

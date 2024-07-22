package com.laimiux.contract;

import com.uber.autodispose.ScopeProvider;

public interface AppWorker {
  void attach(ScopeProvider scopeProvider);
}

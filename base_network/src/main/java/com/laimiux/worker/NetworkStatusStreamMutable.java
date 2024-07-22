package com.laimiux.worker;

import com.laimiux.rxnetwork.Status;

public interface NetworkStatusStreamMutable {
  void accept(Status status);
}

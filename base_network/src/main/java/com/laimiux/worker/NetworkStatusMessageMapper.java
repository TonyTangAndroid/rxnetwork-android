package com.laimiux.worker;

import android.content.Context;
import androidx.annotation.Nullable;
import com.laimiux.base_network_status.R;
import com.laimiux.contract.AppScope;
import javax.inject.Inject;

@AppScope
public class NetworkStatusMessageMapper {

  private final Context context;
  private final NetworkStatusProvider networkStatusProvider;

  @Inject
  public NetworkStatusMessageMapper(Context context, NetworkStatusProvider networkStatusProvider) {
    this.context = context;
    this.networkStatusProvider = networkStatusProvider;
  }

  public String map(@Nullable Throwable throwable) {
    return networkStatusProvider.connected()
        ? errorWithNetwork(throwable)
        : context.getString(R.string.network_absent);
  }

  private String errorWithNetwork(@Nullable Throwable throwable) {
    String errorMessage = throwable == null ? null : throwable.getMessage();
    return errorMessage != null ? errorMessage : context.getString(R.string.unknown_error);
  }
}

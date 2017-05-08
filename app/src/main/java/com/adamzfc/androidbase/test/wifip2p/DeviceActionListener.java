package com.adamzfc.androidbase.test.wifip2p;

import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;

/**
 * Created by adamzfc on 5/5/17.
 */

public interface DeviceActionListener {
    void showDetail(WifiP2pDevice device);

    void connect(WifiP2pConfig config);

    void disconnect();
}

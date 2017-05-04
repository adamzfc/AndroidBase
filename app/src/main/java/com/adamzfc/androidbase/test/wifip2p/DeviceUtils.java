package com.adamzfc.androidbase.test.wifip2p;

import android.net.wifi.p2p.WifiP2pDevice;
import android.util.Log;

/**
 * Created by adamzfc on 5/4/17.
 */

public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    public static String getDeviceStatus(int deviceStatus) {
        Log.d(TAG, "Peer status :" + deviceStatus);
        switch (deviceStatus) {
            case WifiP2pDevice.AVAILABLE:
                return "Available";
            case WifiP2pDevice.INVITED:
                return "Invited";
            case WifiP2pDevice.CONNECTED:
                return "Connected";
            case WifiP2pDevice.FAILED:
                return "Failed";
            case WifiP2pDevice.UNAVAILABLE:
                return "Unavailable";
            default:
                return "Unknown";

        }
    }
}

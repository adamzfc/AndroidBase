package com.adamzfc.androidbase.test.wifip2p;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.AnyThread;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 5/4/17.
 */

public class TestWifiP2PActivity extends FragmentActivity implements DeviceActionListener {

    public static final String KEY_DEVICE = "key_device";
    private static final String TAG = "TestWifiP2PActivity";
    IntentFilter mIntentFilter;

    WifiP2pManager mManager;
    WifiP2pManager.Channel mChannel;
    BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wifi_p2p);
        mManager = (WifiP2pManager) getSystemService(Context.WIFI_P2P_SERVICE);
        mChannel = mManager.initialize(this, getMainLooper(), null);
        mReceiver = new WiFiDirectBroadcastReceiver(mManager, mChannel, this);

        mIntentFilter = new IntentFilter();
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_STATE_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_PEERS_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_CONNECTION_CHANGED_ACTION);
        mIntentFilter.addAction(WifiP2pManager.WIFI_P2P_THIS_DEVICE_CHANGED_ACTION);

        mManager.discoverPeers(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure(int reason) {

            }
        });
    }

    /* register the broadcast receiver with the intent values to be matched */
    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(mReceiver, mIntentFilter);
    }
    /* unregister the broadcast receiver */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mReceiver);
    }

    @Override
    public void showDetail(WifiP2pDevice device) {
        DeviceListFragment listFragment = (DeviceListFragment) getSupportFragmentManager()
                .findFragmentById(R.id.device_list);
        DeviceDetailFragment fragment = (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentByTag("detail");
        if (fragment != null) {
            if (!fragment.isAdded()) {
                getSupportFragmentManager().beginTransaction()
                        .add(android.R.id.content, fragment, "detail")
                        .hide(listFragment)
                        .addToBackStack(null).commit();
            }
            fragment.changeView(device);
        } else {
            Bundle bundle = new Bundle();
            bundle.putParcelable(KEY_DEVICE, device);
            DeviceDetailFragment detailFragment = new DeviceDetailFragment();
            detailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .replace(android.R.id.content, detailFragment, "detail")
                    .hide(listFragment)
                    .addToBackStack(null).commit();
        }
    }

    @Override
    public void connect(WifiP2pConfig config) {
        mManager.connect(mChannel, config, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "connect success");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "Connect failed");
            }
        });
    }

    @Override
    public void disconnect() {
        DeviceDetailFragment fragment = (DeviceDetailFragment) getSupportFragmentManager()
                .findFragmentByTag("detail");
        getSupportFragmentManager().popBackStack();
        mManager.removeGroup(mChannel, new WifiP2pManager.ActionListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG, "disconnect success");
            }

            @Override
            public void onFailure(int reason) {
                Log.d(TAG, "disconnect filaed" + reason);
            }
        });
    }

}

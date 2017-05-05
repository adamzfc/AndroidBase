package com.adamzfc.androidbase.test.wifip2p;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pDeviceList;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.adamzfc.androidbase.R;

import java.util.ArrayList;
import java.util.List;

import static com.adamzfc.androidbase.test.wifip2p.DeviceUtils.getDeviceStatus;

/**
 * Created by adamzfc on 5/4/17.
 */

public class DeviceListFragment extends ListFragment implements WifiP2pManager.PeerListListener {
    private static final String TAG = "DeviceListFragment";
    private List<WifiP2pDevice> mPeers = new ArrayList<>();
    private ProgressDialog mProgressDialog;
    private View mContentView;
    private WifiP2pDevice mDevice;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setListAdapter(new WiFiPeerListAdapter(getActivity(), R.layout.item_peers, mPeers));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContentView = inflater.inflate(R.layout.fragment_test_device_list, container, false);
        return mContentView;
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        WifiP2pDevice device = (WifiP2pDevice) getListAdapter().getItem(position);
        ((DeviceActionListener) getActivity()).showDetail(device);
    }

    @Override
    public void onPeersAvailable(WifiP2pDeviceList peers) {
        mPeers.clear();
        mPeers.addAll(peers.getDeviceList());
        ((WiFiPeerListAdapter) getListAdapter()).notifyDataSetChanged();
    }

    private class WiFiPeerListAdapter extends ArrayAdapter<WifiP2pDevice> {
        private List<WifiP2pDevice> mWifiP2pDevices;


        public WiFiPeerListAdapter(@NonNull Context context, @LayoutRes int resource,
                                   List<WifiP2pDevice> objects) {
            super(context, resource, objects);
            mWifiP2pDevices = objects;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View v = convertView;
            if (v == null) {
                v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_peers, parent, false);
            }
            WifiP2pDevice device = mWifiP2pDevices.get(position);
            if (device != null) {
                TextView top = (TextView) v.findViewById(R.id.device_name);
                TextView bottom = (TextView) v.findViewById(R.id.device_details);
                if (top != null) {
                    top.setText(device.deviceName);
                }
                if (bottom != null) {
                    bottom.setText(getDeviceStatus(device.status));
                }
            }
            return v;
        }
    }


}

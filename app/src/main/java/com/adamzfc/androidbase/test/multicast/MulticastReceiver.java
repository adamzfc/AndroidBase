package com.adamzfc.androidbase.test.multicast;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

import common.utils.NetworkUtils;

/**
 * Created by adamzfc on 5/8/17.
 */

public class MulticastReceiver extends Thread {
    private static final String TAG = "MulticastReceiver";
//    public static final String MCAST_GRP = "228.5.6.7";
    public static final String MCAST_GRP = "224.0.0.0";
    public static final int MCAST_PORT = 6789;
    private final Context mContext;
    private Socket mSocket;
    private MulticastSocket mMulticastSocket;
    private DatagramPacket mDatagramPacket;
    private final Handler mHandler;
    private String mHostIp = NetworkUtils.getIPAddress(true);

    public MulticastReceiver(Handler handler, Context context) {
        this.mHandler = handler;
        this.mContext = context;
    }

    @Override
    public void run() {
        WifiManager wm = (WifiManager) mContext.getApplicationContext()
                .getSystemService(Context.WIFI_SERVICE);
        WifiManager.MulticastLock multicastLock = wm
                .createMulticastLock("mylock");
        multicastLock.acquire();
        byte[] data = new byte[1024];
        try {
            InetAddress groupAddress = InetAddress.getByName(MCAST_GRP);
            mMulticastSocket = new MulticastSocket(MCAST_PORT);
            mMulticastSocket.joinGroup(groupAddress);
            mMulticastSocket.setBroadcast(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
        mDatagramPacket = new DatagramPacket(data, data.length);

        while (true) {
            if (mMulticastSocket != null) {
                try {
                    mMulticastSocket.receive(mDatagramPacket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mDatagramPacket.getAddress() != null) {
                final String clientIp = mDatagramPacket.getAddress().toString();
//                String hostIp = NetworkUtils.getIPAddress(true);
//                Log.d(TAG, "host ip: ------------ " + hostIp);
                Log.d(TAG, "client ip: ---------- " + clientIp.substring(1));
                Log.d(TAG, "host ip: -------------" + mHostIp);
                Log.d(TAG, "received = " + new String(mDatagramPacket.getData()));
                if (!TextUtils.equals(clientIp.substring(1), mHostIp)) {
                    Message message = mHandler.obtainMessage();
                    message.what = 1;
                    message.obj = mDatagramPacket.getData();
                    mHandler.sendMessage(message);
                }
            }
        }

    }
}

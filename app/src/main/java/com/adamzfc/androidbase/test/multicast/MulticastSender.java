package com.adamzfc.androidbase.test.multicast;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

/**
 * Created by adamzfc on 5/8/17.
 */

public class MulticastSender extends Thread {
    private static final String TAG = "MulticastSender";
    private MulticastSocket mSender;
    private DatagramPacket mDatagramPacket;
    private InetAddress mGroup;
    private byte[] mBytes = new byte[1024];
    private Context mContext;

    public MulticastSender(String data, Context context) {
        mBytes = data.getBytes();
        mContext = context;
    }

    @Override
    public void run() {
        try {
            Log.d(TAG, "send start");
            mSender = new MulticastSocket();
            mGroup = InetAddress.getByName(MulticastReceiver.MCAST_GRP);

//            // Check for IP address
//		    WifiManager wim = (WifiManager) mContext.getApplicationContext()
//                    .getSystemService(Context.WIFI_SERVICE);
//		    int ip = wim.getConnectionInfo().getIpAddress();
            mDatagramPacket = new DatagramPacket(mBytes, mBytes.length,
                    mGroup,
                    MulticastReceiver.MCAST_PORT);
            mSender.joinGroup(mGroup);
            mSender.send(mDatagramPacket);
            mSender.close();
            Log.d(TAG, "send end");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String ipToString(int ip, boolean broadcast) {
        String result = new String();

        Integer[] address = new Integer[4];
        for(int i = 0; i < 4; i++)
            address[i] = (ip >> 8*i) & 0xFF;
        for(int i = 0; i < 4; i++) {
            if(i != 3)
                result = result.concat(address[i]+".");
            else result = result.concat("255.");
        }
        return result.substring(0, result.length() - 2);
    }
}

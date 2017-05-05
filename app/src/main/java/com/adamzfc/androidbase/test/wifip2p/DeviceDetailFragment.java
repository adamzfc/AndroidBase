package com.adamzfc.androidbase.test.wifip2p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.adamzfc.androidbase.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by adamzfc on 5/4/17.
 * https://github.com/ahmontero/wifi-direct-demo
 * https://developer.android.com/guide/topics/connectivity/wifip2p.html
 * https://developer.android.com/training/connect-devices-wirelessly/wifi-direct.html
 * https://android.googlesource.com/platform/development/+/master/samples/WiFiDirectServiceDiscovery/src/com/example/android/wifidirect/discovery/WiFiServiceDiscoveryActivity.java
 * http://blog.csdn.net/yichigo/article/details/8472570
 * https://developer.android.com/reference/android/net/wifi/p2p/package-summary.html
 */

public class DeviceDetailFragment extends Fragment implements WifiP2pManager.ConnectionInfoListener {
    private static final String TAG = "Detail";
    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_wifi_detail, container, false);
        view.setBackgroundResource(android.R.color.white);
        return view;
    }

    public void changeView(WifiP2pDevice device) {
        Log.d(TAG, device.toString());
    }

    public static class FileServerAsyncTask extends AsyncTask {

        private static final String TAG = "FileServer";
        private Context context;
        private TextView statusText;

        public FileServerAsyncTask(Context context, View statusText) {
            this.context = context;
            this.statusText = (TextView) statusText;
        }


        /**
         * Start activity that can handle the JPEG image
         */
        @Override
        protected void onPostExecute(Object result) {
            if (result != null) {
                statusText.setText("File copied - " + result);
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + result), "image/*");
                context.startActivity(intent);
            }
        }

        @Override
        protected Object doInBackground(Object[] params) {
            try {

                /**
                 * Create a server socket and wait for client connections. This
                 * call blocks until a connection is accepted from a client
                 */
                ServerSocket serverSocket = new ServerSocket(8888);
                Socket client = serverSocket.accept();

                /**
                 * If this code is reached, a client has connected and transferred data
                 * Save the input stream from the client as a JPEG file
                 */
                final File f = new File(Environment.getExternalStorageDirectory() + "/"
                        + context.getPackageName() + "/wifip2pshared-" + System.currentTimeMillis()
                        + ".jpg");

                File dirs = new File(f.getParent());
                if (!dirs.exists())
                    dirs.mkdirs();
                f.createNewFile();
                InputStream inputstream = client.getInputStream();
                copyFile(inputstream, new FileOutputStream(f));
                serverSocket.close();
                return f.getAbsolutePath();
            } catch (IOException e) {
                Log.e(TAG, e.getMessage());
                return null;
            }
        }

        private void copyFile(InputStream inputstream, FileOutputStream fileOutputStream) {

        }
    }
}

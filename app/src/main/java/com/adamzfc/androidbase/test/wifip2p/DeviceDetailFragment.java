package com.adamzfc.androidbase.test.wifip2p;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.wifi.WpsInfo;
import android.net.wifi.p2p.WifiP2pConfig;
import android.net.wifi.p2p.WifiP2pDevice;
import android.net.wifi.p2p.WifiP2pInfo;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
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
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static android.content.Context.UI_MODE_SERVICE;
import static com.adamzfc.androidbase.test.wifip2p.TestWifiP2PActivity.KEY_DEVICE;

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
    public static final String IP_SERVER = "192.168.49.1";
    public static int PORT = 8988;
    private static boolean isServiceRunning = false;

    private static final int REQUEST_CODE_PICK = 0x0010;

    private WifiP2pDevice mDevice;
    private WifiP2pInfo mInfo;
    private TextView mStatusText;
    private TextView mLocalIP;
    private TextView mClientIp;

    @Override
    public void onConnectionInfoAvailable(WifiP2pInfo info) {
        this.mInfo = info;
        Log.d(TAG, "is group owner" + (mInfo.isGroupOwner ? "yes" : "no"));
        Log.d(TAG, "Group Owner IP - " + mInfo.groupOwnerAddress.getHostAddress());
        if (!isServiceRunning) {
            new FileServerAsyncTask(getActivity(), mStatusText).execute();
            isServiceRunning = true;
        }
        String localIP = DeviceUtils.getLocalIPAddress();
        String client_mac_fixed = mDevice.deviceAddress.replace("99", "19");
        String clientIP = DeviceUtils.getIPFromMac(client_mac_fixed);
        mLocalIP.setText(localIP);
        mClientIp.setText(clientIP);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test_wifi_detail, container, false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            mDevice = bundle.getParcelable(KEY_DEVICE);
        }
        mStatusText = (TextView) view.findViewById(R.id.tv_status_text);
        mLocalIP = (TextView) view.findViewById(R.id.tv_local_ip);
        mClientIp = (TextView) view.findViewById(R.id.tv_client_ip);
        view.findViewById(R.id.bt_connect).setOnClickListener(v -> {
            WifiP2pConfig config = new WifiP2pConfig();
            if (mDevice != null) {
                config.deviceAddress = mDevice.deviceAddress;
            }
            config.wps.setup = WpsInfo.PBC;
            ((TestWifiP2PActivity) getActivity()).connect(config);
        });

        view.findViewById(R.id.bt_disconnect).setOnClickListener(v ->
            ((TestWifiP2PActivity) getActivity()).disconnect()
        );

        view.findViewById(R.id.bt_transfer_file).setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_CODE_PICK);
        });
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String localIP = DeviceUtils.getLocalIPAddress();
        String client_mac_fixed = mDevice.deviceAddress.replace("99", "19");
        String clientIP = DeviceUtils.getIPFromMac(client_mac_fixed);
        Uri uri = data.getData();
        mStatusText.setText("Sending: " + uri);
        Log.d(TAG, "Intent------------" + uri);
        Intent serviceIntent = new Intent(getActivity(), FileTransferService.class);
        serviceIntent.setAction(FileTransferService.ACTION_SEND_FILE);
        serviceIntent.putExtra(FileTransferService.EXTRAS_FILE_PATH, uri.toString());
        if (TextUtils.equals(localIP, IP_SERVER)) {
            serviceIntent.putExtra(FileTransferService.EXTRAS_ADDRESS, clientIP);
        } else {
            serviceIntent.putExtra(FileTransferService.EXTRAS_ADDRESS, IP_SERVER);
        }
        serviceIntent.putExtra(FileTransferService.EXTRAS_PORT, PORT);
        getActivity().startService(serviceIntent);
    }

    public void changeView(WifiP2pDevice device) {
        Log.d(TAG, device.toString());
        mDevice = device;
    }

    public static class FileServerAsyncTask extends AsyncTask<Void, Void, String> {

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
        protected void onPostExecute(String result) {
            if (result != null) {
                statusText.setText("File copied - " + result);
                Intent intent = new Intent();
                intent.setAction(android.content.Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse("file://" + result), "image/*");
                context.startActivity(intent);
            }
        }

        @Override
        protected String doInBackground(Void... params) {
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
    }

    public static boolean copyFile(InputStream inputstream, OutputStream out) {
        byte buf[] = new byte[1024];
        int len;
        try {
            while ((len = inputstream.read(buf)) != -1) {
                out.write(buf, 0, len);
            }
            out.close();
            inputstream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}

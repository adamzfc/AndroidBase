package com.adamzfc.androidbase.test.wifip2p;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created by adamzfc on 5/8/17.
 */

public class FileTransferService extends IntentService {

    private static final String TAG = "FileTransferService";

    private static final int SOCKET_TIMEOUT = 5000;
    public static final String ACTION_SEND_FILE = "action_send_file";
    public static final String EXTRAS_FILE_PATH = "file_path";
    public static final String EXTRAS_ADDRESS = "host_address";
    public static final String EXTRAS_PORT = "port";

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FileTransferService(String name) {
        super(name);
    }

    public FileTransferService() {
        super("FileTransferService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Context context = getApplicationContext();
        if (intent.getAction().equals(ACTION_SEND_FILE)) {
            String fileUri = intent.getExtras().getString(EXTRAS_FILE_PATH);
            String host = intent.getExtras().getString(EXTRAS_ADDRESS);
            int port = intent.getExtras().getInt(EXTRAS_PORT);
            Socket socket = new Socket();

            try {
                Log.d(TAG, "Opening client socket -");
                socket.bind(null);
                socket.connect((new InetSocketAddress(host, port)), SOCKET_TIMEOUT);
                Log.d(TAG, "Client socket - " + socket.isConnected());
                OutputStream stream = socket.getOutputStream();
                ContentResolver cr = context.getContentResolver();
                InputStream is = null;

                try {
                    is = cr.openInputStream(Uri.parse(fileUri));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                DeviceDetailFragment.copyFile(is, stream);
                Log.d(TAG, "Client: Data written");
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    if (socket.isConnected()) {
                        try {
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

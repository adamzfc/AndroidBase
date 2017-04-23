package com.adamzfc.androidbase.test.socket;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.View;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 4/23/17.
 */

public class TestSocketActivity extends Activity {
    private static final int REQUEST_CODE = 0x0011;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_socket);
        checkOverlayPermission();
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkAccessibility(TestSocketActivity.this)) {
                    startService(new Intent(TestSocketActivity.this,
                            SocketService.class));
                }
            }
        });

    }

    public static boolean checkAccessibility(Context context) {
        if (isAccessibilitySettingsOn(context)) {
            context.startActivity(
                    new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
                            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            );
            return false;
        }
        return true;
    }

    private static boolean isAccessibilitySettingsOn(Context context) {
        int accessibilityEnabled = 0;
        try {
            accessibilityEnabled = Settings.Secure.getInt(context.getContentResolver(),
                    Settings.Secure.ACCESSIBILITY_ENABLED);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }

        if (accessibilityEnabled == 1) {
            String services = Settings.Secure.getString(context.getContentResolver(),
                    Settings.Secure.ENABLED_ACCESSIBILITY_SERVICES);
            if (services != null) {
                return services.toLowerCase().contains(context.getPackageName().toLowerCase());
            }
        }

        return false;
    }

    private void checkOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(this)) {
                startActivityForResult(new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getPackageName()))
                        .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                , REQUEST_CODE);
            }
        }
    }
}

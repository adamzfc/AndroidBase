package com.adamzfc.androidbase.test.skin;

import android.app.Activity;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.adamzfc.androidbase.R;
import com.adamzfc.androidbase.test.skin.base.BaseSkinActivity;
import com.adamzfc.androidbase.test.skin.callback.ISkinChangingCallback;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by adamzfc on 4/28/17.
 */

public class TestSkinActivity extends BaseSkinActivity {

    private DrawerLayout mDrawerLayout;
    private ListView mListView;
    private String[] mDatas = new String[]{
            "Activity", "Service", "Activity", "Service", "Activity", "Service"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_skin);
        initView();
        initEvents();
    }

    private void initEvents() {
        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMemu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale + 0.2f;

                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;

                    mMemu.setScaleX(leftScale);
                    mMemu.setScaleY(leftScale);
                    mMemu.setAlpha(0.6f + 0.4f * (1 - scale));
                    mContent.setTranslationX(mMemu.getMeasuredWidth() * (1 - scale));
                    mContent.setPivotX(0);
                    mContent.setPivotY(mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    mContent.setScaleX(rightScale);
                    mContent.setScaleY(rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {

            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.left_menu_container);
        if (fragment == null) {
            fm.beginTransaction().add(R.id.left_menu_container, new MenuLeftFragment()).commit();
        }

        mListView = (ListView) findViewById(R.id.listview);
        mListView.setAdapter(new ArrayAdapter<String>(this, -1, mDatas) {
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                if (convertView == null) {
                    convertView = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.item_test_skin, parent, false);
                }
                TextView tv = (TextView) convertView.findViewById(R.id.tv_title);
                tv.setText(getItem(position));
                return super.getView(position, convertView, parent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_test_skin, menu);
        return true;
    }

    private String mSkinPluginPath = Environment.getExternalStorageDirectory() + File.separator
            + "skin_plugin.apk";
    private String mSkinPluginPkg = "com.adamzfc.skin_plugin";

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.action_change_skin:
                SkinManager.getInstance().changeSkin(mSkinPluginPath, mSkinPluginPkg,
                        new ISkinChangingCallback() {
                    @Override
                    public void onStart() {

                    }

                    @Override
                    public void onError(Exception e) {
//                        Toast.makeText(TestSkinActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {
                        Toast.makeText(TestSkinActivity.this, "Complete", Toast.LENGTH_SHORT).show();
                    }
                });
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}

package com.adamzfc.androidbase;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.adamzfc.router.ActivityRouter;
import com.adamzfc.router.annotation.Router;
import com.adamzfc.router.annotation.SceneTransition;

import common.constants.IntentC;

/**
 * second activity
 */
@Router(IntentC.SECOND)
public class SecondActivity extends AppCompatActivity {
    /**
     * transition imageview
     */
    @SuppressWarnings("visibilitymodifier")
    @SceneTransition("test")
    public ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mImageView = (ImageView) findViewById(R.id.imageView);
        ActivityRouter.bind(this);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}

package com.adamzfc.androidbase.test.drawable;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.adamzfc.androidbase.R;

/**
 * Created by adamzfc on 4/17/17.
 */

public class TestDrawableActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_drawable);
        roundDrawable();
        reflectionDrawable();
        launcherIconDrawable();
        iconDrawable();
    }

    private void roundDrawable() {
        ImageView imageView1 = (ImageView) findViewById(R.id.imageview1);
        ImageView imageView2 = (ImageView) findViewById(R.id.imageview2);

        imageView1.setImageDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_test1)).setCornerRadius(50));
        imageView2.setImageDrawable(new RoundDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_test1)).setCornerRadius(300));
    }

    private void reflectionDrawable() {
        ImageView imageView3 = (ImageView) findViewById(R.id.imageview3);
        ImageView imageView4 = (ImageView) findViewById(R.id.imageview4);

        imageView3.setImageDrawable(new ReflectionDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_test1)).setReflectionHeight(80));
        imageView4.setImageDrawable(new ReflectionDrawable(BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_test1)).setReflectionHeight(120));
    }

    private void launcherIconDrawable() {
        ImageView imageView5 = (ImageView) findViewById(R.id.imageview5);
        final LauncherIconDrawable drawable = new LauncherIconDrawable(imageView5.getDrawable().mutate());
        imageView5.setImageDrawable(drawable);
        drawable.setPercentColor(Color.DKGRAY);
        imageView5.setOnClickListener(new View.OnClickListener() {
            float percent = 0;
            @Override
            public void onClick(View v) {
                drawable.setCurPercent(percent*1.0f/100);
                percent += 5;
                if (percent > 100) {
                    percent = 0;
                }
            }
        });
        ImageView imageView6 = (ImageView) findViewById(R.id.imageview6);
        final LauncherIconDrawable drawable1 = new LauncherIconDrawable(imageView6.getDrawable().mutate());
        imageView6.setImageDrawable(drawable1);
        drawable1.setDefaultColor(Color.DKGRAY);
        drawable1.setPercentColor(Color.WHITE);
        imageView6.setOnClickListener(new View.OnClickListener() {
            float percent = 0;
            @Override
            public void onClick(View v) {
                drawable1.setCurPercent(percent*1.0f/100);
                percent += 5;
                if (percent > 100) {
                    percent = 0;
                }
            }
        });
    }

    private void iconDrawable() {
        IconView imageView7 = (IconView) findViewById(R.id.imageview7);
        IconDrawable drawable = imageView7.getIconDrawable();
        drawable.setBackgroundColor(Color.GREEN);
        drawable.setTextLabel("M");

        IconView imageView8 = (IconView) findViewById(R.id.imageview8);
        IconDrawable drawable1 = imageView8.getIconDrawable();
        drawable1.setBackgroundColor(Color.YELLOW);
        drawable1.setTextColor(Color.DKGRAY);
        drawable1.setTextLabel("王");

        IconView imageView9 = (IconView) findViewById(R.id.imageview9);
        IconDrawable drawable2 = imageView9.getIconDrawable();
        drawable2.setIconLabel(BitmapFactory.decodeResource(getResources(), R.drawable.ic_test1));

        IconView imageView10 = (IconView) findViewById(R.id.imageview10);
        IconDrawable drawable3 = imageView10.getIconDrawable();
        drawable3.setIconLabel(BitmapFactory.decodeResource(getResources(), R.drawable.ic_test2));
    }
}

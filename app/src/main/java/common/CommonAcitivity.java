package common;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.adamzfc.router.ActivityRouter;

/**
 * Created by adamzfc on 3/9/17.
 */

public abstract class CommonAcitivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(setLayout());
        initView();
        ActivityRouter.bind(this);
    }

    /**
     * init view
     */
    protected abstract void initView();

    /**
     * set activity root layout
     * @return layout id
     */
    @LayoutRes
    protected abstract int setLayout();
}

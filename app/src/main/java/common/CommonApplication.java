package common;

import com.adamzfc.androidbase.test.skin.SkinManager;
import com.adamzfc.base.BaseApplication;

/**
 * Created by adamzfc on 3/9/17.
 */

public class CommonApplication extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        SkinManager.getInstance().init(this);
    }
}

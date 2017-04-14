package com.adamzfc.base.util;

import android.text.TextUtils;
import android.widget.TextView;

/**
 * common validator
 * Created by adamzfc on 4/14/17.
 */

final public class CommonValidator extends TextValidator {
    public CommonValidator(TextView textView) {
        super(textView);
    }

    @Override
    public boolean validate(TextView textView) {
        if (TextUtils.isEmpty(textView.getText().toString())) {
            textView.setError(mErrorMsg);
            return false;
        }
        return true;
    }
}

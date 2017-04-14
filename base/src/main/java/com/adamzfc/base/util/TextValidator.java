package com.adamzfc.base.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

/**
 * textvalidator
 * Created by adamzfc on 4/14/17.
 */

public abstract class TextValidator implements TextWatcher, View.OnFocusChangeListener {
    private final TextView textView;
    protected String mErrorMsg;

    public TextValidator(TextView textView) {
        this.textView = textView;
        this.mErrorMsg = textView.getHint().toString();
    }

    public abstract boolean validate(TextView textView);

    public boolean validate() {
        return validate(textView);
    }

    public String getErrorMsg() {
        return this.mErrorMsg;
    }

    public TextView getTextView() {
        return this.textView;
    }

    @Override
    final public void afterTextChanged(Editable s) {
        validate(textView);
    }

    @Override
    final public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // do nothing
    }

    @Override
    final public void onTextChanged(CharSequence s, int start, int before, int count) {
        // do nothing
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            validate((TextView) v);
        }
    }
}

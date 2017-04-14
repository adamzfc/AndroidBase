package com.adamzfc.base.util;

import android.text.InputFilter;
import android.text.InputType;
import android.text.method.NumberKeyListener;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

/**
 * InputTypeUtils
 * Created by adamzfc on 4/14/17.
 */

public final class InputTypeUtils {
    private InputTypeUtils() {
    }

    public interface OnActionListener {
        boolean onAction(TextView v, KeyEvent event);
    }

    /**
     * reletive to android:maxLength
     * @param textView TextView
     * @param num max length
     */
    private static void maxLength(TextView textView, int num) {
        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(num)});
    }

    /**
     * relative to android:maxLines
     * @param textView TextView
     * @param num max lines
     */
    private static void maxLines(TextView textView, int num) {
        textView.setMaxLines(num);
    }

    /**
     * set next action
     * @param textView TextView
     * @param listener OnActionListener
     */
    public static void nextAction(TextView textView, final OnActionListener listener) {
        textView.setImeOptions(EditorInfo.IME_ACTION_NEXT);
        textView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return listener != null && v.getImeOptions() == EditorInfo.IME_ACTION_NEXT
                        && listener.onAction(v, event);
            }
        });
    }

    private static void phoneType(TextView textView) {
        textView.setInputType(InputType.TYPE_CLASS_PHONE);
        maxLength(textView, 11);
    }

    private static void numberType(TextView textView) {
        textView.setInputType(InputType.TYPE_CLASS_NUMBER);
    }

    private static void passwordType(TextView textView) {
        textView.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD | InputType.TYPE_CLASS_TEXT);
    }

    private static void emailType(TextView textView) {
        textView.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ@._";
                return str.toCharArray();
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
    }

    private static void numberAndAlphabeType(TextView textView) {
        textView.setKeyListener(new NumberKeyListener() {
            @Override
            protected char[] getAcceptedChars() {
                String str = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
                return str.toCharArray();
            }

            @Override
            public int getInputType() {
                return InputType.TYPE_CLASS_TEXT;
            }
        });
    }
}

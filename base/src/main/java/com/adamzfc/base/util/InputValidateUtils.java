package com.adamzfc.base.util;

/**
 * inputvalidate utils
 * Created by adamzfc on 4/14/17.
 */

public final class InputValidateUtils {
    private InputValidateUtils(){
    }

    /**
     * set validtor
     * @param validator validator
     */
    public static void setValidator(TextValidator validator) {
        validator.getTextView().addTextChangedListener(validator);
        validator.getTextView().setOnFocusChangeListener(validator);
    }
}

package com.singletonv.messengerf;

import android.widget.EditText;

public class Util {
    public static String getTrimmedValue(EditText editText) {
        return editText.getText().toString().trim();
    }
}

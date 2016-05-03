package com.example.chenzhe.eyerhyme.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by chenzhe on 2016/4/7.
 */
public class ToastUtil {
    public static void printToast(Context context, String temp) {
        Toast.makeText(context, temp, Toast.LENGTH_SHORT).show();
    }
}

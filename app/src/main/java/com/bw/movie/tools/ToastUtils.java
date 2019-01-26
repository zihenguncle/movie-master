package com.bw.movie.tools;

import android.widget.Toast;

import com.bw.movie.application.MyApplication;

/*
* 吐司
* */
public class ToastUtils {
    private static Toast toast;//在类前面声明吐司，确保在这个页面只有一个吐司
    public static void toast(String message){
        if (toast == null) {
            toast = Toast.makeText(MyApplication.getApplication(), message, Toast.LENGTH_SHORT);
        } else {
            toast.cancel();//关闭吐司显示
            toast = Toast.makeText(MyApplication.getApplication(), message, Toast.LENGTH_SHORT);
        }

        toast.show();//重新显示吐司
    }
}

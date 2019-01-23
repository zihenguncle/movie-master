package com.bw.movie.tools;

import android.widget.Toast;

import com.bw.movie.application.MyApplication;

/*
* 吐司
* */
public class ToastUtils {
    public static void toast(String message){
        Toast.makeText(MyApplication.MyApplication(),message,Toast.LENGTH_SHORT).show();
    }
}

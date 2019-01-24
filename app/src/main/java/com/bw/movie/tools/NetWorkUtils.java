package com.bw.movie.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/*
* 判断网络状态，wifi还是4G网络
* */
public class NetWorkUtils {

    //是否有网络
    public static boolean hasNetwork(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        //有网络设备 且设备可用
        return activeNetworkInfo != null && activeNetworkInfo.isAvailable();
    }


}

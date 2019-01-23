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

    //判断是否是手机网络
    public static boolean isMobileNetwork(Context context) {
        //连接管理服务
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        //获取到当前默认可用网络信息
        NetworkInfo activeNetworkInfo = cm.getActiveNetworkInfo();
        //有网络设备 且 网络设备是手机网络
        return activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

}

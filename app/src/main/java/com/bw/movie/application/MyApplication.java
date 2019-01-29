package com.bw.movie.application;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.support.annotation.RequiresApi;

import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.Locale;

import me.jessyan.autosize.AutoSize;
import me.jessyan.autosize.AutoSizeConfig;
import me.jessyan.autosize.onAdaptListener;
import me.jessyan.autosize.utils.LogUtils;

public class MyApplication extends Application {
    private static Context context;
    public static String APP_ID="wxb3852e6a6b7d9516";
    public static IWXAPI api;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public void onCreate() {
        super.onCreate();
        context=getApplicationContext();
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        api.registerApp(APP_ID);
        /**
         * 屏幕适配
         */
        context =this;
        AutoSize.initCompatMultiProcess(this);
        AutoSizeConfig.getInstance().setExcludeFontScale(true).setOnAdaptListener(new onAdaptListener() {
            @Override
            public void onAdaptBefore(Object target, Activity activity) {
                //使用以下代码, 可支持 Android 的分屏或缩放模式, 但前提是在分屏或缩放模式下当用户改变您 App 的窗口大小时
                //系统会重绘当前的页面, 经测试在某些机型, 某些情况下系统不会重绘当前页面, ScreenUtils.getScreenSize(activity) 的参数一定要不要传 Application!!!
//                        AutoSizeConfig.getInstance().setScreenWidth(ScreenUtils.getScreenSize(activity)[0]);
//                        AutoSizeConfig.getInstance().setScreenHeight(ScreenUtils.getScreenSize(activity)[1]);
                LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptBefore!", target.getClass().getName()));
            }

            @Override
            public void onAdaptAfter(Object target, Activity activity) {
                LogUtils.d(String.format(Locale.ENGLISH, "%s onAdaptAfter!", target.getClass().getName()));
            }
        }).setBaseOnWidth(false).setUseDeviceSize(true);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        builder.detectFileUriExposure();

    }


    public static Context getApplication() {
        return context;
    }
}

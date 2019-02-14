package com.bw.movie.splash_page;

import android.Manifest;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.application.MyApplication;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guide_page.GuideActivity;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.view.IView;
import com.bw.movie.tools.NetWorkUtils;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

/**
 * date:2018/1/23
 * author:zhangjing
 * function:启动页跳转到详情页
 */

public class SplashActivity extends AppCompatActivity{
    @BindView(R.id.relative_haswork)
    RelativeLayout relativeLayout_haswork;
    @BindView(R.id.relative_nowork)
    RelativeLayout relativeLayout_nowork;

    private static final long DELAY_TIME = 2000L;

    //读写权限
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE

    };
    //请求状态码
    private static int REQUEST_PERMISSION_CODE = 1;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
      if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE, REQUEST_PERMISSION_CODE);
            }
            else {
                isShowingMain();
            }
        }



    }
    @Override
   public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       super.onRequestPermissionsResult(requestCode, permissions, grantResults);
       if (requestCode == REQUEST_PERMISSION_CODE) {
           isShowingMain();
       }
    }


    private void isShowingMain() {
        String appVersion = getAppVersion();
        String version = (String) SharedPreferencesUtils.getParam(SplashActivity.this, "appVersion", "");
      // Boolean enter_app = (Boolean) SharedPreferencesUtils.getParam(SplashActivity.this, "FIRST_OPEN", false);
        //判断版本号是否一致，一致的话2秒后进入主页面，否则进入引导页
        if(appVersion.equals(version)){
                initData();

        }else {
            SharedPreferencesUtils.setParam(SplashActivity.this,"appVersion",appVersion);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent=new Intent(SplashActivity.this, GuideActivity.class);
                    startActivity(intent);
                    finish();
                }
            },2000);

        }

    }

    public void initData() {
        if(!NetWorkUtils.hasNetwork(this)){
            relativeLayout_nowork.setVisibility(View.VISIBLE);
            relativeLayout_haswork.setVisibility(View.INVISIBLE);
            NetWorkUtils.setNetworkMethod(this);
            return;
        }else {
            relativeLayout_nowork.setVisibility(View.INVISIBLE);
            relativeLayout_haswork.setVisibility(View.VISIBLE);
            delayByTime();
        }

    }

    private void delayByTime() {
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent=new Intent(SplashActivity.this, Login_Success_Activity.class);
              startActivity(intent);
              finish();
          }
      },DELAY_TIME);

    }
    // 软件-版本
    public static String getAppVersion() {

        String versionName = "";
        Application app = (Application) MyApplication.getApplication();
        try {
            PackageManager pkgMng = app.getPackageManager();
            PackageInfo pkgInfo = pkgMng
                    .getPackageInfo(app.getPackageName(), 0);
            versionName = pkgInfo.versionName;
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return versionName;
    }


}

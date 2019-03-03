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

import java.util.List;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        checkPermission();
    }

    boolean isAllGranted = true;
    //申请权限结果返回处理
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {

            // 判断是否所有的权限都已经授予了
            for (int grant : grantResults) {
                if (grant != PackageManager.PERMISSION_GRANTED) {
                    isAllGranted = false;
                }
            }
            if (isAllGranted) {
                // 所有的权限都授予了
              isShowingMain();
            } else {
                // 弹出对话框告诉用户需要权限的原因, 并引导用户去应用权限管理中手动打开权限按钮
                finish();
            }
        }
    }
   public void checkPermission()
   {
       int targetSdkVersion = 0;
       String[] PermissionString={  Manifest.permission.WRITE_EXTERNAL_STORAGE,
               Manifest.permission.READ_PHONE_STATE,
               Manifest.permission.ACCESS_COARSE_LOCATION,};
       try {
           final PackageInfo info = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
           targetSdkVersion = info.applicationInfo.targetSdkVersion;//获取应用的Target版本
       } catch (PackageManager.NameNotFoundException e) {
           e.printStackTrace();
       }

       if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
           //Build.VERSION.SDK_INT是获取当前手机版本 Build.VERSION_CODES.M为6.0系统
           //如果系统>=6.0
           if (targetSdkVersion >= Build.VERSION_CODES.M) {
               //第 1 步: 检查是否有相应的权限
               boolean isAllGranted = checkPermissionAllGranted(PermissionString);
               if (isAllGranted) {
                   //Log.e("err","所有权限已经授权！");
                   isShowingMain();
                   return;
               }
               // 一次请求多个权限, 如果其他有权限是已经授予的将会自动忽略掉
               ActivityCompat.requestPermissions(this,
                       PermissionString, 1);
           }
       }
   }

    /**
     * 检查是否拥有指定的所有权限
     */
    private boolean checkPermissionAllGranted(String[] permissions) {
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                // 只要有一个权限没有被授予, 则直接返回 false
                //Log.e("err","权限"+permission+"没有授权");
                return false;
            }
        }
        return true;
    }

    private void isShowingMain() {
        String appVersion = getAppVersion();
        String version= (String) SharedPreferencesUtils.getParam(SplashActivity.this, "appVersion", "");
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

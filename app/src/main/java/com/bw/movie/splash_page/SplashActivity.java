package com.bw.movie.splash_page;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.application.MyApplication;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guide_page.GuideActivity;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.mvp.view.IView;
import com.bw.movie.tools.NetWorkUtils;
import com.bw.movie.tools.SharedPreferencesUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

/*
* 启动页跳转到引导页
* zhangjing
* 20190123
* */
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
        initData();

    }

    private void isShowingMain() {
        String versionName = getVersionName();
        // String vn = sp.getString("versionname","0.0001");
        String vn = (String) SharedPreferencesUtils.getParam(this, "versionname", "0.0001");
        //判断版本号是否一致，一致的话2秒后进入主页面，否则进入引导页
        if (versionName.equals(vn)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }, DELAY_TIME);

        } else {
            SharedPreferencesUtils.setParam(this, "versionname", versionName);
            if (versionName.equals(vn)) {
                delayByTime();
            }
        }
    }

    public void initData() {
        ButterKnife.bind(this);

        if(!NetWorkUtils.hasNetwork(this)){
            relativeLayout_nowork.setVisibility(View.VISIBLE);
            relativeLayout_haswork.setVisibility(View.INVISIBLE);
        }else {
            relativeLayout_nowork.setVisibility(View.INVISIBLE);
            relativeLayout_haswork.setVisibility(View.VISIBLE);
            //判断是进入主页面还是引导页
        //    isShowingMain();
        delayByTime();

        }

    }
    private String getVersionName(){
        //用来管理手机的APK
        PackageManager pm = getPackageManager();
        try {
            //得到知道的APK的功能清单文件
            PackageInfo info = pm.getPackageInfo(getPackageName(), 0);
            return info.versionName;

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }


    private void delayByTime() {
      new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
              Intent intent=new Intent(SplashActivity.this, GuideActivity.class);
              startActivity(intent);
              finish();
          }
      },DELAY_TIME);

    }


}

package com.bw.movie.splash_page;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.AnimationUtils;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.guide_page.GuideActivity;

/*
* 启动页跳转到引导页
* zhangjing
* 20190123
* */
public class SplashActivity extends BaseActivity {

    private static final long DELAY_TIME = 2000L;

    @Override
    protected int getViewById() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initData() {
        delayByTime();//延时2秒跳转

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

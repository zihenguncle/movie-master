package com.bw.movie.splash_page;

import android.content.Context;
import android.content.Intent;
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
import com.bw.movie.mvp.view.IView;
import com.bw.movie.tools.NetWorkUtils;

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


    public void initData() {
        ButterKnife.bind(this);
        if(!NetWorkUtils.hasNetwork(this)){
            relativeLayout_nowork.setVisibility(View.VISIBLE);
            relativeLayout_haswork.setVisibility(View.INVISIBLE);
        }else {
            relativeLayout_nowork.setVisibility(View.INVISIBLE);
            relativeLayout_haswork.setVisibility(View.VISIBLE);
            delayByTime();//延时2秒跳转
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

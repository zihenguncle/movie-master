package com.bw.movie.guide_page;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.splash_page.SplashActivity;
import com.bw.movie.tools.SharedPreferencesUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/**
 * date:2018/1/25
 * author:zhangjing
 * function:引导页
 */


public class GuideActivity extends AppCompatActivity  {
    @BindView(R.id.guide_viewpager)
    ViewPager guide_viewpager;
    private List<View> views;
    private GuideViewPagerAdapter adapter;
    @BindView(R.id.dots)
    LinearLayout dots;

   @BindView(R.id.btn_enter)
   Button startBtn;

    // 引导页图片资源
    private static final int[] pics = {R.layout.fragment_guide_first,
            R.layout.fragment_guide_second, R.layout.fragment_guide_third, R.layout.fragment_guide_four};


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        views = new ArrayList<View>();
        initImages();
        initDot(views.size());
       // 初始化adapter
        adapter = new GuideViewPagerAdapter(views,this);
        guide_viewpager.setAdapter(adapter);
         dots.getChildAt(0).setSelected(true);
        guide_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            private int index=0;
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==3){
                    startBtn.setVisibility(View.VISIBLE);
                }else {
                    startBtn.setVisibility(View.INVISIBLE);
                }
             dots.getChildAt(position%dots.getChildCount()).setSelected(true);
                //还原原来的点
                if(index>=0){
                    dots.getChildAt(index%dots.getChildCount()).setSelected(false);
                }
                index=position;

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


    Boolean first_open = (Boolean) SharedPreferencesUtils.getParam(GuideActivity.this, "FIRST_OPEN", false);
        if(first_open){
            Intent intent=new Intent(GuideActivity.this, SplashActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void initDot(int size) {
        dots.removeAllViews();
        for (int i=0;i<size;i++){
            ImageView imageView=new ImageView(this);
            imageView.setBackgroundResource(R.drawable.guide_selector);
            LinearLayout.LayoutParams params=
                    new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT);
            int dimension = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 10, getResources().getDisplayMetrics());
            params.leftMargin=dimension;
            params.rightMargin=dimension;
            dots.addView(imageView,params);

        }
    }

  @OnClick({R.id.btn_enter})
    public void getViewClick(View v){
        switch (v.getId()){
            case R.id.btn_enter:
                Intent intent=new Intent(GuideActivity.this, SplashActivity.class);
                startActivity(intent);
           SharedPreferencesUtils.setParam(GuideActivity.this,"FIRST_OPEN",true);
                finish();
                break;
        }
    }

    private void initImages() {
        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            views.add(view);
        }
    }

   @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
    SharedPreferencesUtils.setParam(GuideActivity.this,"FIRST_OPEN",true);
        finish();
    }
}

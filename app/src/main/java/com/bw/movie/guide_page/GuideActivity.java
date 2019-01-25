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
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GuideActivity extends AppCompatActivity  {
    @BindView(R.id.guide_viewpager)
    ViewPager guide_viewpager;
    private List<View> views;
    private GuideViewPagerAdapter adapter;
    @BindView(R.id.group)
    RadioGroup group;
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
        // 初始化adapter
        adapter = new GuideViewPagerAdapter(views,this);
        guide_viewpager.setAdapter(adapter);
        guide_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
               switch (i){
                   case 0:
                       group.check(R.id.but1);
                       startBtn.setVisibility(View.INVISIBLE);
                       break;
                   case 1:
                       group.check(R.id.but2);
                       startBtn.setVisibility(View.INVISIBLE);
                       break;
                   case 2:
                       group.check(R.id.but3);
                       startBtn.setVisibility(View.INVISIBLE);
                       break;
                   case 3:
                       group.check(R.id.but4);
                       startBtn.setVisibility(View.VISIBLE);
                       break;
               }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
         group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
             @Override
             public void onCheckedChanged(RadioGroup group, int checkedId) {
                 switch (checkedId){
                     case R.id.but1:
                         guide_viewpager.setCurrentItem(0);
                         break;
                     case R.id.but2:
                         guide_viewpager.setCurrentItem(1);
                         break;
                     case R.id.but3:
                         guide_viewpager.setCurrentItem(2);
                         break;
                     case R.id.but4:
                         guide_viewpager.setCurrentItem(3);
                         break;
                 }
             }
         });

    }
    @OnClick({R.id.btn_enter})
    public void getViewClick(View v){
        switch (v.getId()){
            case R.id.btn_enter:
                Intent intent=new Intent(GuideActivity.this,LoginActivity.class);
                startActivity(intent);
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





    }

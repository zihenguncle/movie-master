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

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.guide_page.fragment_guide.FragmentGuidFirst;
import com.bw.movie.guide_page.fragment_guide.FragmentGuidFour;
import com.bw.movie.guide_page.fragment_guide.FragmentGuidSecond;
import com.bw.movie.guide_page.fragment_guide.FragmentGuidThired;
import com.bw.movie.login.LoginActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GuideActivity extends AppCompatActivity {
    @BindView(R.id.guide_viewpager)
    ViewPager guide_viewpager;
    private List<Fragment> mListFragment = new ArrayList<Fragment>();
    private PagerAdapter mPgAdapter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        Intent intent=new Intent(GuideActivity.this, LoginActivity.class);
        startActivity(intent);


    }



    private void initView() {
         mListFragment.add(new FragmentGuidFirst());
        mListFragment.add(new FragmentGuidSecond());
        mListFragment.add(new FragmentGuidThired());
        mListFragment.add(new FragmentGuidFour());
    }
}

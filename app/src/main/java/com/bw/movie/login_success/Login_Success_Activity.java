package com.bw.movie.login_success;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.HomeFragment;
import com.bw.movie.login_success.nearby_cinema_fragment.NearbyCinemaFragment;
import com.bw.movie.login_success.person.PersonalFragment;
import com.bw.movie.mvp.eventbus.MessageList;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class  Login_Success_Activity extends AppCompatActivity {

    @BindView(R.id.login_success_viewpager)
    ViewPager loginSuccessViewpager;
    @BindView(R.id.home_fragment)
    RadioButton homeFragment;
    @BindView(R.id.nearby_cinema_fragment)
    RadioButton nearbyCinemaFragment;
    @BindView(R.id.personal_fragment)
    RadioButton personalFragment;
    @BindView(R.id.login_success_group)
    LinearLayout loginSuccessGroup;
    @BindView(R.id.home_fragment_true)
    RadioButton homeFragmentTrue;
    @BindView(R.id.nearby_cinema_fragment_true)
    RadioButton nearbyCinemaFragmentTrue;
    @BindView(R.id.personal_fragment_true)
    RadioButton personalFragmentTrue;
    private List<Fragment> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_success);
        ButterKnife.bind(this);
        initData();

    }


    @OnClick({R.id.home_fragment,R.id.nearby_cinema_fragment,R.id.personal_fragment})
    public void setCheck(View v){
        switch (v.getId()){
                case R.id.home_fragment:
                    loginSuccessViewpager.setCurrentItem(0);
                        homeFragmentTrue.setVisibility(View.VISIBLE);
                        homeFragment.setVisibility(View.GONE);
                        nearbyCinemaFragment.setVisibility(View.VISIBLE);
                        nearbyCinemaFragmentTrue.setVisibility(View.GONE);
                        personalFragment.setVisibility(View.VISIBLE);
                        personalFragmentTrue.setVisibility(View.GONE);
                    break;
                case R.id.nearby_cinema_fragment:
                    loginSuccessViewpager.setCurrentItem(1);
                        homeFragmentTrue.setVisibility(View.GONE);
                        homeFragment.setVisibility(View.VISIBLE);
                        nearbyCinemaFragment.setVisibility(View.GONE);
                        nearbyCinemaFragmentTrue.setVisibility(View.VISIBLE);
                        personalFragment.setVisibility(View.VISIBLE);
                        personalFragmentTrue.setVisibility(View.GONE);
                    break;
                case R.id.personal_fragment:
                    loginSuccessViewpager.setCurrentItem(2);
                        homeFragmentTrue.setVisibility(View.GONE);
                        homeFragment.setVisibility(View.VISIBLE);
                        nearbyCinemaFragment.setVisibility(View.VISIBLE);
                        nearbyCinemaFragmentTrue.setVisibility(View.GONE);
                        personalFragment.setVisibility(View.GONE);
                        personalFragmentTrue.setVisibility(View.VISIBLE);
                    break;
                default:
                    break;
        }
    }

    protected void initData() {

        ButterKnife.bind(this);

        list = new ArrayList<>();
        list.add(new HomeFragment());
        list.add(new NearbyCinemaFragment());
        list.add(new PersonalFragment());
        //添加适配器
        loginSuccessViewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }


}

package com.bw.movie.login_success;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.HomeFragment;
import com.bw.movie.login_success.nearby_cinema_fragment.NearbyCinemaFragment;
import com.bw.movie.login_success.personal_fragment.PersonalFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Login_Success_Activity extends BaseActivity {
    @BindView(R.id.login_success_viewpager)
    ViewPager loginSuccessViewpager;
    @BindView(R.id.radio1)
    RadioButton radio1;
    @BindView(R.id.radio2)
    RadioButton radio2;
    @BindView(R.id.radio3)
    RadioButton radio3;
    @BindView(R.id.group)
    RadioGroup group;
    private List<Fragment> list;

    @Override
    protected int getViewById() {
        return R.layout.activity_login_success;
    }

    @Override
    protected void initData() {
        ButterKnife.bind(this);

        list=new ArrayList<>();
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

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radio1:
                        loginSuccessViewpager.setCurrentItem(0);
                        break;
                    case R.id.radio2:
                        loginSuccessViewpager.setCurrentItem(1);
                        break;
                    case R.id.radio3:
                        loginSuccessViewpager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

    }



}

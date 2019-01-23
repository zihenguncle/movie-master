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
    @BindView(R.id.home_fragment)
    RadioButton homeFragment;
    @BindView(R.id.nearby_cinema_fragment)
    RadioButton nearbyCinemaFragment;
    @BindView(R.id.personal_fragment)
    RadioButton personalFragment;
    @BindView(R.id.login_success_group)
    RadioGroup loginSuccessGroup;
    private List<Fragment> list;

    @Override
    protected int getViewById() {
        return R.layout.activity_login_success;
    }

    @Override
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

        loginSuccessGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.home_fragment:
                        loginSuccessViewpager.setCurrentItem(0);
                        break;
                    case R.id.nearby_cinema_fragment:
                        loginSuccessViewpager.setCurrentItem(1);
                        break;
                    case R.id.personal_fragment:
                        loginSuccessViewpager.setCurrentItem(2);
                        break;
                    default:
                        break;
                }
            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}

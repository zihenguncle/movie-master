package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.fragment.Cinema_Message;
import com.bw.movie.login_success.person.fragment.Movie_Message;
import com.bw.movie.login_success.person.personal_adapter.CimeamaAdapter;
import com.bw.movie.login_success.person.personal_adapter.VideoAdapter;
import com.bw.movie.login_success.person.personal_bean.CimeamaBean;
import com.bw.movie.login_success.person.personal_bean.VideInformationBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Fragment_Focus_Activity extends BaseActivity{

    @BindView(com.bw.movie.R.id.message_ticket)
    RadioButton messageTicket;
    @BindView(com.bw.movie.R.id.message_movie)
    RadioButton messageMovie;
    @BindView(R.id.personal_focus_viewpager)
    ViewPager personal_focus_viewpager;
    @BindView(R.id.perosnal_fouce_radio)
    RadioGroup personal_fouce_radio;
    private List<Fragment> list;
    @BindView(R.id.image_View_Back)
    ImageView image_back;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById(){
        return com.bw.movie.R.layout.personal_focus;
    }


    @Override
    protected void initData() {
        list=new ArrayList<>();
        list.add(new Movie_Message());
        list.add(new Cinema_Message());
        personal_focus_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
        image_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        messageTicket.setChecked(true);
        messageMovie.setChecked(false);
        personal_fouce_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.message_ticket:
                        personal_focus_viewpager.setCurrentItem(0);
                        messageTicket.setChecked(true);
                        messageMovie.setChecked(false);
                        break;
                    case R.id.message_movie:
                        personal_focus_viewpager.setCurrentItem(1);
                        messageTicket.setChecked(false);
                        messageMovie.setChecked(true);
                        break;
                }
            }
        });
        personal_focus_viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        personal_fouce_radio.check(R.id.message_ticket);
                        messageTicket.setChecked(true);
                        messageMovie.setChecked(false);
                        break;
                    case 1:
                        personal_fouce_radio.check(R.id.message_movie);
                        messageTicket.setChecked(false);
                        messageMovie.setChecked(true);
                        default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

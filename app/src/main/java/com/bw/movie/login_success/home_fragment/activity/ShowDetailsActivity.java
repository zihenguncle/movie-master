package com.bw.movie.login_success.home_fragment.activity;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.adapter.ShowDetailsAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.home_fragment.fargment.DoingFragment;
import com.bw.movie.login_success.home_fragment.fargment.HotFragment;
import com.bw.movie.login_success.home_fragment.fargment.TodoFragment;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.show_details_viewpager)
    ViewPager show_details_viewpager;
    @BindView(R.id.show_details_hot)
    RadioButton showDetailsHot;
    @BindView(R.id.show_deltails_doing)
    RadioButton showDeltailsDoing;
    @BindView(R.id.show_deltails_todo)
    RadioButton showDeltailsTodo;
    @BindView(R.id.show_details_radio)
    RadioGroup show_details_radio;
    private List<Fragment> list;
    @BindView(R.id.show_details_home_search)
    ImageView image_search;
    @BindView(R.id.show_details_relative_search)
    RelativeLayout relative_search;
    @BindView(R.id.show_details_edit_search)
    EditText edit_search;

    @OnClick({R.id.show_details_hot, R.id.show_deltails_doing, R.id.show_deltails_todo,R.id.show_details_home_search,R.id.show_details_text_search})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_details_hot:
                show_details_viewpager.setCurrentItem(0);
                showDetailsHot.setChecked(true);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(false);
                break;
            case R.id.show_deltails_doing:
                show_details_viewpager.setCurrentItem(1);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(true);
                showDeltailsTodo.setChecked(false);
                break;
            case R.id.show_deltails_todo:
                show_details_viewpager.setCurrentItem(2);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(true);
                break;
            case R.id.show_details_home_search:
                getSearch();
                break;
            case R.id.show_details_text_search:
                gotoSearch();
                break;
            default:
                break;
        }
    }

    private void gotoSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", -600, 0);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(true);
        }else {
            ToastUtils.toast(edit_search.getText().toString());
        }
    }

    //点击放大镜弹出搜索框
    private void getSearch() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", 0, -600);
        translationX.setDuration(500);
        translationX.start();
        image_search.setClickable(false);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.show_details_activity;
    }

    @Override
    protected void initData() {

        Intent intent = getIntent();
        String hot = intent.getStringExtra("hot");
        switch (hot){
            case "1":
                show_details_viewpager.setCurrentItem(0);
                showDetailsHot.setChecked(true);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(false);
                break;
            case "2":
                show_details_viewpager.setCurrentItem(1);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(true);
                showDeltailsTodo.setChecked(false);
                break;
            case "3":
                show_details_viewpager.setCurrentItem(2);
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(true);
                break;
        }
        list = new ArrayList<>();
        list.add(new HotFragment());
        list.add(new DoingFragment());
        list.add(new TodoFragment());

        show_details_viewpager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return list.get(i);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });

        show_details_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i){
                    case 0:
                        show_details_radio.check(R.id.show_details_hot);
                        showDetailsHot.setChecked(true);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 1:
                        show_details_radio.check(R.id.show_deltails_doing);
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(true);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 2:
                        show_details_radio.check(R.id.show_deltails_todo);
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(true);
                        break;
                        default:break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        show_details_radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:
                        showDetailsHot.setChecked(true);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 1:
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(true);
                        showDeltailsTodo.setChecked(false);
                        break;
                    case 2:
                        showDetailsHot.setChecked(false);
                        showDeltailsDoing.setChecked(false);
                        showDeltailsTodo.setChecked(true);
                        break;
                }
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

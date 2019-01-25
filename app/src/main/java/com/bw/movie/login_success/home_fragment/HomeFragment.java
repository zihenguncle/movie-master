package com.bw.movie.login_success.home_fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.home_fragment.adapter.HomeBannerAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * @author 郭淄恒
 * @date 2019.1.24      15:57
 *
 * 影片主页面
 *
 */
public class HomeFragment extends BaseFragment {


    @BindView(R.id.home_search)
    ImageView image_search;
    @BindView(R.id.home_edit_search)
    EditText edit_search;
    @BindView(R.id.home_text_search)
    TextView but_search;
    @BindView(R.id.relative_search)
    RelativeLayout relative_search;
    @BindView(R.id.custom_recycle)
    RecyclerCoverFlow coverFlow_recycle;

    HomeBannerAdapter homeBannerAdapter;
    @Override
    protected void initData() {


        startRequestGet(String.format(Apis.URL_BANNER,1,10),HomeBannerBean.class);
        homeBannerAdapter = new HomeBannerAdapter(getActivity());
        coverFlow_recycle.setAdapter(homeBannerAdapter);
    }

    @OnClick({R.id.home_text_search,R.id.home_search})
    public void onClick(View v) {
        switch (v.getId()){
            //点击进行搜索
            case R.id.home_text_search:
                    gotoSearch();
                break;
             //点击放大镜弹出搜索框
            case R.id.home_search:
                    getSearch();
                break;
                default:
                    break;
        }
    }

    //进行搜索，如果输入框有信息进行搜索，否则收起
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

    //搜索框出现
    private void getSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", 0, -600);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(false);
        }
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof HomeBannerBean){
            HomeBannerBean homeBannerBean= (HomeBannerBean) data;
            TextUtils.isEmpty(homeBannerBean.getMessage());
            homeBannerAdapter.setData(((HomeBannerBean) data).getResult());
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    protected int getViewById() {
        return R.layout.fargment_home;
    }



}

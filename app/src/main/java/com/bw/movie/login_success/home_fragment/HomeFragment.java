package com.bw.movie.login_success.home_fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.home_fragment.adapter.HomeBannerAdapter;
import com.bw.movie.login_success.home_fragment.adapter.MovieAdapter;
import com.bw.movie.login_success.home_fragment.adapter.MovieDoingAdapter;
import com.bw.movie.login_success.home_fragment.adapter.MovieNoceAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBeanone;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBeantwo;
import com.bw.movie.login_success.home_fragment.bean.HomeMovieBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import recycler.coverflow.CoverFlowLayoutManger;
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
    MovieAdapter movieAdapter;
    MovieDoingAdapter doingAdapter;
    MovieNoceAdapter noceAdapter;
    @BindView(R.id.hot_movie_xrecycle)
    RecyclerView hot_recycle;
    @BindView(R.id.doing_movie_xrecycle)
    RecyclerView doing_recycle;
    @BindView(R.id.noce_movie_xrecycle)
    RecyclerView noce_recycle;

    @BindView(R.id.banner_tag)
    RadioGroup radiogroup;
    private List<HomeBannerBean.ResultBean> result;

    @Override
    protected void initData() {

        startRequestGet(String.format(Apis.URL_BANNER,1,10),HomeBannerBean.class);
        setLayout();
        startRequestGet(String.format(Apis.URL_HOTMOVIE,1,10),HomeBannerBeanone.class);
        startRequestGet(String.format(Apis.URL_DOINGMOVIE,1,10),HomeBannerBeantwo.class);
        movieAdapter = new MovieAdapter(getActivity());
        hot_recycle.setAdapter(movieAdapter);
        doingAdapter = new MovieDoingAdapter(getActivity());
        doing_recycle.setAdapter(doingAdapter);
        noceAdapter = new MovieNoceAdapter(getActivity());
        noce_recycle.setAdapter(noceAdapter);
        coverFlow_recycle.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
               int i = position%result.size();
               setCuur(i);
            }
        });
    }

    private void setCuur(int i) {
        switch (i){
            case 0:
                radiogroup.check(R.id.but_one);
                break;
            case 1:
                radiogroup.check(R.id.but_two);
                break;
            case 2:
                radiogroup.check(R.id.but_three);
                break;
            case 3:
                radiogroup.check(R.id.but_four);
                break;
            case 4:
                radiogroup.check(R.id.but_five);
                break;
            case 5:
                radiogroup.check(R.id.but_sex);
                break;
            case 6:
                radiogroup.check(R.id.but_seveen);
                break;
            default:
                break;
        }
    }
    //布局管理器
    private void setLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_recycle.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        doing_recycle.setLayoutManager(linearLayoutManager1);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        noce_recycle.setLayoutManager(linearLayoutManager2);
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
            homeBannerAdapter = new HomeBannerAdapter(((HomeBannerBean) data).getResult(),getActivity());
            coverFlow_recycle.setAdapter(homeBannerAdapter);
            result = ((HomeBannerBean) data).getResult();
            noceAdapter.setData(((HomeBannerBean) data).getResult());
        }

        if(data instanceof HomeBannerBeantwo){
            doingAdapter.setData(((HomeBannerBeantwo) data).getResult());
        }
        if (data instanceof HomeBannerBeanone){
            movieAdapter.setData(((HomeBannerBeanone) data).getResult());
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

package com.bw.movie.login_success.home_fragment.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.RadioButton;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.adapter.ShowDetailsAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ShowDetailsActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.show_details_xrecycle)
    XRecyclerView show_details_recycle;
    @BindView(R.id.show_details_hot)
    RadioButton showDetailsHot;
    @BindView(R.id.show_deltails_doing)
    RadioButton showDeltailsDoing;
    @BindView(R.id.show_deltails_todo)
    RadioButton showDeltailsTodo;
    private int mPage;
    private static final int TYPE_COUNT = 10;
    private ShowDetailsAdapter showDetailsAdapter;

    @OnClick({R.id.show_details_hot, R.id.show_deltails_doing, R.id.show_deltails_todo})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_details_hot:
                //热门电影
                loadDataHot();
                showDetailsHot.setChecked(true);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(false);
                break;
            case R.id.show_deltails_doing:
                //正在热映
                loadDataDoing();
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(true);
                showDeltailsTodo.setChecked(false);
                break;
            case R.id.show_deltails_todo:
                //即将上映
                loadDataTodo();
                showDetailsHot.setChecked(false);
                showDeltailsDoing.setChecked(false);
                showDeltailsTodo.setChecked(true);
                break;
            default:
                break;
        }
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
        mPage = 1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        show_details_recycle.setLayoutManager(layoutManager);
        showDetailsAdapter = new ShowDetailsAdapter(this);
        show_details_recycle.setAdapter(showDetailsAdapter);

        show_details_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage = 1;
                //热门电影
                loadDataHot();
                //正在热映
                loadDataDoing();
                //即将上映
                loadDataTodo();
            }

            @Override
            public void onLoadMore() {
                //热门电影
                loadDataHot();
                //正在热映
                loadDataDoing();
                //即将上映
                loadDataTodo();
            }
        });

    }

    //热门电影
    private void loadDataHot() {
        startRequestGet(String.format(Apis.URL_HOTMOVIE, mPage, TYPE_COUNT), HomeBannerBean.class);
    }

    //正在热映
    private void loadDataDoing() {
        startRequestGet(String.format(Apis.URL_DOINGMOVIE, mPage, TYPE_COUNT), HomeBannerBean.class);
    }

    //即将上映
    private void loadDataTodo() {
        startRequestGet(String.format(Apis.URL_BANNER, mPage, TYPE_COUNT), HomeBannerBean.class);
    }

    @Override
    protected void successed(Object data) {
        if (data instanceof HomeBannerBean) {
            HomeBannerBean homeBannerBean = (HomeBannerBean) data;
            if (homeBannerBean.getStatus().equals("0000")) {
                ToastUtils.toast(homeBannerBean.getMessage());
                if (mPage == 1) {
                    showDetailsAdapter.setDatas(homeBannerBean.getResult());
                }else{
                    showDetailsAdapter.addDatas(homeBannerBean.getResult());
                }
                mPage++;
                show_details_recycle.loadMoreComplete();
                show_details_recycle.refreshComplete();
            } else {
                ToastUtils.toast(homeBannerBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

}

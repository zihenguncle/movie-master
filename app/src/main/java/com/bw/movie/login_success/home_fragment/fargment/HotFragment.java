package com.bw.movie.login_success.home_fragment.fargment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.home_fragment.adapter.ShowDetailsAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.FollowBean;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HotFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.hot_fragment_recycle)
    XRecyclerView hot_fargment_recycle;
    private int mPage;
    private static final int TYPE_COUNT = 10;
    private ShowDetailsAdapter showDetailsAdapter;

    @Override
    public void onClick(View v) {

    }

    @Override
    protected int getViewById() {
        return R.layout.hot_fragment;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        hot_fargment_recycle.setLayoutManager(layoutManager);

        showDetailsAdapter = new ShowDetailsAdapter(getContext());
        hot_fargment_recycle.setAdapter(showDetailsAdapter);

        showDetailsAdapter.setOnCallBack(new ShowDetailsAdapter.CallBack() {
            @Override
            public void getInformation(int id, int followMovie, int position) {
                //取消关注
                if(followMovie==1){
                    cancelMovie(id);
                    showDetailsAdapter.updateChoose(position);
                }else {
                    //关注
                    loveMovie(id);
                    showDetailsAdapter.updateSelect(position);
                }
            }
        });
        hot_fargment_recycle.setPullRefreshEnabled(true);
        hot_fargment_recycle.setLoadingMoreEnabled(true);
        hot_fargment_recycle.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadDataHot(mPage);
            }

            @Override
            public void onLoadMore() {
                loadDataHot(mPage);
            }
        });
        loadDataHot(mPage);
    }
    //得到传值进行刷新
    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void refresh(MessageList messageList){
        if (messageList.getFlag().equals("refrersh")){
            mPage =1;
            loadDataHot(mPage);
        }
    }

    private void loveMovie(int id) {
        startRequestGet(String.format(Apis.URL_LOVEMOVIE,id),FollowBean.class);
    }


    private void cancelMovie(int id) {
        startRequestGet(String.format(Apis.URLNOLOVEMOVIE,id), FollowBean.class);
    }

    //热门电影
    private void loadDataHot(int mPage) {
        startRequestGet(String.format(Apis.URL_HOTMOVIE, mPage, TYPE_COUNT), HomeBannerBean.class);
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
    }

    @Override
    protected void successed(Object data) {
        if (data instanceof HomeBannerBean) {
            HomeBannerBean homeBannerBean = (HomeBannerBean) data;
            if (homeBannerBean.getStatus().equals("0000")) {
                if (mPage == 1) {
                    showDetailsAdapter.setDatas(homeBannerBean.getResult());
                } else {
                    showDetailsAdapter.addDatas(homeBannerBean.getResult());
                }
                mPage++;
                hot_fargment_recycle.refreshComplete();
                hot_fargment_recycle.loadMoreComplete();
            } else {
                ToastUtils.toast(homeBannerBean.getMessage());
            }
        }
        if(data instanceof FollowBean){
            FollowBean bean= (FollowBean) data;
            if(bean.getStatus().equals("0000")){
                ToastUtils.toast(bean.getMessage());


            }else {
                ToastUtils.toast(bean.getMessage());
            }
        }
    }
    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().post(new MessageList("refrersh",null));
        EventBus.getDefault().unregister(this);


    }
}

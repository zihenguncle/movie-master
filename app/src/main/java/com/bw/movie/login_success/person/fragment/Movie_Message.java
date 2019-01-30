package com.bw.movie.login_success.person.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.person.personal_adapter.VideoAdapter;
import com.bw.movie.login_success.person.personal_bean.VideInformationBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Movie_Message extends BaseFragment {

    @BindView(com.bw.movie.R.id.personal_message)
    XRecyclerView perosnal_message;
    private int mPage;
    private VideoAdapter videoAdapter;
    public static final int TYPA_CIMEAMA_COUNT=10;

    @Override
    protected int getViewById() {
        return R.layout.movie_message;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        perosnal_message.setLayoutManager(layoutManager);
        videoAdapter = new VideoAdapter(getContext());
        perosnal_message.setAdapter(videoAdapter);
        perosnal_message.setLoadingMoreEnabled(true);
        perosnal_message.setPullRefreshEnabled(true);
        perosnal_message.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh(){
                mPage=1;
                loadsData();
            }

            @Override
            public void onLoadMore() {

                loadsData();
            }
        });
        loadsData();
    }

    private void loadsData(){
        startRequestGet(String.format(Apis.URL_VIDE_INFORMATION,mPage,TYPA_CIMEAMA_COUNT),VideInformationBean.class);
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof VideInformationBean){
            VideInformationBean videInformationBean= (VideInformationBean)data;
            if(videInformationBean.getStatus().equals("0000")){
                if (mPage == 1) {
                    videoAdapter.setDatas(videInformationBean.getResult());
                } else {
                    videoAdapter.addDatas(videInformationBean.getResult());
                }
                mPage++;
                perosnal_message.loadMoreComplete();
                perosnal_message.refreshComplete();
            } else{
                ToastUtils.toast(videInformationBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

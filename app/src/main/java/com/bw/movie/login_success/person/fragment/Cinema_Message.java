package com.bw.movie.login_success.person.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.RadioButton;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.person.personal_adapter.CimeamaAdapter;
import com.bw.movie.login_success.person.personal_bean.CimeamaBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Cinema_Message extends BaseFragment {
    private int mPage;
    @BindView(com.bw.movie.R.id.personal_message_titcket)
    XRecyclerView personalMessageTitcket;
    private CimeamaAdapter cimeamaAdapter;
    public static final int TYPA_CIMEAMA_COUNT=10;
    @Override
    protected int getViewById() {
        return R.layout.cimema_message;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        personalMessageTitcket.setLayoutManager(linearLayoutManager);

        cimeamaAdapter = new CimeamaAdapter(getContext());
        personalMessageTitcket.setAdapter(cimeamaAdapter);

        personalMessageTitcket.setLoadingMoreEnabled(true);
        personalMessageTitcket.setPullRefreshEnabled(true);
        personalMessageTitcket.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh(){
                mPage=1;
                loadData(mPage);
            }

            @Override
            public void onLoadMore() {
                loadData(mPage);
            }
        });
        loadData(mPage);
    }

    private void loadData(int mPage){
        startRequestGet(String.format(Apis.URL_CIMEAMA,mPage,TYPA_CIMEAMA_COUNT),CimeamaBean.class);
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void successed(Object data) {
        if (data instanceof CimeamaBean) {
            CimeamaBean cimeamaBean = (CimeamaBean) data;
            if (cimeamaBean.getStatus().equals("0000")) {
                if (mPage == 1) {
                    cimeamaAdapter.setDatas(cimeamaBean.getResult());
                } else {
                    cimeamaAdapter.addDatas(cimeamaBean.getResult());
                }
                mPage++;
                personalMessageTitcket.refreshComplete();
                personalMessageTitcket.loadMoreComplete();
            } else {
                ToastUtils.toast(cimeamaBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

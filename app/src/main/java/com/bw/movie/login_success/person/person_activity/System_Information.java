package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.personal_adapter.SystemAdapter;
import com.bw.movie.login_success.person.personal_bean.ReadCountBean;
import com.bw.movie.login_success.person.personal_bean.SystemInformationBean;
import com.bw.movie.login_success.person.personal_bean.UpdateReadBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class System_Information extends BaseActivity implements View.OnClickListener {
    @BindView(R.id.system_information)
    XRecyclerView system_information;

    private int mPage;
    @BindView(R.id.system_information_count)
    TextView system_information_count;
    private final int TYPE_COUNT=10;
    private SystemAdapter systemAdapter;
    private SystemInformationBean systemInformationBean;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.system_information;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        system_information.setLayoutManager(linearLayoutManager);

        systemAdapter = new SystemAdapter(this);
        system_information.setAdapter(systemAdapter);
        system_information.setLoadingMoreEnabled(true);
        system_information.setPullRefreshEnabled(true);
        system_information.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                mPage++;
                loadData();
            }
        });
        loadData();
        startRequestGet(Apis.URL_READ_COUNT,ReadCountBean.class);
        systemAdapter.setOnClickListeener(new SystemAdapter.OnClickListeener() {
            @Override
            public void onSuccess(String id, String position) {
                startRequestGet(String.format(Apis.URL_UPDATE_READ,id),UpdateReadBean.class);
            }
        });
    }

    private void loadData() {
        startRequestGet(String.format(Apis.URL_SELECT_INFORMATION,mPage,TYPE_COUNT),SystemInformationBean.class);
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof SystemInformationBean){
            systemInformationBean = (SystemInformationBean) data;
            if(systemInformationBean.getMessage().equals("无数据")){
                ToastUtils.toast(systemInformationBean.getMessage());
                system_information.loadMoreComplete();
                system_information.refreshComplete();
            }else if(systemInformationBean.getStatus().equals("0000")){
                    if(mPage==1){
                        if(systemInformationBean.getResult().size()==0){
                            ToastUtils.toast(systemInformationBean.getMessage());
                        }else {
                            systemAdapter.setData(systemInformationBean.getResult());
                        }
                    }else{
                        if(systemInformationBean.getResult().size()==0){
                            ToastUtils.toast(systemInformationBean.getMessage());
                        }else{
                            systemAdapter.addData(systemInformationBean.getResult());
                        }
                    }
                system_information.loadMoreComplete();
                system_information.refreshComplete();
            }else{
                ToastUtils.toast(systemInformationBean.getMessage());
            }
        }else if(data instanceof ReadCountBean){
            ReadCountBean readCountBean= (ReadCountBean) data;
            if(readCountBean.getStatus().equals("0000")){
                system_information_count.setText("系统消息 ("+readCountBean.getCount()+"条未读)");
            }else{
                ToastUtils.toast(readCountBean.getMessage());
            }
        }else if(data instanceof UpdateReadBean){
            UpdateReadBean updateReadBean= (UpdateReadBean) data;
            if(updateReadBean.getStatus().equals("0000")){
                initData();
                loadData();
            }else{
                ToastUtils.toast(updateReadBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @OnClick(R.id.system_information_back)
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.system_information_back:
                finish();
                break;
                default:break;
        }
    }
}

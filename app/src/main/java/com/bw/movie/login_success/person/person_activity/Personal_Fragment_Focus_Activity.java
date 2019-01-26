package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.personal_adapter.CimeamaAdapter;
import com.bw.movie.login_success.person.personal_adapter.VideoAdapter;
import com.bw.movie.login_success.person.personal_bean.CimeamaBean;
import com.bw.movie.login_success.person.personal_bean.VideInformationBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Fragment_Focus_Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.message_ticket)
    Button messageTicket;
    @BindView(R.id.message_movie)
    Button messageMovie;
    @BindView(R.id.personal_message_titcket)
    XRecyclerView personalMessageTitcket;

    public static final int TYPA_CIMEAMA_COUNT=10;
    public int mPage;
    private CimeamaAdapter cimeamaAdapter;
    private VideoAdapter videoAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById(){
        return R.layout.personal_focus;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        personalMessageTitcket.setLayoutManager(layoutManager);

        cimeamaAdapter = new CimeamaAdapter(this);
        personalMessageTitcket.setAdapter(cimeamaAdapter);

        videoAdapter = new VideoAdapter(this);
        personalMessageTitcket.setAdapter(videoAdapter);

        personalMessageTitcket.setLoadingMoreEnabled(true);
        personalMessageTitcket.setPullRefreshEnabled(true);
        personalMessageTitcket.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData();
                loadsData();
            }

            @Override
            public void onLoadMore() {
                loadData();
                loadsData();
            }
        });
        loadsData();
    }

    private void loadData(){
        startRequestGet(String.format(Apis.URL_CIMEAMA,mPage,TYPA_CIMEAMA_COUNT),CimeamaBean.class);
    }

    private void loadsData(){
        startRequestGet(String.format(Apis.URL_VIDE_INFORMATION,mPage,TYPA_CIMEAMA_COUNT),VideInformationBean.class);
    }
    @Override
    protected void successed(Object data){
        if(data instanceof CimeamaBean){
            CimeamaBean cimeamaBean=(CimeamaBean) data;
            if(cimeamaBean.getStatus().equals("0000")){
                TextUtils.isEmpty(cimeamaBean.getMessage());
                if(mPage==1){
                    cimeamaAdapter.setDatas(cimeamaBean.getResult());
                }else{
                    cimeamaAdapter.addDatas(cimeamaBean.getResult());
                }
                mPage++;
                personalMessageTitcket.refreshComplete();
                personalMessageTitcket.loadMoreComplete();
            }else{
                TextUtils.isEmpty(cimeamaBean.getMessage());
            }
        }else if(data instanceof VideInformationBean){
            VideInformationBean videInformationBean= (VideInformationBean)data;
            if(videInformationBean.getStatus().equals("0000")){
                TextUtils.isEmpty(videInformationBean.getMessage());
                if(mPage==1){
                    videoAdapter.setDatas(videInformationBean.getResult());
                }else{
                    videoAdapter.addDatas(videInformationBean.getResult());
                }
                mPage++;
                personalMessageTitcket.loadMoreComplete();
                personalMessageTitcket.refreshComplete();
            }else{
                TextUtils.isEmpty(videInformationBean.getMessage());
            }
        }
    }

    @OnClick({R.id.message_ticket,R.id.message_movie})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.message_ticket:
                loadsData();

                break;
            case R.id.message_movie:
                loadData();
                break;
            default:break;
        }
    }
    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

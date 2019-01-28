package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

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

    @BindView(com.bw.movie.R.id.message_ticket)
    RadioButton messageTicket;
    @BindView(com.bw.movie.R.id.message_movie)
    RadioButton messageMovie;
    @BindView(com.bw.movie.R.id.personal_message_titcket)
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
        return com.bw.movie.R.layout.personal_focus;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        personalMessageTitcket.setLayoutManager(layoutManager);

        messageMovie.setChecked(false);
        messageTicket.setChecked(true);

        videoAdapter = new VideoAdapter(this);
        personalMessageTitcket.setAdapter(videoAdapter);

        cimeamaAdapter = new CimeamaAdapter(this);
        personalMessageTitcket.setAdapter(cimeamaAdapter);

        personalMessageTitcket.setLoadingMoreEnabled(true);
        personalMessageTitcket.setPullRefreshEnabled(true);
        personalMessageTitcket.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                loadData(mPage);
                loadsData(mPage);
            }

            @Override
            public void onLoadMore() {
                loadData(mPage);
                loadsData(mPage);
            }
        });
        loadsData(mPage);
    }

    private void loadData(int mPage){
        startRequestGet(String.format(Apis.URL_CIMEAMA,mPage,TYPA_CIMEAMA_COUNT),CimeamaBean.class);
    }

    private void loadsData(int mPage){
        startRequestGet(String.format(Apis.URL_VIDE_INFORMATION,mPage,TYPA_CIMEAMA_COUNT),VideInformationBean.class);
    }
    @Override
    protected void successed(Object data){
        if(data instanceof CimeamaBean){
            CimeamaBean cimeamaBean=(CimeamaBean) data;

            if(cimeamaBean.getStatus().equals("0000")){
                if(mPage==1){
                    cimeamaAdapter.setDatas(cimeamaBean.getResult());
                }else{
                    cimeamaAdapter.addDatas(cimeamaBean.getResult());
                }

                mPage++;
                personalMessageTitcket.refreshComplete();
                personalMessageTitcket.loadMoreComplete();

            }else{
                ToastUtils.toast(cimeamaBean.getMessage());
            }
        }else if(data instanceof VideInformationBean){
            VideInformationBean videInformationBean= (VideInformationBean)data;

            if(videInformationBean.getStatus().equals("0000")){
                if(mPage==1){
                    videoAdapter.setDatas(videInformationBean.getResult());
                }else{
                    videoAdapter.addDatas(videInformationBean.getResult());
                }
                mPage++;
                personalMessageTitcket.loadMoreComplete();
                personalMessageTitcket.refreshComplete();
            }else{
                ToastUtils.toast(videInformationBean.getMessage());
            }
        }
    }

    @OnClick({com.bw.movie.R.id.message_ticket, com.bw.movie.R.id.message_movie,})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case com.bw.movie.R.id.personal_focus_back:
                finish();
                break;
            case com.bw.movie.R.id.message_ticket:
                mPage=1;
                loadsData(mPage);
                messageMovie.setChecked(false);
                messageTicket.setChecked(true);
                break;
            case com.bw.movie.R.id.message_movie:
                mPage=1;
               loadData(mPage);
                messageTicket.setChecked(false);
                messageMovie.setChecked(true);
                break;
            default:break;
        }
    }



    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.personal_adapter.PerformAdapter;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Message_TicketActivity extends BaseActivity implements View.OnClickListener {

    @BindView(com.bw.movie.R.id.personal_ticket_pay)
    RadioButton personalTicketPay;
    @BindView(com.bw.movie.R.id.personal_perform)
    RadioButton personalPerform;
    @BindView(com.bw.movie.R.id.personal_recycle_show)
    XRecyclerView personalRecycleShow;
    private int mPage;
    private PerformAdapter performAdapter;
    private int TYPE_COMLETE=2;
    private int TYPE_OBLIGATION=1;
    private int TYPE_COUNT=5;
    private PopupWindow popupWindow;

    private double sum_num=0.0;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return com.bw.movie.R.layout.personal_message_ticket_activity;
    }

    @Override
    protected void initData() {
        mPage=1;
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(OrientationHelper.VERTICAL);
        personalRecycleShow.setLayoutManager(layoutManager);

        personalPerform.setChecked(false);
        personalTicketPay.setChecked(true);

        personalRecycleShow.setLoadingMoreEnabled(true);
        personalRecycleShow.setPullRefreshEnabled(true);
        personalRecycleShow.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
              loadData(mPage,TYPE_COUNT,TYPE_OBLIGATION);
               loadsData(mPage,TYPE_COUNT,TYPE_COMLETE);
            }

            @Override
            public void onLoadMore() {
               loadData(mPage,TYPE_COUNT,TYPE_OBLIGATION);
              loadsData(mPage,TYPE_COUNT,TYPE_COMLETE);
            }

        });
       loadsData(mPage,TYPE_COUNT,TYPE_COMLETE);


     /*   performAdapter.setOnClickListener(new PerformAdapter.OnClicksListener() {
            @Override
            public void onSuccess(String orderId,String price,int num) {
                View view=View.inflate(Personal_Message_TicketActivity.this, com.bw.movie.R.layout.popwindow_pay,null);
                TextView weChat = view.findViewById(com.bw.movie.R.id.weChat_pay);
                TextView alipay = view.findViewById(com.bw.movie.R.id.text_alipay);
                TextView sum_price = view.findViewById(com.bw.movie.R.id.textView_sum_price);
                popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

                popupWindow.showAtLocation(view,
                        Gravity.BOTTOM, 0, 0);
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
                popupWindow.setTouchable(true);
                *//*price*num;
                sum_price.setText("微信支付+"++"元");*//*

                weChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });

                alipay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });


            }
        });*/
    }

    private void loadsData(int mPage, int type_count, int type_comlete) {
        startRequestGet(String.format(Apis.URL_TICKET_RECORD,mPage,type_count,type_comlete),TicketInformationBean.class);
    }

    private void loadData(int mPage, int type_count, int type_obligation) {
        startRequestGet(String.format(Apis.URL_TICKET_RECORD,mPage,type_count,type_obligation),TicketInformationBean.class);
    }


    @Override
    protected void successed(Object data) {
        if(data instanceof TicketInformationBean){
            TicketInformationBean ticketInformationBean= (TicketInformationBean) data;
            if(ticketInformationBean.getStatus().equals("0000")){
                ToastUtils.toast(ticketInformationBean.getMessage());
                performAdapter = new PerformAdapter(this);
                personalRecycleShow.setAdapter(performAdapter);
                if(mPage==1){
                    performAdapter.setDatas(ticketInformationBean.getResult());
                }else{
                    performAdapter.addDatas(ticketInformationBean.getResult());
                }
                mPage++;
                personalRecycleShow.refreshComplete();
                personalRecycleShow.loadMoreComplete();
            }else{
                ToastUtils.toast(ticketInformationBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }


    @OnClick({com.bw.movie.R.id.personal_ticket_pay, com.bw.movie.R.id.personal_perform})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case com.bw.movie.R.id.personal_ticket_pay:
                mPage=1;
               loadData(mPage,TYPE_COUNT,TYPE_OBLIGATION);
               personalTicketPay.setChecked(true);
               personalPerform.setChecked(false);
                break;
            case com.bw.movie.R.id.personal_perform:
                mPage=1;
               loadsData(mPage,TYPE_COUNT,TYPE_COMLETE);
               personalPerform.setChecked(true);
               personalTicketPay.setChecked(false);
                break;
                default:break;
        }
    }
}

package com.bw.movie.login_success.home_fragment.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.adapter.BuyTicketAdapter;
import com.bw.movie.login_success.home_fragment.bean.BuyTicketBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyTicketActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.buy_ticket_recycle)
    RecyclerView buy_ticket_recycle;
    private BuyTicketAdapter buyTicketAdapter;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.buy_ticket_activity;
    }

    @Override
    protected void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        buy_ticket_recycle.setLayoutManager(linearLayoutManager);
        buyTicketAdapter = new BuyTicketAdapter(this);
        buy_ticket_recycle.setAdapter(buyTicketAdapter);

        startRequestGet(String.format(Apis.URL_TICKET_LIST,"12"),BuyTicketBean.class);
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof BuyTicketBean){
            BuyTicketBean buyTicketBean= (BuyTicketBean) data;
            if(buyTicketBean.getStatus().equals("0000")){
                ToastUtils.toast(buyTicketBean.getMessage());
                buyTicketAdapter.setDatas(buyTicketBean.getResult());
            }else{
                ToastUtils.toast(buyTicketBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @OnClick(R.id.image_buy_ticket_back)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_buy_ticket_back:
                finish();
                break;
                default:break;
        }
    }
}

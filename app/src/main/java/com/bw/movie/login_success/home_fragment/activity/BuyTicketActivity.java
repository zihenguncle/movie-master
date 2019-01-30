package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.adapter.BuyTicketAdapter;
import com.bw.movie.login_success.home_fragment.bean.BuyTicketBean;
import com.bw.movie.login_success.home_fragment.bean.SchedBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.FollowBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BuyTicketActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.buy_ticket_recycle)
    RecyclerView buy_ticket_recycle;
    @BindView(R.id.buy_ticket_file_name)
    TextView buy_ticket_file_name;
    private BuyTicketAdapter buyTicketAdapter;
    private BuyTicketBean buyTicketBean;

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
        Intent intent = getIntent();
        final int movieId = intent.getIntExtra("movieId", 0);
        String movieName = intent.getStringExtra("movieName");
        buy_ticket_file_name.setText(movieName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        buy_ticket_recycle.setLayoutManager(linearLayoutManager);
        buyTicketAdapter = new BuyTicketAdapter(this);
        buy_ticket_recycle.setAdapter(buyTicketAdapter);

        startRequestGet(String.format(Apis.URL_TICKET_LIST,movieId),BuyTicketBean.class);
        buyTicketAdapter.setOnClickLisener(new BuyTicketAdapter.OnClickLisener() {
            @Override
            public void onSuccess(int id, int followCinema, int position) {
                if(followCinema==1){
                    //取消关注
                    cancelCollection(id);
                    buyTicketAdapter.updateFalse(position);
                }else {
                    //关注
                    collection(id);
                    buyTicketAdapter.updateTrue(position);
                }
            }
        });
        buyTicketAdapter.setOnClickLisener(new BuyTicketAdapter.OnsClick() {
            @Override
            public void onCinema(int id, int position,String name,String address) {
                Intent intent1 = new Intent(BuyTicketActivity.this, SchedActivity.class);
                intent1.putExtra("movieId",movieId);
                intent1.putExtra("cinemasId",id);
                intent1.putExtra("name",name);
                intent1.putExtra("address",address);
                startActivity(intent1);
            }
        });
    }

    private void collection(int id) {
        startRequestGet(String.format(Apis.URL_FOLLOW_CINEMA,id), FollowBean.class);
    }

    private void cancelCollection(int id) {
        startRequestGet(String.format(Apis.URL_CANCEL_FOLLOW_CINEMA,id),FollowBean.class);
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof BuyTicketBean){
            buyTicketBean = (BuyTicketBean) data;
            if(buyTicketBean.getStatus().equals("0000")){
                ToastUtils.toast(buyTicketBean.getMessage());
                buyTicketAdapter.setDatas(buyTicketBean.getResult());
            }else{
                ToastUtils.toast(buyTicketBean.getMessage());
            }
        }else if(data instanceof FollowBean){
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

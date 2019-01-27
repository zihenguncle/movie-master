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

public class Personal_Message_TicketActivity extends BaseActivity {


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.personal_message_ticket_activity;
    }

    @Override
    protected void initData() {

    }



    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }




}

package com.bw.movie.login_success.person.person_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Message_Activity extends BaseActivity {
    @BindView(R.id.personal_reset_pwd)
    RelativeLayout personalResetPwd;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @OnClick(R.id.personal_reset_pwd)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal_reset_pwd:
                Intent intent = new Intent(this, Personal_Reset_Pwd_Activity.class);
                startActivity(intent);
                break;
                default:break;
        }
    }
    @Override
    protected int getViewById() {
        return R.layout.personal_message_activity;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}

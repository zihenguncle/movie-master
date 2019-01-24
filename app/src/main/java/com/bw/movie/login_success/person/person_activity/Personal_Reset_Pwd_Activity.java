package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Personal_Reset_Pwd_Activity extends BaseActivity {


    @BindView(R.id.personal_current)
    TextView personalCurrent;
    @BindView(R.id.personal_current_textView)
    TextView personalCurrentTextView;
    @BindView(R.id.personal_reset_view1)
    View personalResetView1;
    @BindView(R.id.personal_reset_again_pwd)
    TextView personalResetAgainPwd;
    @BindView(R.id.personal_reset_pwd_textView)
    TextView personalResetPwdTextView;
    @BindView(R.id.personal_reset_view2)
    View personalResetView2;
    @BindView(R.id.personal_enter_again)
    TextView personalEnterAgain;
    @BindView(R.id.personal_enter_again_textView)
    TextView personalEnterAgainTextView;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.personal_reset_pwd;
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

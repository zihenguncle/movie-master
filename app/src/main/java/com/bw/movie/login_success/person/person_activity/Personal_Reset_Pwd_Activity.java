package com.bw.movie.login_success.person.person_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.person.personal_bean.UpdatePwdBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.RegexUtils;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.bw.movie.tools.md5.EncryptUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Reset_Pwd_Activity extends BaseActivity implements View.OnClickListener{


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
    private String pwd_again;
    @BindView(R.id.relative_layout)
    RelativeLayout relativeLayout;

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
        final String oldPwd = SharedPreferencesUtils.getParam(this, "OldPwd", "0****0").toString();
        String decrypt = EncryptUtil.decrypt(oldPwd);
        String maskNumber = decrypt.substring(0,1)+"****"+decrypt.substring(5,decrypt.length());
        personalCurrentTextView.setText(maskNumber);
        final String s = personalResetPwdTextView.getText().toString();
        pwd_again = personalEnterAgainTextView.getText().toString();

        if(TextUtils.isEmpty(s)||!RegexUtils.isPassword(s)||s.length()<6){
            ToastUtils.toast("重置密码不能为空,长度为6位");
        } else if(TextUtils.isEmpty(pwd_again)||!RegexUtils.isPassword(pwd_again)||s.length()<6) {
            ToastUtils.toast("请再次输入密码不能为空,长度为6位");
        }else {
            relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    relativeLayout.setFocusable(true);
                    relativeLayout.setFocusableInTouchMode(true);
                    relativeLayout.requestFocus();
                    if (s.equals(pwd_again)) {
                        String pwd = EncryptUtil.encrypt(s);
                        String encrypt = EncryptUtil.encrypt(pwd_again);
                        Map<String, String> params = new HashMap<>();
                        params.put("oldPwd", oldPwd);
                        params.put("newPwd", pwd);
                        params.put("newPwd2", encrypt);
                        startRequestPost(Apis.URL_UPDATE_PASSWORD, params, UpdatePwdBean.class);
                    } else {
                        ToastUtils.toast("请重新输入");
                    }
                    return false;
                }
            });
        }
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof UpdatePwdBean){
            UpdatePwdBean updatePwdBean= (UpdatePwdBean) data;
            if (updatePwdBean.getStatus().equals("0000")){
                ToastUtils.toast(updatePwdBean.getMessage());
               /* Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();*/
            }else{
                ToastUtils.toast(updatePwdBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
    @OnClick(R.id.image_View_Back)
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.image_View_Back:
                finish();
                break;
            default:break;
        }
    }



}

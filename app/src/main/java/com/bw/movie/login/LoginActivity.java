package com.bw.movie.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.register.RegisterActivity;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.bw.movie.tools.md5.EncryptUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 登录页面
* zhangjing
* 20190124
* */

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_phone)
    EditText editText_phone;
    @BindView(R.id.login_pass)
    EditText editText_pass;
    @BindView(R.id.but_login)
    Button button_login;
    @BindView(R.id.text_register)
    TextView textView_register;
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_login;
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.but_login,R.id.text_register})
    public void getViewById(View view){
        switch (view.getId()){
            case R.id.but_login:
                login();
                break;
            case R.id.text_register:
                //跳转到注册页面
                intentRegisterPage();
                break;
        }
    }

    private void intentRegisterPage() {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        String phone = editText_phone.getText().toString();
        String pass = editText_pass.getText().toString();
        String pwd = EncryptUtil.encrypt(pass);
       // ToastUtils.toast(pwd);
        Map<String,String> map=new HashMap<>();
        map.put("phone",phone);
        map.put("pwd",pwd);
        startRequestPost(Apis.URL_LOGIN,map,LoginBean.class);
    }


    @Override
    protected void successed(Object data) {
          LoginBean bean= (LoginBean) data;
         if(bean.getStatus().equals("0000")){
             ToastUtils.toast(bean.getMessage());
            int userId = bean.getResult().getUserId();
             String sessionId = bean.getResult().getSessionId();
             SharedPreferencesUtils.setParam(this,"userId",userId+"");
             SharedPreferencesUtils.setParam(this,"sessionId",sessionId);
         }else {
             ToastUtils.toast(bean.getMessage());
         }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

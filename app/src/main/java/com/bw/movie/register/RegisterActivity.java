package com.bw.movie.register;


import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.RegexUtils;
import com.bw.movie.tools.ToastUtils;
import com.bw.movie.tools.md5.EncryptUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
/*
* 注册页面，注册成功跳转到成功的页面
* zhangjing
* 20190124
* */
public class RegisterActivity extends BaseActivity {

    @BindView(R.id.reg_name)
    EditText editText_name;
    @BindView(R.id.reg_sex)
    EditText editText_sex;
    @BindView(R.id.reg_birth_date)
    EditText editText_birth_date;
    @BindView(R.id.reg_phone)
    EditText editText_phone;
    @BindView(R.id.reg_email)
    EditText editText_email;
    @BindView(R.id.reg_pass)
    EditText editText_pass;
    @BindView(R.id.but_register)
    Button button_register;
    private int mYear,mMonth,mDay;

    @Override
    protected void initView(Bundle savedInstanceState) {
     //绑定
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_register;
    }

    @Override
    protected void initData() {

    }
    @OnClick({R.id.but_register,R.id.reg_birth_date})
    public void getViewById(View view){
        switch (view.getId()){
            case R.id.but_register:
                register();
                break;
            case R.id.reg_birth_date:
                getDate();
                break;
        }
    }

    private void getDate() {
        new DatePickerDialog(RegisterActivity.this,
                R.style.MyDatePickerDialogTheme,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        mYear = year;
                        mMonth = month;
                        mDay = dayOfMonth;
                        editText_birth_date.setText(mYear + "-" + (mMonth + 1) + "-" + mDay);
                    }
                },
                mYear, mMonth, mDay).show();


    }

    private void register()  {
        String name = editText_name.getText().toString();
        String sex = editText_sex.getText().toString();
        String phone = editText_phone.getText().toString();
        String email = editText_email.getText().toString();
        String pass = editText_pass.getText().toString();
        String date = editText_birth_date.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastUtils.toast("昵称不能为空");
        }else if(TextUtils.isEmpty(phone)){
            ToastUtils.toast("手机号不能为空");
        }else if(!RegexUtils.isMobile(phone)){
            ToastUtils.toast("手机号格式不对");
        }else if(TextUtils.isEmpty(email)){
            ToastUtils.toast("邮箱不能为空");
        }else if(!RegexUtils.isEmail(email)){
            ToastUtils.toast("邮箱格式不对");
        }else if(TextUtils.isEmpty(pass)){
            ToastUtils.toast("登录密码不能为空");
        }else if(!RegexUtils.isPassword(pass)){
            ToastUtils.toast("登录密码6-20位");
        }else {
          String pwd = EncryptUtil.encrypt(pass);
            Map<String,String> map=new HashMap<>();
            map.put("nickName",name);
            map.put("phone",phone);
            map.put("pwd",pwd);
            map.put("pwd2",pwd);
            map.put("sex",1+"");
            map.put("birthday",date);
            map.put("email",email);
           startRequestPost(Apis.URL_REGISTER,map,RegisterBean.class);
        }

    }

    @Override
    protected void successed(Object data) {
        RegisterBean bean= (RegisterBean) data;
            if(bean.getStatus().equals("0000")){
                ToastUtils.toast(bean.getMessage());
                Intent intent=new Intent(RegisterActivity.this, Login_Success_Activity.class);
                startActivity(intent);
                finish();
            }else {
                ToastUtils.toast(bean.getMessage());
            }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

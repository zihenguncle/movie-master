package com.bw.movie.register;


import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.RegexUtils;
import com.bw.movie.tools.ToastUtils;
import com.bw.movie.tools.md5.EncryptUtil;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

    private void register() {
        String name = editText_name.getText().toString();
        String sex = editText_sex.getText().toString();
        String phone = editText_phone.getText().toString();
        String email = editText_email.getText().toString();
        String pass = editText_pass.getText().toString();
        String date = editText_birth_date.getText().toString();
        if(TextUtils.isEmpty(name)){
            ToastUtils.toast("昵称不能为空");
        }else if(!RegexUtils.isUsername(name)){
            ToastUtils.toast("昵称格式不对");
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
            ToastUtils.toast("登录密码格式不对");
        }else {
           /* String pwd = EncryptUtil.encrypt(pass);
            Map<String,String> map=new HashMap<>();
            map.put("nickName",name);
            map.put("phone",phone);
            map.put("pwd",pwd);
            map.put("pwd2",pwd);
            map.put("sex",1+"");
            map.put("birthday",date);
            map.put("imei",name);
            map.put("ua",name);
            map.put("screenSize",name);
            map.put("os",phone);
            map.put("email",email);
            startRequestPost(Apis.URL_REGISTER,map,);*/
        }

    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}

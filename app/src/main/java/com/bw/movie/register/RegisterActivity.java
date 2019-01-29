package com.bw.movie.register;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Window;
import android.view.WindowManager;
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
/**
 * date:2018/1/24
 * author:zhangjing
 * function:注册页面，注册成功跳转到成功的页面
 */

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
    private int gender;

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
    @OnClick({R.id.but_register,R.id.reg_birth_date,R.id.reg_sex})
    public void getViewById(View view){
        switch (view.getId()){
            case R.id.but_register:
                register();
                break;
            case R.id.reg_birth_date:
                getDate();//得到日期
                break;
            case R.id.reg_sex:
                getSex();//得到性别
                break;
        }
    }
    String[] sexArry = new String[]{ "女", "男"};// 性别选择
    private void getSex() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setTitle("性别");

        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
                // showToast(which+"");
               editText_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示



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
          if(sex.equals("男")){
              gender=1;
          }else if(sex.equals("女")){
              gender=2;
          }else {
              ToastUtils.toast("请输入正确的性别");
          }
          ToastUtils.toast(gender+"");

        if(TextUtils.isEmpty(name)){
            ToastUtils.toast("昵称不能为空");
        } else if(TextUtils.isEmpty(phone)){
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
            map.put("sex",gender+"");
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

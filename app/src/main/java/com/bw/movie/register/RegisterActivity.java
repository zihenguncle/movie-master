package com.bw.movie.register;


import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectChangeListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.bw.movie.R;
import com.bw.movie.application.MyApplication;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.LoginBean;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.login_success.person.personal_bean.TokenBean;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.RegexUtils;
import com.bw.movie.tools.ToastUtils;
import com.bw.movie.tools.md5.EncryptUtil;
import com.tencent.android.tpush.XGIOperateCallback;
import com.tencent.android.tpush.XGPushConfig;
import com.tencent.android.tpush.XGPushManager;
import com.xw.repo.XEditText;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    XEditText editText_name;
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
    private int gender;
    private TimePickerView pvTime;
    private String phone;
    private String pwd;
    private String token;

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
        //关闭软键盘
        editText_birth_date.setInputType(InputType.TYPE_NULL);
        editText_sex.setInputType(InputType.TYPE_NULL);

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

    private void getDate() {

        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();
        //正确设置方式 原因：注意事项有说明
        startDate.set(1980,0,1);
        endDate.set(2019,11,1);

        pvTime = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
               editText_birth_date.setText(getTime(date));
            }
        })
                .setType(new boolean[]{true, true, true, false, false, false})// 默认全部显示
                .setCancelText("取消")//取消按钮文字
                .setSubmitText("确定")//确认按钮文字
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择日期")//标题文字
                .setOutSideCancelable(true)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.WHITE)//标题文字颜色
                .setSubmitColor(Color.WHITE)//确定按钮文字颜色
                .setCancelColor(Color.WHITE)//取消按钮文字颜色
                .setTitleBgColor(this.getResources().getColor(R.color.colorYellow))//标题背景颜色 Night mode
                .setBgColor(this.getResources().getColor(R.color.colorWhite))//滚轮背景颜色 Night mode
                .setRangDate(startDate,selectedDate)//起始终止年月日设定
                .setLabel("年","月","日","时","分","秒")//默认设置为年月日时分秒
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .isDialog(true)//是否显示为对话框样式
                .build();
        pvTime.show();
    }

    private String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    String[] sexArry = new String[]{ "女", "男"};// 性别选择
    private void getSex() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);// 自定义对话框
        builder.setTitle("性别");
        builder.setSingleChoiceItems(sexArry, 0, new DialogInterface.OnClickListener() {// 2默认的选中
            @Override
            public void onClick(DialogInterface dialog, int which) {// which是被选中的位置
               editText_sex.setText(sexArry[which]);
                dialog.dismiss();// 随便点击一个item消失对话框，不用点击确认取消
            }
        });
        builder.show();// 让弹出框显示
    }
    private void register()  {
        String name = editText_name.getText().toString();
        String sex = editText_sex.getText().toString();
        phone = editText_phone.getText().toString();
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
            ToastUtils.toast("登录密码6位");
        }else {
            pwd = EncryptUtil.encrypt(pass);
            Map<String,String> map=new HashMap<>();
            map.put("nickName",name);
            map.put("phone", phone);
            map.put("pwd", pwd);
            map.put("pwd2", pwd);
            map.put("sex",gender+"");
            map.put("birthday",date);
            map.put("email",email);
           startRequestPost(Apis.URL_REGISTER,map,RegisterBean.class);
        }

    }

    @Override
    protected void successed(Object data) {

        if(data instanceof RegisterBean) {
            RegisterBean bean = (RegisterBean) data;
            if (bean.getStatus().equals("0000")) {
                ToastUtils.toast(bean.getMessage());
                Map<String, String> map = new HashMap<>();
                map.put("phone", phone);
                map.put("pwd", pwd);
                startRequestPost(Apis.URL_LOGIN, map, LoginBean.class);

            } else {
                ToastUtils.toast(bean.getMessage());
            }
        }else if(data instanceof LoginBean){
            LoginBean loginBean= (LoginBean) data;
            if(loginBean.getStatus().equals("0000")){
                String token = XGPushConfig.getToken(this);
                Map<String, String> map = new HashMap<>();
                map.put("token", token);
                map.put("os", 1 + "");
                startRequestPost(Apis.URL_TOKEN, map, TokenBean.class);
                Intent intent = new Intent(RegisterActivity.this, Login_Success_Activity.class);
                startActivity(intent);
                finish();
            }else{
               ToastUtils.toast(loginBean.getMessage());
            }
        } else if(data instanceof TokenBean){
            TokenBean tokenBean= (TokenBean) data;
            ToastUtils.toast(tokenBean.getMessage());
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

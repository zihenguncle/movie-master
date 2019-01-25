package com.bw.movie.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.register.RegisterActivity;
import com.bw.movie.tools.RegexUtils;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.bw.movie.tools.md5.EncryptUtil;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbiz.AddCardToWXCardPackage;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
/*
* 登录页面,记住密码，自动登录
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
    @BindView(R.id.checkbox_remember)
    CheckBox checkBox_remember;
    @BindView(R.id.checkbox_auto_login)
    CheckBox checkBox_auto_login;
    private String pwd;
    private String phone;
    private String pass;
    @BindView(R.id.image_login_eye)
    ImageView imageView_login_eye;
    @BindView(R.id.weixin)
    ImageView weixin;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private static final String APP_ID = "wxb3852e6a6b7d9516";
    private String code;


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);


    }


    @Override
    protected int getViewById() {
        return R.layout.activity_login;
    }


    @Override
    protected void initData() {
     //得到值
        boolean c_remember = (boolean) SharedPreferencesUtils.getParam(LoginActivity.this, "c_remember", false);
        if(c_remember){
            String phone = (String) SharedPreferencesUtils.getParam(LoginActivity.this, "phone", "");
            String pass = (String) SharedPreferencesUtils.getParam(LoginActivity.this, "pass", "");
            editText_phone.setText(phone);
            editText_pass.setText(pass);
            checkBox_remember.setChecked(true);
        }
        boolean c_auto_login = (boolean) SharedPreferencesUtils.getParam(LoginActivity.this, "c_auto_login", false);
        if(c_auto_login){
            Intent intent=new Intent(LoginActivity.this, Login_Success_Activity.class);
            startActivity(intent);
            finish();
        }
        checkBox_auto_login.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    checkBox_remember.setChecked(true);
                }
            }
        });
    }
    @OnClick({R.id.but_login,R.id.text_register,R.id.weixin})
    public void getViewById(View view){
        switch (view.getId()){
            case R.id.but_login:
                login();
                break;
            case R.id.text_register:
                //跳转到注册页面
                intentRegisterPage();
                break;
            case R.id.weixin:
                weixinLogin();
                break;
        }
    }

    private void weixinLogin() {
       // 第一步.请求code
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_sdk_demo_test";
        api.sendReq(req);
       /* Intent intent = getIntent();
        code = intent.getStringExtra("code");
        Map<String,String> map=new HashMap<>();
        map.put("code", code);
        startRequestPost(Apis.URL_WEIXIN_LOGIN,map,LoginBean.class);*/

    }

    //触摸事件
    @OnTouch({R.id.image_login_eye})
    public boolean onTouch(View view, MotionEvent event){
        switch (view.getId()){
            case R.id.image_login_eye:
                if(event.getAction()==MotionEvent.ACTION_DOWN){
                    //显示
                    editText_pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }else {
                    //隐藏
                    editText_pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }

                break;
        }
        return true;
    }

    private void intentRegisterPage() {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }

    private void login() {
        phone = editText_phone.getText().toString();
        pass = editText_pass.getText().toString();
        if(TextUtils.isEmpty(phone)){
            ToastUtils.toast("手机号不能为空");
        }else if(!RegexUtils.isMobile(phone)){
            ToastUtils.toast("手机号格式不对");
        }else if(TextUtils.isEmpty(pass)){
            ToastUtils.toast("登录密码不能为空");
        }else if(!RegexUtils.isPassword(pass)){
            ToastUtils.toast("登录密码6-20位");
        }else {
            pwd = EncryptUtil.encrypt(pass);
            // ToastUtils.toast(pwd);
            Map<String,String> map=new HashMap<>();
            map.put("phone", phone);
            map.put("pwd", pwd);
            startRequestPost(Apis.URL_LOGIN,map,LoginBean.class);
        }

    }


    @Override
    protected void successed(Object data) {
          LoginBean bean= (LoginBean) data;
         if(bean.getStatus().equals("0000")){
             ToastUtils.toast(bean.getMessage());
             //用SharedPreferences保存sessionId，userId
            int userId = bean.getResult().getUserId();
            // String phone = bean.getResult().getUserInfo().getPhone();
             String sessionId = bean.getResult().getSessionId();
             SharedPreferencesUtils.setParam(this,"userId",userId+"");
             SharedPreferencesUtils.setParam(this,"sessionId",sessionId);
             if(checkBox_remember.isChecked()){
                 SharedPreferencesUtils.setParam(LoginActivity.this,"phone",phone);
                 SharedPreferencesUtils.setParam(LoginActivity.this,"pass",pass);
                 SharedPreferencesUtils.setParam(LoginActivity.this,"c_remember",true);
             }
             if(checkBox_auto_login.isChecked()){
                 SharedPreferencesUtils.setParam(LoginActivity.this,"c_auto_login",true);
             }
             Intent intent=new Intent(LoginActivity.this, Login_Success_Activity.class);
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

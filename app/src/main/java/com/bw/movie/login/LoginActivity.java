package com.bw.movie.login;

import android.content.Intent;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.method.TransformationMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.application.MyApplication;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.eventbus.MessageList;
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

import org.greenrobot.eventbus.EventBus;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTouch;
/**
 * date:2018/1/24
 * author:zhangjing
 * function:登录页面,记住密码，自动登录
 */
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
    private String pwd;
    private String phone;
    private String pass;
    @BindView(R.id.image_login_eye)
    ImageView imageView_login_eye;
    @BindView(R.id.weixin)
    ImageView weixin;
    private boolean isHideFirst = true;// 输入框密码是否是隐藏的，默认为true
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
     //得到值
        boolean c_remember = (boolean) SharedPreferencesUtils.getParam(LoginActivity.this, "c_remember", false);
        if(c_remember){
            String phone = (String) SharedPreferencesUtils.getParam(LoginActivity.this, "phone", "");
            String pass = (String) SharedPreferencesUtils.getParam(LoginActivity.this, "pass", "");
            editText_phone.setText(phone);
            editText_pass.setText(pass);
            checkBox_remember.setChecked(true);
        }
    }
    @OnClick({R.id.but_login,R.id.text_register,R.id.weixin,R.id.image_login_eye})
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
            case R.id.image_login_eye:
                if (isHideFirst == true) {
                    //密文
                    HideReturnsTransformationMethod method1 = HideReturnsTransformationMethod.getInstance();
                    editText_pass.setTransformationMethod(method1);
                    isHideFirst = false;
                } else {
                    //密文
                    TransformationMethod method = PasswordTransformationMethod.getInstance();
                    editText_pass.setTransformationMethod(method);
                    isHideFirst = true;

                }
                // 光标的位置
                int index = editText_pass.getText().toString().length();
                editText_pass.setSelection(index);
                break;
        }
    }

    private void weixinLogin() {
        //微信登录
        if (!WeiXinUtil.success(this)) {
            ToastUtils.toast("请先安装应用");
        } else {
            //  验证
            SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            WeiXinUtil.reg(LoginActivity.this).sendReq(req);
        }
    }
    private void intentRegisterPage() {
        Intent intent=new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
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
            SharedPreferencesUtils.setParam(this,"OldPwd",pwd);
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
             //用SharedPreferences保存sessionId，userId
            int userId = bean.getResult().getUserId();
             Log.i("TAG","userId:"+userId+"");
            // String phone = bean.getResult().getUserInfo().getPhone();
             String sessionId = bean.getResult().getSessionId();
             Log.i("TAG","sessionId"+sessionId);
             SharedPreferencesUtils.setParam(this,"userId",userId+"");
             SharedPreferencesUtils.setParam(this,"sessionId",sessionId);
             EventBus.getDefault().postSticky(new MessageList("sessionId","1"));
             if(checkBox_remember.isChecked()){
                 SharedPreferencesUtils.setParam(LoginActivity.this,"phone",phone);
                 SharedPreferencesUtils.setParam(LoginActivity.this,"pass",pass);
                 SharedPreferencesUtils.setParam(LoginActivity.this,"c_remember",true);
             }else {
                 SharedPreferencesUtils.setParam(LoginActivity.this,"phone","");
                 SharedPreferencesUtils.setParam(LoginActivity.this,"pass","");
             }
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

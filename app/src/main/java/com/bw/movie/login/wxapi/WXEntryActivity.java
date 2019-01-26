package com.bw.movie.login.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.application.MyApplication;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.LoginBean;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    public static final String TAG = WXEntryActivity.class.getSimpleName();
    public static String code;
    public static BaseResp resp = null;



    @Override
    protected void initView(Bundle savedInstanceState) {
        boolean handleIntent = MyApplication.api.handleIntent(getIntent(), this);
   //下面代码是判断微信分享后返回WXEnteryActivity的，如果handleIntent==false,说明没有调用IWXAPIEventHandler，则需要在这里销毁这个透明的Activity;
        if(handleIntent==false){
            Log.d(TAG, "onCreate: "+handleIntent);
            finish();
        }

    }
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MyApplication.api.handleIntent(intent, this);
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_wxentry;
    }

    @Override
    protected void initData() {
        Map<String,String> map=new HashMap<>();
        map.put("code", code);
        startRequestPost(Apis.URL_WEIXIN_LOGIN,map,WeiXinBean.class);

    }

    @Override
    protected void successed(Object data) {
        WeiXinBean bean= (WeiXinBean) data;
        if(bean.getStatus().equals("0000")){
            ToastUtils.toast(bean.getMessage());
            //用SharedPreferences保存sessionId，userId
            int userId = bean.getResult().getUserId();
            // String phone = bean.getResult().getUserInfo().getPhone();
            String sessionId = bean.getResult().getSessionId();
            SharedPreferencesUtils.setParam(this,"userId",userId+"");
            SharedPreferencesUtils.setParam(this,"sessionId",sessionId);

            Intent intent=new Intent(WXEntryActivity.this, Login_Success_Activity.class);
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
    @Override
    public void onReq(BaseReq baseReq) {
        Log.d(TAG, "onReq: ");
        finish();
    }

    @Override
    public void onResp(BaseResp baseResp) {
        if (baseResp != null) {
            resp = baseResp;
            code = ((SendAuth.Resp) baseResp).code; //即为所需的code
        }
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                Log.d(TAG, "onResp: 成功");
                finish();
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                Log.d(TAG, "onResp: 用户取消");
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Log.d(TAG, "onResp: 发送请求被拒绝");
                finish();
                break;
        }
    }

}

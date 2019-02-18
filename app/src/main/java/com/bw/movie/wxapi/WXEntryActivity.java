package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.bw.movie.R;
import com.bw.movie.application.MyApplication;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login.LoginBean;
import com.bw.movie.login.WeiXinUtil;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    public static String code;


    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewById() {
        return R.layout.activity_wxentry;
    }

    @Override
    protected void initData() {
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
    }

    @Override
    protected void successed(Object data) {
        WeiXinBean bean= (WeiXinBean) data;
        if(bean.getStatus().equals("0000")){
            ToastUtils.toast(bean.getMessage());
            //用SharedPreferences保存sessionId，userId
            int userId = bean.getResult().getUserId();
            Log.i("TAG","userId:"+userId+"");
            // String phone = bean.getResult().getUserInfo().getPhone();
            String sessionId = bean.getResult().getSessionId();
            Log.i("TAG","sessionId"+sessionId);
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

    }

    @Override
    public void onResp(final BaseResp baseResp) {
        switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                //主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //得到code
                        code = ((SendAuth.Resp) baseResp).code;
                        Map<String,String> map=new HashMap<>();
                        map.put("code", code);
                        startRequestPost(Apis.URL_WEIXIN_LOGIN,map,WeiXinBean.class);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
            default:
                break;
        }

    }
}

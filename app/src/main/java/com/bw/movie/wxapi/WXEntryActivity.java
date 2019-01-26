package com.bw.movie.wxapi;

import android.content.Intent;
import android.os.Bundle;

import com.bw.movie.R;
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
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    private static final int RETURN_MSG_TYPE_SHARE = 2;
    // IWXAPI 是第三方app和微信通信的openapi接口
    private IWXAPI api;
    private static final String APP_ID = "wxb3852e6a6b7d9516";

    @Override
    protected void initView(Bundle savedInstanceState) {
        // 通过WXAPIFactory工厂，获取IWXAPI的实例
        api = WXAPIFactory.createWXAPI(this, APP_ID, true);
        // 将应用的appId注册到微信
        api.registerApp(APP_ID);
        //注意：
        //第三方开发者如果使用透明界面来实现WXEntryActivity，需要判断handleIntent的返回值，如果返回值为false，则说明入参不合法未被SDK处理，应finish当前透明界面，避免外部通过传递非法参数的Intent导致停留在透明界面，引起用户的疑惑
        try {
            boolean b = api.handleIntent(getIntent(), this);
            if(b){
                //   ViseLog.d("参数不合法，未被SDK处理，退出");
                ToastUtils.toast("参数不合法，未被SDK处理，退出");
                finish();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_wxentry;
    }

    @Override
    protected void initData() {


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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        api.handleIntent(data,this);
    }
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
        finish();
    }

    public void onResp(BaseResp resp) {
      /*  LogUtils.sf(resp.errStr);
        LogUtils.sf("错误码 : " + resp.errCode + "");*/
        switch (resp.errCode) {

            case BaseResp.ErrCode.ERR_AUTH_DENIED:
            case BaseResp.ErrCode.ERR_USER_CANCEL:

                break;
            case BaseResp.ErrCode.ERR_OK:
                switch (resp.getType()) {
                    case RETURN_MSG_TYPE_LOGIN:
                        //拿到了微信返回的code,立马再去请求access_token
                        String code = ((SendAuth.Resp) resp).code;
                        ToastUtils.toast("code = " + code);

                        //就在这个地方，用网络库什么的或者自己封的网络api，发请求去咯，注意是get请求

                        break;

                    case RETURN_MSG_TYPE_SHARE:
                       /* UIUtils.showToast("微信分享成功");
                        finish();*/
                        break;
                }
                break;
        }
    }


}

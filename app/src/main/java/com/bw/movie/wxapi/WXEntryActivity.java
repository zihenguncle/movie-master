package com.bw.movie.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.WeiXinUtil;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;

import java.util.HashMap;
import java.util.Map;

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {
      String code;
      private SharedPreferences sharedPreferences;
      private SharedPreferences.Editor editor;
    @Override
    protected void initData() {
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(),this);
        sharedPreferences = getSharedPreferences("User",MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void successed(Object data) {
        if (data instanceof WeiXinBean) {
            WeiXinBean bean = (WeiXinBean) data;
            if (((WeiXinBean) data).getStatus().equals("0000")) {
                int userId = bean.getResult().getUserId();
                String sessionId = bean.getResult().getSessionId();
                editor.putString("userId", userId + "");
                editor.putString("sessionId", sessionId);
                editor.commit();
                Intent intent = new Intent(WXEntryActivity.this, Login_Success_Activity.class);
                startActivity(intent);
                finish();
            }
        }
    }


    @Override
    protected void failed(String error) {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        WeiXinUtil.reg(WXEntryActivity.this).handleIntent(getIntent(), this);

    }

    @Override
    protected int getViewById() {
        return R.layout.activity_wxentry;
    }


    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(final BaseResp baseResp)
    {


            switch (baseResp.errCode){
            case BaseResp.ErrCode.ERR_OK:
                //主线程
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //得到code
                        code = ((SendAuth.Resp) baseResp).code;
                        Map<String,String> map = new HashMap<>();
                        map.put("code",code);
                        startRequestPost(Apis.URL_WEIXIN_LOGIN,map,WeiXinBean.class);
                    }
                });
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                break;
                default:
                    break;
        }
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int errCord = baseResp.errCode;
            if (errCord == 0) {
                ToastUtils.toast("支付成功！");

            } else if (errCord == -1) {
                ToastUtils.toast("支付失败！");
            } else {
                ToastUtils.toast("用户取消了！");
            }
            //这里接收到了返回的状态码可以进行相应的操作，如果不想在这个页面操作可以把状态码存在本地然后finish掉这个页面，这样就回到了你调起支付的那个页面
            //获取到你刚刚存到本地的状态码进行相应的操作就可以了
            finish();
        }
    }

}

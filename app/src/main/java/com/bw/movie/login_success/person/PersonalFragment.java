package com.bw.movie.login_success.person;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.person.person_activity.Personal_FeedBack_Advice_Activity;
import com.bw.movie.login_success.person.person_activity.Personal_Fragment_Focus_Activity;
import com.bw.movie.login_success.person.person_activity.Personal_Message_Activity;
import com.bw.movie.login_success.person.person_activity.Personal_Message_Tickets;
import com.bw.movie.login_success.person.person_activity.System_Information;
import com.bw.movie.login_success.person.personal_bean.PersonalMessageBean;
import com.bw.movie.login_success.person.personal_bean.SignInBean;
import com.bw.movie.login_success.person.personal_bean.TokenBean;
import com.bw.movie.login_success.person.personal_bean.UpdateCodeBean;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalFragment extends BaseFragment{
    @BindView(R.id.personal_top_image)
    ImageView personalTopImage;
    @BindView(R.id.personal_name)
    TextView personalName;
    @BindView(R.id.personal_fragment_update_xin_code)
    LinearLayout personal_update_code;
    @BindView(R.id.personal_latest_version)
    ImageView imageView_perosnal;
    private String sessionId;
    @BindView(R.id.personal_sign_in)
    TextView textView_sigin;
    private String token;

    @Override
    protected int getViewById() {
        return R.layout.fargment_personal;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receive(MessageList message) {
        if (message.getFlag().equals("hendPic")) {
            String str = message.getStr().toString();
            Glide.with(this).load(str)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(personalTopImage);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onSucces(MessageList message) {
        if (message.getFlag().equals("sessionId")){
            startRequestGet(Apis.URL_PERSONAL_MESSAGE, PersonalMessageBean.class);
            sessionId = (String)SharedPreferencesUtils.getParam(getContext(),"sessionId","0");

        }
    }


    @Override
    protected void initData(){
        sessionId = (String)SharedPreferencesUtils.getParam(getContext(),"sessionId","0");
        Log.i("TAG",sessionId);
        startRequestGet(Apis.URL_PERSONAL_MESSAGE, PersonalMessageBean.class);
        
    }

    @Override
    public void onResume() {
        super.onResume();
        if(sessionId.equals("0")){
            textView_sigin.setText("请登录");
        }else{
            textView_sigin.setText("签到");
        }
    }

    @Override
    protected void initView(View view){
        ButterKnife.bind(this,view);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @OnClick({R.id.personal_meassage_my,R.id.system_information_push,R.id.personal_fragment_update_xin_code,R.id.personal_message_secede, R.id.personal_sign_in,R.id.personal_fragment_my_focus,R.id.personal_fragment_ticket_record,R.id.personal_feedback_advice,R.id.personal_top_image})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal_fragment_update_xin_code:
                    ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView_perosnal, "rotation", 0.0f, 720f);
                    rotation.setDuration(2000);
                    rotation.start();
                    startRequestGet(Apis.URL_UPDATE_CODE, UpdateCodeBean.class);
                break;
            case R.id.personal_message_secede:
                SharedPreferencesUtils.setParam(getContext(),"userId","0");
                SharedPreferencesUtils.setParam(getContext(),"sessionId","0");
                startRequestGet(Apis.URL_PERSONAL_MESSAGE, PersonalMessageBean.class);
                Intent intent1 = new Intent(getContext(), LoginActivity.class);
                startActivity(intent1);
                break;
            case R.id.system_information_push:
                //TODO:展示系统消息
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent information = new Intent(getContext(), System_Information.class);
                    startActivity(information);
                }
                break;
            case R.id.personal_sign_in:
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    startRequestGet(Apis.URL_SIGN_IN, SignInBean.class);
                }
                break;
            case R.id.personal_meassage_my:
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent = new Intent(getContext(), Personal_Message_Activity.class);
                    startActivity(intent);
                }
                break;
            case R.id.personal_fragment_my_focus:
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent intent_frocus = new Intent(getContext(), Personal_Fragment_Focus_Activity.class);
                    startActivity(intent_frocus);
                }
                break;
            case R.id.personal_fragment_ticket_record:
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent personal_ticket = new Intent(getContext(), Personal_Message_Tickets.class);
                    startActivity(personal_ticket);
                }
                break;
            case R.id.personal_feedback_advice:
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    Intent advice_feedback = new Intent(getContext(), Personal_FeedBack_Advice_Activity.class);
                    startActivity(advice_feedback);
                }
                break;
            case R.id.personal_top_image:
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
                break;
                default:break;
        }
    }
    @Override
    protected void successed(Object data) {
        if (data instanceof SignInBean) {
            SignInBean signInBean = (SignInBean) data;
            if (signInBean.getStatus().equals("0000")){
                ToastUtils.toast(signInBean.getMessage());
            } else {
                ToastUtils.toast(signInBean.getMessage());
            }
        } else if (data instanceof PersonalMessageBean) {
            PersonalMessageBean personalMessageBean = (PersonalMessageBean) data;
            if(personalMessageBean.getStatus().equals("0000")) {
                String headPic = personalMessageBean.getResult().getHeadPic();
                Glide.with(this)
                        .load(headPic)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(personalTopImage);
                String nickName = personalMessageBean.getResult().getNickName();
                personalName.setText(nickName);
                startRequestGet(Apis.URL_PERSONAL_MESSAGE, PersonalMessageBean.class);
            }else {
                ToastUtils.toast(personalMessageBean.getMessage());
            }
        }else if(data instanceof UpdateCodeBean){
            UpdateCodeBean updateCodeBean= (UpdateCodeBean) data;
            if(updateCodeBean.getStatus().equals("0000")){
                final String downloadUrl = updateCodeBean.getDownloadUrl();
                if(updateCodeBean.getFlag()==1){
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("有新版本，需要更新");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            openBrowser(getContext(),downloadUrl);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }else if(updateCodeBean.getFlag()==2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("没新版本，不需要更新");
                    builder.show();
                }
            }else{
                ToastUtils.toast(updateCodeBean.getMessage());
            }
        }
    }

    public static void openBrowser(Context context, String url){
        final Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            final ComponentName componentName = intent.resolveActivity(context.getPackageManager()); // 打印Log   ComponentName到底是什么
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"));
        } else {
            Toast.makeText(context.getApplicationContext(), "请下载浏览器", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);

    }
}

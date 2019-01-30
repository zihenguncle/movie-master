package com.bw.movie.login_success.person;

import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
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
import com.bw.movie.login_success.person.person_activity.Personal_Message_TicketActivity;
import com.bw.movie.login_success.person.person_activity.UpdateAppActivity;
import com.bw.movie.login_success.person.personal_bean.PersonalMessageBean;
import com.bw.movie.login_success.person.personal_bean.SignInBean;
import com.bw.movie.login_success.person.personal_bean.UpdateCodeBean;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PersonalFragment extends BaseFragment{
    @BindView(R.id.personal_top_image)
    ImageView personalTopImage;
    @BindView(R.id.personal_name)
    TextView personalName;
    @BindView(R.id.personal_fragment_update_xin_code)
    RelativeLayout personal_update_code;
    @BindView(R.id.personal_latest_version)
    ImageView imageView_perosnal;
    @Override
    protected int getViewById() {
        return R.layout.fargment_personal;
    }


    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void receive(MessageList message) {
        if (message.equals("hendPic")) {
            String str = message.getStr().toString();
            Glide.with(this).load(str)
                    .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                    .into(personalTopImage);
        }
    }

    @Override
    protected void initData(){
        startRequestGet(Apis.URL_PERSONAL_MESSAGE, PersonalMessageBean.class);
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        if(!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);
        }
    }

    @OnClick({R.id.personal_meassage_my,R.id.personal_fragment_update_xin_code,R.id.personal_message_secede, R.id.personal_sign_in,R.id.personal_fragment_my_focus,R.id.personal_fragment_ticket_record,R.id.personal_feedback_advice})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal_fragment_update_xin_code:
                ObjectAnimator rotation = ObjectAnimator.ofFloat(imageView_perosnal, "rotation", 0.0f, 720f);
                rotation.setDuration(2000);
                rotation.start();
                startRequestGet(Apis.URL_UPDATE_CODE,UpdateCodeBean.class);
                break;
            case R.id.personal_message_secede:
               // getActivity().finish();
                break;
            case R.id.personal_sign_in:
                startRequestGet(Apis.URL_SIGN_IN, SignInBean.class);
                break;
            case R.id.personal_meassage_my:
                Intent intent = new Intent(getContext(), Personal_Message_Activity.class);
                startActivity(intent);
                break;
            case R.id.personal_fragment_my_focus:
                Intent intent_frocus = new Intent(getContext(), Personal_Fragment_Focus_Activity.class);
                startActivity(intent_frocus);
                break;
            case R.id.personal_fragment_ticket_record:
                Intent personal_ticket = new Intent(getContext(), Personal_Message_TicketActivity.class);
                startActivity(personal_ticket);
                break;
            case R.id.personal_feedback_advice:
                Intent advice_feedback = new Intent(getContext(), Personal_FeedBack_Advice_Activity.class);
                startActivity(advice_feedback);
                break;
                default:break;
        }
    }
    @Override
    protected void successed(Object data) {
        if (data instanceof SignInBean) {
            SignInBean signInBean = (SignInBean) data;
            if (signInBean.getStatus().equals("0000")) {
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
                ToastUtils.toast(updateCodeBean.getMessage());
                if(updateCodeBean.getFlag()==1){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("有新版本，需要更新");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), UpdateAppActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "你点击了不是", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });

                    builder.show();

                }else if(updateCodeBean.getFlag()==2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("有新版本，需要更新");
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getContext(), UpdateAppActivity.class);
                            startActivity(intent);
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getContext(), "你点击了不是", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }else{
                ToastUtils.toast(updateCodeBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}

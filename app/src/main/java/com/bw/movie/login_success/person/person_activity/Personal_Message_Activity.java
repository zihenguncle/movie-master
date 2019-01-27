package com.bw.movie.login_success.person.person_activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.person.personal_bean.PersonalMessageBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SimpleDataUtils;
import com.bw.movie.tools.ToastUtils;

import java.text.ParseException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Message_Activity extends BaseActivity {
    @BindView(com.bw.movie.R.id.personal_reset_pwd)
    RelativeLayout personalResetPwd;
    @BindView(com.bw.movie.R.id.personal_icon)
    ImageView personal_icon;
    @BindView(com.bw.movie.R.id.personal_nickName_textView)
    TextView personal_nickName;
    @BindView(com.bw.movie.R.id.personal_sex_textView)
    TextView personal_sex;
    @BindView(com.bw.movie.R.id.personal_month_textView)
    TextView personal_month;
    @BindView(com.bw.movie.R.id.personal_phone_number)
    TextView personal_phone;
    @BindView(com.bw.movie.R.id.personal_emails_count)
    TextView peronsal_emails;


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @OnClick({com.bw.movie.R.id.personal_reset_pwd, com.bw.movie.R.id.personal_message_back})
    public void onClick(View v){
        switch (v.getId()){
            case com.bw.movie.R.id.personal_reset_pwd:
                Intent intent = new Intent(this, Personal_Reset_Pwd_Activity.class);
                startActivity(intent);
                break;
            case com.bw.movie.R.id.personal_message_back:
                finish();
                break;
                default:break;
        }
    }
    @Override
    protected int getViewById() {
        return com.bw.movie.R.layout.personal_message_activity;
    }


    @Override
    protected void initData() {
        startRequestGet(Apis.URL_PERSONAL_MESSAGE,PersonalMessageBean.class);
    }


    @Override
    protected void successed(Object data) {
        if(data instanceof PersonalMessageBean){
            PersonalMessageBean personalMessageBean= (PersonalMessageBean) data;
            if(personalMessageBean.getStatus().equals("0000")){
                String headPic = personalMessageBean.getResult().getHeadPic();
                Glide.with(this)
                        .load(headPic)
                        .apply(RequestOptions.bitmapTransform(new CircleCrop()))
                        .into(personal_icon);
                personal_nickName.setText(personalMessageBean.getResult().getNickName());
                personal_phone.setText(personalMessageBean.getResult().getPhone());
                int sex = personalMessageBean.getResult().getSex();
                if(sex==1){
                    personal_sex.setText("男");
                }else{
                    personal_sex.setText("女");
                }
                try {
                    personal_month.setText(SimpleDataUtils.longToString(personalMessageBean.getResult().getBirthday(), "yyyy-MM-dd"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                peronsal_emails.setText(personalMessageBean.getResult().getEmail());
            }
        }
    }
    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }
}

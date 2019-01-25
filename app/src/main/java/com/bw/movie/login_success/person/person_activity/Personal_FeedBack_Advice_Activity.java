package com.bw.movie.login_success.person.person_activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.Login_Success_Activity;
import com.bw.movie.login_success.person.personal_bean.FeedBackBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_FeedBack_Advice_Activity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.personal_feedback_advice_message)
    RelativeLayout personalFeedbackAdviceMessage;
    @BindView(R.id.personal_feedback_success)
    RelativeLayout personalFeedbackSuccess;
    @BindView(R.id.advice_feedback_content)
    EditText adviceFeedbackContent;
    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.feedback_advice;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void successed(Object data) {
        if(data instanceof FeedBackBean){
            FeedBackBean feedBackBean= (FeedBackBean) data;
            if(feedBackBean.getStatus().equals("0000")){
                TextUtils.isEmpty(feedBackBean.getMessage());
                personalFeedbackSuccess.setVisibility(View.VISIBLE);
                personalFeedbackAdviceMessage.setVisibility(View.GONE);
            }else{
                TextUtils.isEmpty(feedBackBean.getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        TextUtils.isEmpty(error);
    }

    @OnClick({R.id.feedback_advice_button_submit,R.id.personal_advice_back})
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.feedback_advice_button_submit:
                Map<String,String> params=new HashMap<>();
                String s = adviceFeedbackContent.getText().toString();
                if(s.equals("")){
                    ToastUtils.toast("内容不能为空");
                }else {
                    params.put("content",s);
                    startRequestPost(Apis.URL_FEED_BACK, params, FeedBackBean.class);
                }
                break;
            case R.id.personal_advice_back:
                finish();
                break;
                default:break;
        }
    }
}

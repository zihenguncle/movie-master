package com.bw.movie.login_success.person;

import android.content.Intent;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.person.person_activity.Personal_FeedBack_Advice_Activity;
import com.bw.movie.login_success.person.person_activity.Personal_Fragment_Focus_Activity;
import com.bw.movie.login_success.person.person_activity.Personal_Message_Activity;
import butterknife.OnClick;

public class PersonalFragment extends BaseFragment{
    @Override
    protected int getViewById() {
        return R.layout.fargment_personal;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View view) {

    }

    @OnClick({R.id.personal_meassage_my,R.id.personal_fragment_my_focus,R.id.personal_fragment_ticket_record,R.id.personal_feedback_advice})
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal_meassage_my:
                Intent intent = new Intent(getContext(), Personal_Message_Activity.class);
                startActivity(intent);
                break;
            case R.id.personal_fragment_my_focus:
                Intent intent_frocus = new Intent(getContext(), Personal_Fragment_Focus_Activity.class);
                startActivity(intent_frocus);
                break;
            case R.id.personal_fragment_ticket_record:
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

    }

    @Override
    protected void failed(String error) {

    }

}

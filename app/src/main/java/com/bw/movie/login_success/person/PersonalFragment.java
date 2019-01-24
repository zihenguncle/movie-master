package com.bw.movie.login_success.person;

import android.content.Intent;
import android.view.View;

import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.person.person_activity.Personal_Message_Activity;

import butterknife.OnClick;

public class PersonalFragment extends BaseFragment {
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

    @OnClick(R.id.personal_meassage_my)
    public void onClick(View v){
        switch (v.getId()){
            case R.id.personal_meassage_my:
                Intent intent = new Intent(getContext(), Personal_Message_Activity.class);
                startActivity(intent);
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

package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;

import butterknife.ButterKnife;

public class DetailsActivity extends BaseActivity {


    private int shop_id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
        //获取商品id
        Intent intent = getIntent();
        shop_id = intent.getIntExtra("id", 0);

    }

    @Override
    protected int getViewById() {
        return R.layout.activity_details;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void successed(Object data) {

    }

    

    @Override
    protected void failed(String error) {

    }
}

package com.bw.movie.login_success.nearby_cinema_fragment.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;

import java.io.Serializable;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CinemaDtailActivity extends BaseActivity {
    private RecommentBean.ResultBean cinemaInfo;
    @BindView(R.id.focus_image)
    ImageView imageView;
    @BindView(R.id.focus_name)
    TextView textView_name;
    @BindView(R.id.focus_address)
    TextView textView_address;
    @BindView(R.id.focus_position)
    ImageView imageView_position;


    @Override
    protected void initView(Bundle savedInstanceState) {
        //绑定控件
        ButterKnife.bind(this);

        //接收到影院详情信息
        Intent intent = getIntent();
        cinemaInfo = (RecommentBean.ResultBean) intent.getSerializableExtra("cinemaInfo");
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_focus_cinema;
    }

    @Override
    protected void initData() {
        //设置值
        imageView.setScaleType(ImageView.ScaleType.CENTER);
        Glide.with(this).load(cinemaInfo.getLogo()).into(imageView);
        textView_name.setText(cinemaInfo.getName());
        textView_address.setText(cinemaInfo.getAddress());

    }

    @Override
    protected void successed(Object data) {

    }

    @Override
    protected void failed(String error) {

    }
}

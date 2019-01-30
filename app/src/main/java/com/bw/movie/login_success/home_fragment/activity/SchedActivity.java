package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.DetailsBean;
import com.bw.movie.login_success.home_fragment.bean.SchedBean;
import com.bw.movie.tools.ToastUtils;

import java.text.SimpleDateFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.bw.movie.mvp.utils.Apis.URL_MOVE_DATEILS;
import static com.bw.movie.mvp.utils.Apis.URL_SCHEDULE;

public class SchedActivity extends BaseActivity {


    @BindView(R.id.sched_cinemas_name)
    TextView schedCinemasName;
    @BindView(R.id.sched_address)
    TextView schedAddress;
    @BindView(R.id.sched_image)
    ImageView schedImage;
    @BindView(R.id.sched_name)
    TextView schedName;
    @BindView(R.id.sched_movieTypes)
    TextView schedMovieTypes;
    @BindView(R.id.sched_director)
    TextView schedDirector;
    @BindView(R.id.sched_time)
    TextView schedTime;
    @BindView(R.id.sched_movie_address)
    TextView schedMovieAddress;
    @BindView(R.id.sched_recycle)
    RecyclerView schedRecycle;


    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return R.layout.activity_sched;
    }

    @Override
    protected void initData() {

        //得到电影id和影院id
        Intent intent = getIntent();
        int movieId = intent.getIntExtra("movieId", 0);
        int cinemasId = intent.getIntExtra("cinemasId", 0);
        String name = intent.getStringExtra("name");
        String address = intent.getStringExtra("address");
        schedCinemasName.setText(name);
        schedAddress.setText(address);


        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        schedRecycle.setLayoutManager(linearLayoutManager);

        //请求电影的数据
        startRequestGet(String.format(URL_MOVE_DATEILS, movieId), DetailsBean.class);
        //请求排期哈
        startRequestGet(String.format(URL_SCHEDULE, cinemasId, movieId), SchedBean.class);
    }


    @Override
    protected void successed(Object data) {
        if (data instanceof DetailsBean) {
            setMovieData((DetailsBean) data);
        }
        if (data instanceof SchedBean) {

        }

    }

    private void setMovieData(DetailsBean data){
        //设置数据
        String imageUrl = ((DetailsBean) data).getResult().getImageUrl();
        schedImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(4)))
                .into(schedImage);
        String movieTypes = ((DetailsBean) data).getResult().getMovieTypes();
        schedMovieTypes.setText(movieTypes);
        String name = ((DetailsBean) data).getResult().getName();
        schedName.setText(name);
        String duration = ((DetailsBean) data).getResult().getDuration();
        schedTime.setText(duration);
        String director = ((DetailsBean) data).getResult().getDirector();
        schedDirector.setText(director);
        String placeOrigin = ((DetailsBean) data).getResult().getPlaceOrigin();
        schedMovieAddress.setText(placeOrigin);
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

}

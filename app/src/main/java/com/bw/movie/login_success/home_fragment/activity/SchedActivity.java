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
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.home_fragment.adapter.SchedAdapter;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.DetailsBean;
import com.bw.movie.login_success.home_fragment.bean.SchedBean;
import com.bw.movie.tools.SharedPreferencesUtils;
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
    SchedAdapter adapter;
    private DetailsBean databean;

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
        final String name = intent.getStringExtra("name");
        final String address = intent.getStringExtra("address");
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
        adapter = new SchedAdapter(this);
        schedRecycle.setAdapter(adapter);

        adapter.setName(new SchedAdapter.setMovieName() {
            @Override
            public void setFloat(String starttime,String endtime,String num,double price,int scheduleId) {
                String userId = (String) SharedPreferencesUtils.getParam(SchedActivity.this, "userId", "0");
                if(userId.equals("0")){
                    Intent intent1 = new Intent(SchedActivity.this, LoginActivity.class);
                    startActivity(intent1);
                }else {
                    Intent intent = new Intent(SchedActivity.this, CinemaSeatTableActivity.class);

                    //开始的时间,结束时间
                    intent.putExtra("start",starttime);
                    intent.putExtra("end",endtime);
                    intent.putExtra("num",num);
                    //票价
                    intent.putExtra("price",price);
                    //影院Name
                    intent.putExtra("name",name);
                    //影院address
                    intent.putExtra("address",address);

                    //scheduleId
                    intent.putExtra("scheduleId",scheduleId);

                    //电影的Name
                    intent.putExtra("MovieName",databean.getResult().getName());
                    startActivity(intent);
                }

            }
        });
    }


    @Override
    protected void successed(Object data) {
        if (data instanceof DetailsBean) {
            databean = (DetailsBean) data;
            setMovieData((DetailsBean) data);
        }
        if (data instanceof SchedBean) {
            adapter.setData(((SchedBean) data).getResult());
        }

    }

    private void setMovieData(DetailsBean data){
        //设置数据
        String imageUrl = data.getResult().getImageUrl();
        schedImage.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(imageUrl)
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(4)))
                .into(schedImage);
        String movieTypes = data.getResult().getMovieTypes();
        schedMovieTypes.setText(movieTypes);
        String name = data.getResult().getName();
        schedName.setText(name);
        String duration = data.getResult().getDuration();
        schedTime.setText(duration);
        String director = data.getResult().getDirector();
        schedDirector.setText(director);
        String placeOrigin = data.getResult().getPlaceOrigin();
        schedMovieAddress.setText(placeOrigin);
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

}

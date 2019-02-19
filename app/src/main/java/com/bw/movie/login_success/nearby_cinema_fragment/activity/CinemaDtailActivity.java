package com.bw.movie.login_success.nearby_cinema_fragment.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.home_fragment.activity.CinemaSeatTableActivity;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.CommentAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.MovieBannerAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.ScheduleAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.CinemaCommentBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.GreatBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.MovieDetailBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.MovieImageBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.ScheduleBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;
/**
 * date:2018/1/28
 * author:zhangjing
 * function:影院详情,评论，档期
 */

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
    @BindView(R.id.rcf_cinema_flow)
    RecyclerCoverFlow recyclerCoverFlow;
    MovieBannerAdapter movieBannerAdapter;
    @BindView(R.id.shedule_recycler)
    RecyclerView recyclerView;
    private List<MovieImageBean.ResultBean> result;
    private ScheduleAdapter scheduleAdapter;
    @BindView(R.id.relative_focus)
    RelativeLayout relativeLayout_focus;
    @BindView(R.id.relative_no_cinema)
    RelativeLayout relative_no_cinema;
    @BindView(R.id.relative_cinema)
    RelativeLayout relativeLayout_cinema;
    private PopupWindow popupWindow_nocie;
    @BindView(R.id.checked_layout)
    LinearLayout checkedLayout;
    // 声明平移动画
    private TranslateAnimation animation;
    @BindView(R.id.cinema_detail_back)
    ImageView imageView_back;
    private ImageView imageView1;
    private View v1;
    private View v2;
    private RelativeLayout  relative_detail;
    private XRecyclerView recyclerView_comment;
    private TextView textView_address1,textView_phone,textView_route,textView_movie_detail,textView_movie_comment;
    private int movieId;
    private int mPage;
    private int cinemasId;
    private CommentAdapter commentAdapter;
    private String name;
    private String logo;
    private String cinemaName;
    private String address;


    protected void initView(Bundle savedInstanceState) {
        //绑定控件
        ButterKnife.bind(this);

        //接收到影院详情信息
        Intent intent = getIntent();
        logo = intent.getStringExtra("logo");
        cinemaName = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
         cinemasId = intent.getIntExtra("id", 1);
    }

    @Override
    protected int getViewById(){
        return R.layout.activity_focus_cinema;
    }

    @Override
    protected void initData() {
        //设置值
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(this).load(logo).into(imageView);
        textView_name.setText(cinemaName);
        textView_address.setText(address);

        LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView.setLayoutManager(manager);

        startRequestGet(String.format(Apis.URL_MOVIE_AT_TIME,cinemasId), MovieImageBean.class);

        final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(this, checkedLayout, 2, 30);
        recyclerCoverFlow.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {//滑动监听
            @Override
            public void onItemSelected(int position) {
                movieId = result.get(position).getId();
                name = result.get(position).getName();
                imageViewAnimationHelper.startAnimation(position);
                startRequestGet(String.format(Apis.URL_SCHEDULE_CINEMA, cinemasId, movieId), ScheduleBean.class);
            }
        });



        scheduleAdapter=new ScheduleAdapter(this);
        scheduleAdapter.setName(new ScheduleAdapter.setMovieName() {
            @Override
            public void setFloat(String starttime, String endtime, String num, double price,int scheduleId) {
                Intent intent = new Intent(CinemaDtailActivity.this, CinemaSeatTableActivity.class);
                //开始的时间,结束时间
                intent.putExtra("start",starttime);
                intent.putExtra("end",endtime);
                intent.putExtra("num",num);
                //票价
                intent.putExtra("price",price);
                //影院Name
                intent.putExtra("name",cinemaName);
                //影院address
                intent.putExtra("address",address);
                //scheduleId
                intent.putExtra("scheduleId",scheduleId);
                //电影的Name
                intent.putExtra("MovieName",name);
                startActivity(intent);

            }
        });
        recyclerView.setAdapter(scheduleAdapter);

    }

    @OnClick({R.id.relative_focus,R.id.cinema_detail_back})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.relative_focus:
                startRequestGet(String.format(Apis.URL_FIND_CINEMA_INFO,movieId), MovieDetailBean.class);
                //弹出popupWindow
                View popup = getPopup(R.layout.popup_cinema);
                getPopupView(popup);
                break;
            case R.id.cinema_detail_back:
                finish();
                break;
        }
    }
    //详情的获取id
    private void getPopupView(View popupView){
        textView_movie_detail = popupView.findViewById(R.id.text_movie_detail);
        textView_movie_comment = popupView.findViewById(R.id.text_movie_comment);
        imageView1 = popupView.findViewById(R.id.cinema_back);
        v1 = popupView.findViewById(R.id.v1);
        v2 = popupView.findViewById(R.id.v2);
        relative_detail = popupView.findViewById(R.id.relative_detail);
        recyclerView_comment = popupView.findViewById(R.id.recycler_comment);
        textView_address1 = popupView.findViewById(R.id.text_address);
        textView_phone = popupView.findViewById(R.id.text_phone);
        textView_route = popupView.findViewById(R.id.take_route);
        //详情
        textView_movie_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getMovieDetail();
                v1.setVisibility(View.VISIBLE);
                v2.setVisibility(View.INVISIBLE);
                recyclerView_comment.setVisibility(View.INVISIBLE);
                relative_detail.setVisibility(View.VISIBLE);
            }
        });
        //评论
        textView_movie_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getMovieComment();
                v1.setVisibility(View.INVISIBLE);
                v2.setVisibility(View.VISIBLE);
                recyclerView_comment.setVisibility(View.VISIBLE);
                relative_detail.setVisibility(View.INVISIBLE);

            }
        });
        //返回
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_nocie.dismiss();
                NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
            }
        });

        mPage=1;
        final LinearLayoutManager manager=new LinearLayoutManager(this);
        manager.setOrientation(OrientationHelper.VERTICAL);
        recyclerView_comment.setLayoutManager(manager);

        commentAdapter=new CommentAdapter(this);
        recyclerView_comment.setAdapter(commentAdapter);
        commentAdapter.setOnPraiseCallBack(new CommentAdapter.PraiseCallBack() {
            @Override
            public void getPraiseInfo(int commentId, int isGreat, int position) {
                if(isGreat==0){
                    addGreat(commentId);
                    commentAdapter.addHand(position);

                }
            }
        });


        recyclerView_comment.setPullRefreshEnabled(true);
        recyclerView_comment.setLoadingMoreEnabled(true);

        recyclerView_comment.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mPage=1;
                getMovieComment();

            }

            @Override
            public void onLoadMore() {
                getMovieComment();

            }
        });

    }

    private void addGreat(int commentId) {
        Map<String,String> map=new HashMap<>();
        map.put("commentId",commentId+"");
        startRequestPost(Apis.URL_CINEMA_COMMENT_GRENT,map,GreatBean.class);
    }

    private void getMovieComment() {
        startRequestGet(String.format(Apis.URL_FIND_CINEMA_COMMENT,cinemasId,mPage,5), CinemaCommentBean.class);
    }

    //请求详情数据
    private void getMovieDetail() {
        startRequestGet(String.format(Apis.URL_FIND_CINEMA_INFO,movieId), MovieDetailBean.class);
    }

    //弹出popup
    private View getPopup(int popup_view) {
        View popupView = View.inflate(CinemaDtailActivity.this,popup_view,null);
        animation = new TranslateAnimation(Animation.RELATIVE_TO_PARENT, 0, Animation.RELATIVE_TO_PARENT, 0,
                Animation.RELATIVE_TO_PARENT, 1, Animation.RELATIVE_TO_PARENT, 0);
        animation.setInterpolator(new AccelerateInterpolator());
        animation.setDuration(500);
        popupWindow_nocie = new PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT);
        popupWindow_nocie.setBackgroundDrawable(new BitmapDrawable());
        popupWindow_nocie.setFocusable(true);
        popupWindow_nocie.setOutsideTouchable(true);
        popupWindow_nocie.showAtLocation(View.inflate(CinemaDtailActivity.this, popup_view, null),
                Gravity.BOTTOM, 0, 0);
        popupView.startAnimation(animation);

        return popupView;

    }

    @Override
    protected void successed(Object data) {
           if(data instanceof MovieImageBean){
               MovieImageBean bean= (MovieImageBean) data;
               if(bean.getStatus().equals("0000")){
                   relative_no_cinema.setVisibility(View.INVISIBLE);
                   relativeLayout_cinema.setVisibility(View.VISIBLE);
                  result = bean.getResult();
                   recyclerCoverFlow.smoothScrollToPosition(3);
                   movieBannerAdapter=new MovieBannerAdapter(result,this);
                   recyclerCoverFlow.setAdapter(movieBannerAdapter);
                   recyclerCoverFlow.smoothScrollToPosition(1);

               }else {
                   relative_no_cinema.setVisibility(View.VISIBLE);
                   relativeLayout_cinema.setVisibility(View.INVISIBLE);
               }

           }else if(data instanceof ScheduleBean){
               ScheduleBean bean= (ScheduleBean) data;
              if(bean.getStatus().equals("0000")){
                  List<ScheduleBean.ResultBean> result = bean.getResult();
                  scheduleAdapter.setList(result);
              }else {
                  ToastUtils.toast(bean.getMessage());
              }
           }else if(data instanceof MovieDetailBean){
               MovieDetailBean bean= (MovieDetailBean) data;
               if(bean.getStatus().equals("0000")){
                   MovieDetailBean.ResultBean result = bean.getResult();
                   textView_address1.setText(result.getAddress());
                   textView_phone.setText(result.getPhone());
                   textView_route.setText(result.getVehicleRoute());

               }else {
                   ToastUtils.toast(bean.getMessage());
               }
           }else if(data instanceof CinemaCommentBean){
               CinemaCommentBean bean= (CinemaCommentBean) data;
               if(bean.getStatus().equals("0000")){
                   List<CinemaCommentBean.ResultBean> result = bean.getResult();
                   if(mPage==1){
                       commentAdapter.setList(result);
                   }else {
                       commentAdapter.addList(result);
                   }
                   mPage++;
                   recyclerView_comment.loadMoreComplete();
                   recyclerView_comment.refreshComplete();
               }else {
                   ToastUtils.toast(bean.getMessage());
               }
           }else if(data instanceof GreatBean){
               GreatBean bean= (GreatBean) data;
               if(bean.getStatus().equals("0000")){
                   ToastUtils.toast(bean.getMessage());
               }else {
                   ToastUtils.toast(bean.getMessage());
               }
           }
    }

    @Override
    protected void failed(String error) {
          ToastUtils.toast(error);
    }
    @Override
    public void onBackPressed() {
        // 在全屏或者小窗口时按返回键要先退出全屏或小窗口，
        // 所以在Activity中onBackPress要交给NiceVideoPlayer先处理。
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }


}

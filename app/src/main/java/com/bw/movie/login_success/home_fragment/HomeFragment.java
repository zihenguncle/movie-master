package com.bw.movie.login_success.home_fragment;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login_success.home_fragment.activity.ShowDetailsActivity;
import com.bw.movie.login_success.home_fragment.adapter.HomeBannerAdapter;
import com.bw.movie.login_success.home_fragment.adapter.MovieAdapter;
import com.bw.movie.login_success.home_fragment.adapter.MovieDoingAdapter;
import com.bw.movie.login_success.home_fragment.adapter.MovieNoceAdapter;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBeanone;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBeantwo;
import com.bw.movie.login_success.nearby_cinema_fragment.activity.ImageViewAnimationHelper;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import recycler.coverflow.CoverFlowLayoutManger;
import recycler.coverflow.RecyclerCoverFlow;

/**
 * @author 郭淄恒
 * @date 2019.1.24      15:57
 *
 * 影片主页面
 *
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.home_search)
    ImageView image_search;
    @BindView(R.id.home_edit_search)
    EditText edit_search;
    @BindView(R.id.location_text)
    TextView loaction;
    @BindView(R.id.home_text_search)
    TextView but_search;
    @BindView(R.id.relative_search)
    RelativeLayout relative_search;
    @BindView(R.id.custom_recycle)
    RecyclerCoverFlow coverFlow_recycle;
    HomeBannerAdapter homeBannerAdapter;
    MovieAdapter movieAdapter;
    MovieDoingAdapter doingAdapter;
    MovieNoceAdapter noceAdapter;
    @BindView(R.id.hot_movie_xrecycle)
    RecyclerView hot_recycle;
    @BindView(R.id.doing_movie_xrecycle)
    RecyclerView doing_recycle;
    @BindView(R.id.noce_movie_xrecycle)
    RecyclerView noce_recycle;
    @BindView(R.id.checked_layout)
    LinearLayout checkedLayout;
    private List<HomeBannerBean.ResultBean> result;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String cityCode;
    private String province;
    private String city;
    private double longitude;
    private double latitude;


    @Override
    protected void initData() {
       getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE | WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);// 设置默认键盘不弹出

        startRequestGet(String.format(Apis.URL_BANNER,1,10),HomeBannerBean.class);
        setLayout();
        startRequestGet(String.format(Apis.URL_HOTMOVIE,1,10),HomeBannerBeanone.class);
        startRequestGet(String.format(Apis.URL_DOINGMOVIE,1,10),HomeBannerBeantwo.class);
        movieAdapter = new MovieAdapter(getActivity());
        hot_recycle.setAdapter(movieAdapter);
        doingAdapter = new MovieDoingAdapter(getActivity());
        doing_recycle.setAdapter(doingAdapter);
        noceAdapter = new MovieNoceAdapter(getActivity());
        noce_recycle.setAdapter(noceAdapter);
        final ImageViewAnimationHelper imageViewAnimationHelper = new ImageViewAnimationHelper(getActivity(), checkedLayout, 2, 30);
        coverFlow_recycle.setOnItemSelectedListener(new CoverFlowLayoutManger.OnSelected() {
            @Override
            public void onItemSelected(int position) {
               int i = position%result.size();
                imageViewAnimationHelper.startAnimation(position%result.size());
            }
        });
       startLocation();
    }

    private void startLocation() {
        //开始定位，这里模拟一下定位
                mlocationClient = new AMapLocationClient(getActivity());
        //设置定位监听
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if(aMapLocation!=null){
                    if(aMapLocation.getErrorCode() == 0){
                        //获取纬度
                        latitude = aMapLocation.getLatitude();
                        //获取经度
                        longitude = aMapLocation.getLongitude();
                        //城市信息
                        city = aMapLocation.getCity();
                        //省信息
                        province = aMapLocation.getProvince();
                        //城市编码
                        cityCode = aMapLocation.getCityCode();
                        //地区编码
                        String adCode = aMapLocation.getAdCode();
                        CityPicker.from(getActivity()).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);
                        loaction.setText(city);
                       mlocationClient.stopLocation();

                    }
                }
            }
        });
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //设置定位参数
        mlocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        //启动定位
        mlocationClient.startLocation();
    }

    //布局管理器
    private void setLayout() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        hot_recycle.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getActivity());
        linearLayoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        doing_recycle.setLayoutManager(linearLayoutManager1);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getActivity());
        linearLayoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        noce_recycle.setLayoutManager(linearLayoutManager2);
    }



    @OnClick({R.id.home_text_search,R.id.location,R.id.home_search,R.id.hot_text,R.id.doing_text,R.id.noce_text})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.location:
                getLocation();
                break;
            //点击进行搜索
            case R.id.home_text_search:
                    gotoSearch();
                break;
             //点击放大镜弹出搜索框
            case R.id.home_search:
                    getSearch();
                break;
            case R.id.hot_text:
                Intent intent_hot = new Intent(getActivity(), ShowDetailsActivity.class);
                intent_hot.putExtra("hot","1");
                startActivity(intent_hot);
                break;
            case R.id.doing_text:
                Intent intent_doing = new Intent(getActivity(), ShowDetailsActivity.class);
                intent_doing.putExtra("hot","2");
                startActivity(intent_doing);
                break;
            case R.id.noce_text:
                Intent intent_noce = new Intent(getActivity(), ShowDetailsActivity.class);
                intent_noce.putExtra("hot","3");
                startActivity(intent_noce);
                break;
                default:
                    break;
        }
    }

    private void getLocation() {
        CityPicker.from(getActivity()) //activity或者fragment
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        loaction.setText(data.getName());
                    }

                    @Override
                    public void onCancel(){

                    }

                    @Override
                    public void onLocate() {
                        //定位接口，需要APP自身实现，这里模拟一下定位
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                //定位完成之后更新数据
                                CityPicker.from(getActivity()).locateComplete(new LocatedCity(city, province, cityCode), LocateState.SUCCESS);

                            }
                        }, 3000);
                    }
                })
                .show();
    }


    //进行搜索，如果输入框有信息进行搜索，否则收起
    private void gotoSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", -600, 0);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(true);
        }else {
            ToastUtils.toast(edit_search.getText().toString());
        }
    }

    //搜索框出现
    private void getSearch() {
        if(TextUtils.isEmpty(edit_search.getText().toString())){
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", 0, -600);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(false);
        }
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);

    }

    @Override
    protected void successed(Object data) {
        if(data instanceof HomeBannerBean){
            homeBannerAdapter = new HomeBannerAdapter(((HomeBannerBean) data).getResult(),getActivity());
            coverFlow_recycle.setAdapter(homeBannerAdapter);
            result = ((HomeBannerBean) data).getResult();
            noceAdapter.setData(((HomeBannerBean) data).getResult());
            coverFlow_recycle.smoothScrollToPosition(4);
        }

        if(data instanceof HomeBannerBeantwo){
            doingAdapter.setData(((HomeBannerBeantwo) data).getResult());
        }
        if (data instanceof HomeBannerBeanone){
            movieAdapter.setData(((HomeBannerBeanone) data).getResult());
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    protected int getViewById() {
        return R.layout.fargment_home;
    }



}

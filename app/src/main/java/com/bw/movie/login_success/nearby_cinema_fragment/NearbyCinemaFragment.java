package com.bw.movie.login_success.nearby_cinema_fragment;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.nearby_cinema_fragment.adapter.RecommendAdapter;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.FollowBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.RecommentBean;
import com.bw.movie.mvp.eventbus.MessageList;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
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
/**
 * date:2018/1/27
 * author:zhangjing
 * function:推荐影院，附近影院，关注，取消关注
 */

public class NearbyCinemaFragment extends BaseFragment {
    @BindView(R.id.cinema_recyclerView)
    RecyclerView xRecyclerView;
    @BindView(R.id.recommend_cinema)
    RadioButton radioButton_recommend;
    @BindView(R.id.nearby_cinema)
    RadioButton radioButton_nearby;
    @BindView(R.id.home_edit_search)
    EditText edit_search;
    @BindView(R.id.relative_search)
    RelativeLayout relative_search;
    @BindView(R.id.home_search)
    ImageView image_search;
    @BindView(R.id.home_text_search)
    TextView textView;
    @BindView(R.id.nearbycineam_location_text)
    TextView location_text;

    private RecommendAdapter recommendAdapter;
    private String cinema_name;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private String cityCode;
    private String province;
    private String city;
    private double longitude;
    private double latitude;
    private String sessionId;

    @Override
    protected int getViewById() {
        return R.layout.fargment_nearbycinema;
    }

    @Override
    protected void initData() {
        startLocation();
        //布局管理器
        LinearLayoutManager manager=new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        xRecyclerView.setLayoutManager(manager);

        recommendAdapter=new RecommendAdapter(getActivity());
        xRecyclerView.setAdapter(recommendAdapter);

        recommendAdapter.setOnCallBack(new RecommendAdapter.CallBack() {
            @Override
            public void getInformation(int id, int followCinema,int position) {
                sessionId = (String) SharedPreferencesUtils.getParam(getActivity(), "sessionId", "0");
                if(sessionId.equals("0")){
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }else {
                    if(followCinema==1){
                        //取消关注
                        cancelCollection(id);
                        recommendAdapter.update2(position);
                    }else {
                        //关注
                        collection(id);
                        recommendAdapter.update(position);
                    }
                }

            }
        });

        getInfoCinema();

    }

    @Override
    public void onResume() {
        super.onResume();
        getInfoFindCinema();
        getInfoNearby();
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
                        location_text.setText(city);
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

    private void collection(int id) {
        startRequestGet(String.format(Apis.URL_FOLLOW_CINEMA,id), FollowBean.class);
    }

    private void cancelCollection(int id) {
        startRequestGet(String.format(Apis.URL_CANCEL_FOLLOW_CINEMA,id),FollowBean.class);
    }

    private void getInfoFindCinema() {
        startRequestGet(String.format(Apis.URL_FIND_CINEMA,1,10,cinema_name),RecommentBean.class);
    }

    private void getInfoNearby() {

        startRequestGet(String.format(Apis.URL_NEARBY_CINEAMS,1,10,longitude,latitude), RecommentBean.class);
    }

    private void getInfoCinema() {
        startRequestGet(String.format(Apis.URL_RECOMMEND_CINEAMS,1,10), RecommentBean.class);
    }
    //进行搜索，如果输入框有信息进行搜索，否则收起
    private void gotoSearch() {
            ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", -600, 0);
            translationX.setDuration(500);
            translationX.start();
            image_search.setClickable(true);
            cinema_name = edit_search.getText().toString();
            getInfoFindCinema();
            edit_search.setText("");

    }


    //搜索框出现
    private void getSearch() {
        ObjectAnimator translationX = ObjectAnimator.ofFloat(relative_search, "translationX", 0, -600);
        translationX.setDuration(500);
        translationX.start();
        image_search.setClickable(false);
    }

    @Override
    protected void initView(View view) {
        //绑定控件
        ButterKnife.bind(this,view);

    }
    @OnClick({R.id.recommend_cinema,R.id.nearbycineam_location,R.id.nearby_cinema,R.id.home_text_search,R.id.home_search})
    public void getViewClick(View view){
        switch (view.getId()){
            case R.id.nearbycineam_location:
                getLocation();
                break;
            case R.id.recommend_cinema:
                recommendCineam();
                radioButton_nearby.setChecked(false);
                ObjectAnimator anim = ObjectAnimator.ofFloat(xRecyclerView, "translationX",-700,0 );
                anim.setDuration(300);
                anim.start();
                break;
            case R.id.nearby_cinema:
                nearbyCinema();
                radioButton_recommend.setChecked(false);
                ObjectAnimator anim1 = ObjectAnimator.ofFloat(xRecyclerView, "translationX",700,0 );
                anim1.setDuration(300);
                anim1.start();
                break;
            case R.id.home_text_search:
                gotoSearch();
                break;
            //点击放大镜弹出搜索框
            case R.id.home_search:
                getSearch();
                break;
        }
    }

    private void getLocation() {
        CityPicker.from(getActivity()) //activity或者fragment
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {
                        location_text.setText(data.getName());
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

    private void nearbyCinema() {
        getInfoNearby();
    }

    private void recommendCineam() {
        getInfoCinema();
    }

    @Override
    protected void successed(Object data) {
        if(data instanceof RecommentBean){
            RecommentBean bean= (RecommentBean) data;
            if(bean.getStatus().equals("0000")){
                List<RecommentBean.ResultBean> result = bean.getResult();
               // result.remove(result.size()-1);
                recommendAdapter.setList(result);

            }else {
                ToastUtils.toast(bean.getMessage());
            }

        }else if(data instanceof FollowBean){
            FollowBean bean= (FollowBean) data;
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


}

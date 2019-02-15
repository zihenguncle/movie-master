package com.bw.movie.login_success.home_fragment.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.login.WeiXinUtil;
import com.bw.movie.login_success.home_fragment.bean.CinemaSeatTableDetailBean;
import com.bw.movie.login_success.home_fragment.bean.PayBean;
import com.bw.movie.login_success.home_fragment.bean.PayMessageBean;
import com.bw.movie.login_success.home_fragment.seattable.SeatTable;
import com.bw.movie.login_success.person.person_activity.Personal_Message_TicketActivity;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Author: 郭淄恒
 * Date: 2019/2/13 10:33
 * Description: 选座
 */
public class CinemaSeatTableActivity extends BaseActivity {

    @BindView(R.id.cinema_seat_table_text_beginTime)
    TextView mTextView_beginTime;

    @BindView(R.id.cinema_seat_table_text_endTime)
    TextView mTextView_endTime;

    @BindView(R.id.cinema_seat_table_text_hall)
    TextView mTextView_hall;

    @BindView(R.id.item_cinema_seat_text_price)
    TextView mTextView_price;

    @BindView(R.id.item_cinema_detail_img_v)
    ImageView mImageView_v;

    public SeatTable seatTableView;
    @BindView(R.id.item_cinema_detail_img_x)
    ImageView mImageView_x;
    private int mSeatsTotal;
    double totalPrice = 0;

    @BindView(R.id.seat_table)
    TextView seat_table;
    @BindView(R.id.seat_address)
    TextView seat_address;
    @BindView(R.id.movie_name)
    TextView movie_name;
    private int scheduleId;
    private String orderId;

    @Override
    protected void initView(Bundle savedInstanceState) {

    }

    @Override
    protected int getViewById() {
        return R.layout.activity_cinema_seat_table;
    }
    //购票数量
    int i = 0;
    @Override
    public void initData() {
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        seat_table.setText(name);
        String address = intent.getStringExtra("address");
        seat_address.setText(address);
        String movieName = intent.getStringExtra("MovieName");
        movie_name.setText(movieName+" (国语3D)");
        String start = intent.getStringExtra("start");
        mTextView_beginTime.setText(start);
        final String end = intent.getStringExtra("end");
        mTextView_endTime.setText(end);
        String num = intent.getStringExtra("num");
        mTextView_hall.setText(num);

        scheduleId = intent.getIntExtra("scheduleId", 0);

        //票的单价
        final double price = intent.getDoubleExtra("price", 0.0);
        seatTableView = (SeatTable) findViewById(R.id.seatView);
        seatTableView.setScreenName(num);//设置屏幕名称
        seatTableView.setMaxSelected(3);//设置最多选中

        seatTableView.setSeatChecker(new SeatTable.SeatChecker() {

            @Override
            public boolean isValidSeat(int row, int column) {
                if (column == 2) {
                    return false;
                }
                return true;
            }

            @Override
            public boolean isSold(int row, int column) {
                if (row == 6 && column == 6) {
                    return true;
                }
                return false;
            }

            @Override
            public void checked(int row, int column) {
                    totalPrice += price;
                    i++;
                    mTextView_price.setText(new BigDecimal(totalPrice).setScale(2, BigDecimal.ROUND_DOWN) + "");
            }

            @Override
            public void unCheck(int row, int column) {
                    totalPrice -= price;
                    i--;
                    mTextView_price.setText(new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN)+"");
            }

            @Override
            public String[] checkedSeatTxt(int row, int column) {
                return null;
            }

        });
        seatTableView.setData(10, 15);

    }


    @OnClick(R.id.item_cinema_detail_img_x)
    public void onImgXClickListener() {
        finish();
    }


    //点击去选择支付方式
    private static final int WX = 1;
    private static final int ZFB = 2;
    PopupWindow popupWindow;
    @OnClick(R.id.item_cinema_detail_img_v)
    public void onImgVClickListener() {
        if(totalPrice>0) {
            //签名
            String userId = (String) SharedPreferencesUtils.getParam(CinemaSeatTableActivity.this, "userId", "");
            String sign = MD5(userId + scheduleId + i + "movie");
            //排期表ID
            final Map<String,String> map = new HashMap<>();
            map.put("scheduleId",scheduleId+"");
            map.put("amount",i+"");
            map.put("sign",sign);
            startRequestPost(Apis.URL_GOTO_PAY,map,PayBean.class);
            View view = View.inflate(CinemaSeatTableActivity.this, com.bw.movie.R.layout.popwindow_pay, null);
            //两个选中的框
            final RadioButton weChat = view.findViewById(com.bw.movie.R.id.weChat_pay);
            final RadioButton alipay = view.findViewById(com.bw.movie.R.id.text_alipay);
            //点击进行选择支付方式
            TextView weChat_goto_pay = view.findViewById(com.bw.movie.R.id.weChat_goto_pay);
            TextView text_goto_alipay = view.findViewById(com.bw.movie.R.id.text_goto_alipay);
            TextView sum_price = view.findViewById(com.bw.movie.R.id.textView_sum_price);
            ImageView back = view.findViewById(R.id.image_back);
            popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            popupWindow.setTouchable(true);
            popupWindow.showAtLocation(view,
                    Gravity.BOTTOM, 0, 0);
            //*price*num;
            sum_price.setText("微信支付" + new BigDecimal(totalPrice).setScale(2,BigDecimal.ROUND_DOWN)+"" + "元");

            weChat.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alipay.setChecked(false);
                }
            });

            alipay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    weChat.setChecked(false);
                }
            });

            //点击去支付
            sum_price.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(weChat.isChecked()){
                        Map<String,String> map1 = new HashMap<>();
                        map1.put("payType",WX+"");
                        map1.put("orderId",orderId);
                        startRequestPost(Apis.URL_PAY,map1,PayMessageBean.class);
                    }else if(alipay.isChecked()){
                        ToastUtils.toast("暂无开通支付宝");
                    }else {
                        ToastUtils.toast("请选择支付方式");
                    }
                }
            });
            //点击失去焦点
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }else {
            ToastUtils.toast("请选择座位");
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void sjx(CinemaSeatTableDetailBean detailsBean) {
        mTextView_beginTime.setText(detailsBean.getBeginTime());
        mTextView_endTime.setText(detailsBean.getEndTime());
        mTextView_hall.setText(detailsBean.getHall());
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    /**
     *  MD5加密
     * @param sourceStr
     * @return
     */
    public static String MD5(String sourceStr) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(sourceStr.getBytes());
            byte b[] = md.digest();
            int i;
            StringBuffer buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0)
                    i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
            result = buf.toString();
        } catch (NoSuchAlgorithmException e) {
            System.out.println(e);
        }
        return result;
    }
    @Override
    protected void successed(Object data) {
        if(data instanceof PayBean){
            if(((PayBean) data).getStatus().equals("0000")){
                ToastUtils.toast(((PayBean) data).getMessage());
                orderId = ((PayBean) data).getOrderId();
            }else {
                ToastUtils.toast(((PayBean) data).getMessage());
            }
        }
        if(data instanceof PayMessageBean){
            if(((PayMessageBean) data).getStatus().equals("0000")){
                ToastUtils.toast(((PayMessageBean) data).getMessage());
                WeiXinUtil.weiXinPay(CinemaSeatTableActivity.this, (PayMessageBean) data);
            }else {
                ToastUtils.toast(((PayMessageBean) data).getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

}

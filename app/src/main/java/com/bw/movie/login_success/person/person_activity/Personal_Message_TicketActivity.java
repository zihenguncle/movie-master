package com.bw.movie.login_success.person.person_activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
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
import com.bw.movie.login_success.home_fragment.activity.CinemaSeatTableActivity;
import com.bw.movie.login_success.home_fragment.bean.PayBean;
import com.bw.movie.login_success.home_fragment.bean.PayMessageBean;
import com.bw.movie.login_success.person.personal_adapter.MyCompleAdpter;
import com.bw.movie.login_success.person.personal_adapter.MyWaitPayAdpter;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBean;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBeanTwo;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Personal_Message_TicketActivity extends BaseActivity {

    @BindView(R.id.my_ticket_recycle)
    XRecyclerView recyclerView;
    @BindView(R.id.my_waitpay_radio)
    RadioButton waitpay;
    @BindView(R.id.my_complete_radio)
    RadioButton complete;
    private int wpage=1;
    private int cpage=1;
    MyWaitPayAdpter waitPayAdpter;
    MyCompleAdpter compleAdpter;

    //点击去选择支付方式
    private static final int WX = 1;
    private static final int ZFB = 2;
    PopupWindow popupWindow;
    @Override
    protected void initData() {
        initWaitLayout();
        waitPayAdpter.onCallBack(new MyWaitPayAdpter.setOnCallBack() {
            @Override
            public void setClick(int num, double price, final String scheduleId) {
                double totalPrice = num*price;
                if(totalPrice>0) {
                    //签名
                    String userId = (String) SharedPreferencesUtils.getParam(Personal_Message_TicketActivity.this, "userId", "");
                    String sign = MD5(userId + scheduleId + num + "movie");
                    //排期表ID
                    final Map<String,String> map = new HashMap<>();
                    map.put("scheduleId",scheduleId+"");
                    map.put("amount",num+"");
                    map.put("sign",sign);
                    startRequestPost(Apis.URL_GOTO_PAY,map,PayBean.class);
                    View view = View.inflate(Personal_Message_TicketActivity.this, com.bw.movie.R.layout.popwindow_pay, null);
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
                                map1.put("orderId",scheduleId);
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
        });
    }
    @OnClick(R.id.my_waitpay_radio)
    public void waitOnClick(){
        initWaitLayout();
    }
    @OnClick(R.id.my_complete_radio)
    public void comOnClick(){
        initComLayout();
    }

    //待付款加载布局
    public void  initWaitLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        waitPayAdpter = new MyWaitPayAdpter(this);
        wpage=1;
        recyclerView.setAdapter(waitPayAdpter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                wpage=1;
                recyclerView.refreshComplete();
                getWatiData();
            }

            @Override
            public void onLoadMore() {
                getWatiData();
                recyclerView.loadMoreComplete();
            }
        });
        getWatiData();


    }
    //已完成加载布局
    public void  initComLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        compleAdpter = new MyCompleAdpter(this);
        cpage=1;
        recyclerView.setAdapter(compleAdpter);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                cpage=1;
                recyclerView.refreshComplete();
                getComData();
            }
            @Override
            public void onLoadMore() {
                getComData();
                recyclerView.loadMoreComplete();
            }
        });
        getComData();
    }
    //待付款请求数据
    public void getWatiData(){
        startRequestGet(String.format(Apis.URL_TICKET_RECORD,wpage,10,1),TicketInformationBean.class);
    }
    //已完成请求数据
    public void getComData(){
        startRequestGet(String.format(Apis.URL_TICKET_RECORD,cpage,10,2),TicketInformationBeanTwo.class);
    }
    @Override
    protected void successed(Object data){
        if (data instanceof TicketInformationBean){
            TicketInformationBean ticketRecrodBean = (TicketInformationBean) data;
            if (wpage==1){
                waitPayAdpter.setmList(ticketRecrodBean.getResult());

            }
            else {
                waitPayAdpter.addmList(ticketRecrodBean.getResult());
            }
            wpage++;
        }
        if (data instanceof TicketInformationBeanTwo){
            TicketInformationBeanTwo ticketRecrodBean1 = (TicketInformationBeanTwo) data;
            if (cpage==1){
                compleAdpter.setmList(ticketRecrodBean1.getResult());
            }
            else {
                compleAdpter.addmList(ticketRecrodBean1.getResult());
            }
            cpage++;
        }
        if(data instanceof PayMessageBean){
            if(((PayMessageBean) data).getStatus().equals("0000")){
                WeiXinUtil.weiXinPay(Personal_Message_TicketActivity.this, (PayMessageBean) data);
                //ToastUtils.toast(((PayMessageBean) data).getMessage());
                finish();
            }else {
                ToastUtils.toast(((PayMessageBean) data).getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        ButterKnife.bind(this);
    }

    @Override
    protected int getViewById() {
        return com.bw.movie.R.layout.activity_ticket_record;
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
}

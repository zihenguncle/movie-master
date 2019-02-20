package com.bw.movie.login_success.person.fragment;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.TextView;

import com.alipay.sdk.app.PayTask;
import com.bw.movie.R;
import com.bw.movie.base.BaseFragment;
import com.bw.movie.login.WeiXinUtil;
import com.bw.movie.login_success.home_fragment.activity.CinemaSeatTableActivity;
import com.bw.movie.login_success.home_fragment.bean.PayMessageBean;
import com.bw.movie.login_success.person.personal_adapter.MyWaitPayAdpter;
import com.bw.movie.login_success.person.personal_bean.TicketInformationBean;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WaitPagFragment extends BaseFragment {
    @BindView(R.id.my_ticket_recycle)
    XRecyclerView recyclerView;
    private int wpage;
    MyWaitPayAdpter waitPayAdpter;
    //点击去选择支付方式
    private static final int WX = 1;
    private static final int ZFB = 2;
    PopupWindow popupWindow;
    @Override
    protected int getViewById() {
        return R.layout.wait_tickets;
    }

    @Override
    protected void initData() {
        initWaitLayout();


        waitPayAdpter.onCallBack(new MyWaitPayAdpter.setOnCallBack() {
            @Override
            public void setClick(int num, double price, final String scheduleId) {
                double totalPrice = num*price;
                if(totalPrice>0) {
                    View view = View.inflate(getActivity(), com.bw.movie.R.layout.popwindow_pay, null);
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

                    weChat_goto_pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            weChat.setChecked(true);
                            alipay.setChecked(false);
                        }
                    });

                    text_goto_alipay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            weChat.setChecked(false);
                            alipay.setChecked(true);
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
                                //ToastUtils.toast("暂无开通支付宝");
                                Runnable payRunnable = new Runnable() {

                                    @Override
                                    public void run() {
                                        PayTask alipay = new PayTask(getActivity());
                                        Map <String,String> result = alipay.payV2(scheduleId,true);

                                        Message msg = new Message();
                                        //msg.what = SDK_PAY_FLAG;
                                        msg.obj = result;
                                        mHandler.sendMessage(msg);
                                    }
                                };
                                // 必须异步调用
                                Thread payThread = new Thread(payRunnable);
                                payThread.start();
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
                }
            }
        });
    }
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            ToastUtils.toast(msg+"");
            /*Toast.makeText(DemoActivity.this, result.getResult(),
                    Toast.LENGTH_LONG).show();*/
        };
    };



    //待付款加载布局
    public void  initWaitLayout(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        wpage=1;
        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                wpage=1;

                getWatiData();
            }

            @Override
            public void onLoadMore() {
                getWatiData();


            }
        });
        getWatiData();
        waitPayAdpter = new MyWaitPayAdpter(getActivity());
        recyclerView.setAdapter(waitPayAdpter);

    }
    //待付款请求数据
    public void getWatiData(){
        startRequestGet(String.format(Apis.URL_TICKET_RECORD,wpage,10,1),TicketInformationBean.class);
    }
    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
    }

    @Override
    protected void successed(Object data) {
        if (data instanceof TicketInformationBean){
            TicketInformationBean ticketRecrodBean = (TicketInformationBean) data;
            if (wpage==1){
                waitPayAdpter.setmList(ticketRecrodBean.getResult());
            }
            else {
                waitPayAdpter.addmList(ticketRecrodBean.getResult());
            }
            wpage++;
            recyclerView.refreshComplete();
            recyclerView.loadMoreComplete();
        }
        if(data instanceof PayMessageBean){
            if(((PayMessageBean) data).getStatus().equals("0000")){
                WeiXinUtil.weiXinPay(getActivity(), (PayMessageBean) data);
                //ToastUtils.toast(((PayMessageBean) data).getMessage());
               // finish();
                getWatiData();
            }else {
                ToastUtils.toast(((PayMessageBean) data).getMessage());
            }
        }
    }

    @Override
    protected void failed(String error) {
        ToastUtils.toast(error);
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

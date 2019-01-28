package com.bw.movie.login_success.person.personal_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.login_success.person.personal_bean.TicketInformationBean;
import com.bw.movie.tools.SimpleDataUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PerformAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<TicketInformationBean.ResultBean> mData;
    private Context mContext;
    private static final int TYPE_TICKET_PAY=1;
    private static final int TYPE_PERFORM=2;
    private static final int TYPE_NONE=0;

    public PerformAdapter(Context mContext) {
        this.mContext = mContext;
        mData=new ArrayList();
    }

    public void setDatas(List<TicketInformationBean.ResultBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addDatas(List<TicketInformationBean.ResultBean> data) {
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getStatus()==TYPE_TICKET_PAY){
            return TYPE_TICKET_PAY;
        }else if(mData.get(position).getStatus()==TYPE_PERFORM){
            return TYPE_PERFORM;
        }else{
            return TYPE_NONE;
        }
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i){
            case TYPE_TICKET_PAY:
                View ticket = LayoutInflater.from(mContext).inflate(com.bw.movie.R.layout.ticket_pay, viewGroup, false);
                return new PayViewHolder(ticket);
            case TYPE_PERFORM:
                View complete = LayoutInflater.from(mContext).inflate(com.bw.movie.R.layout.perform_indent, viewGroup, false);
                return new CompleteHolder(complete);
                default:
                    return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType){
            case TYPE_TICKET_PAY:
                PayViewHolder payViewHolder= (PayViewHolder) viewHolder;
                payViewHolder.ticket_name.setText(mData.get(i).getMovieName());
                try {
                    String s = SimpleDataUtils.longToString(mData.get(i).getBeginTime(), "YYYY-mm-hh-");
                    payViewHolder.ticket_time_begin.setText(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                try {
                    String s = SimpleDataUtils.longToString(mData.get(i).getEndTime(), "YYYY-mm-hh");
                    payViewHolder.ticket_end_time.setText(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                payViewHolder.ticket_number.setText(mData.get(i).getOrderId());
                try {
                    String s = SimpleDataUtils.longToString(mData.get(i).getCreateTime(), "yyyy-mm-dd hh:mm");
                    payViewHolder.pay_time.setText(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                payViewHolder.cimeama_name.setText(mData.get(i).getCinemaName());
                payViewHolder.address_pay.setText(mData.get(i).getScreeningHall());
                payViewHolder.ticket_sum_number.setText(mData.get(i).getAmount()+"张");
                payViewHolder.sum_price.setText(mData.get(i).getPrice()+"元");
                break;
            case TYPE_PERFORM:
                CompleteHolder completeHolder= (CompleteHolder) viewHolder;
                completeHolder.ticket_indent_name.setText(mData.get(i).getMovieName());
                completeHolder.indent_number_pay.setText(mData.get(i).getOrderId());
                completeHolder.cimeama_name_pay.setText(mData.get(i).getCinemaName());
                completeHolder.address_pay_name.setText(mData.get(i).getScreeningHall());
                try {
                    String s = SimpleDataUtils.longToString(mData.get(i).getCreateTime(), "yyyy-mm-dd hh:mm");
                    completeHolder.pay_time_indent.setText(s);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                completeHolder.ticket_sum_number_indent.setText(mData.get(i).getAmount()+"张");
                completeHolder.sum_price_indent.setText(mData.get(i).getPrice()+"元");
                completeHolder.pay_item.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(onClickListener!=null){
                            onClickListener.onSuccess(mData.get(i).getOrderId()+"",mData.get(i).getPrice()+"",mData.get(i).getAmount());
                        }
                    }
                });

                break;
                default:break;
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class PayViewHolder extends RecyclerView.ViewHolder {
        @BindView(com.bw.movie.R.id.ticket_pay_name)
        TextView ticket_name;
        @BindView(com.bw.movie.R.id.ticket_pay_time_begin)
        TextView ticket_time_begin;
        @BindView(com.bw.movie.R.id.ticket_pay_time_end)
        TextView ticket_end_time;
        @BindView(com.bw.movie.R.id.indent_number)
        TextView ticket_number;
        @BindView(com.bw.movie.R.id.pay_time)
        TextView pay_time;
        @BindView(com.bw.movie.R.id.cimeama_name)
        TextView cimeama_name;
        @BindView(com.bw.movie.R.id.address_pay)
        TextView address_pay;
        @BindView(com.bw.movie.R.id.ticket_sum_number)
        TextView ticket_sum_number;
        @BindView(com.bw.movie.R.id.sum_price)
        TextView sum_price;
        public PayViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class CompleteHolder extends RecyclerView.ViewHolder {
        @BindView(com.bw.movie.R.id.ticket_indent_name)
        TextView ticket_indent_name;
        @BindView(com.bw.movie.R.id.pay_item)
        TextView pay_item;
        @BindView(com.bw.movie.R.id.indent_number_pay)
        TextView indent_number_pay;
        @BindView(com.bw.movie.R.id.cimeama_name_pay)
        TextView cimeama_name_pay;
        @BindView(com.bw.movie.R.id.address_pay_name)
        TextView address_pay_name;
        @BindView(com.bw.movie.R.id.pay_time_indent)
        TextView pay_time_indent;
        @BindView(com.bw.movie.R.id.ticket_sum_number_indent)
        TextView ticket_sum_number_indent;
        @BindView(com.bw.movie.R.id.sum_price_indent)
        TextView sum_price_indent;
        public CompleteHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }


    OnClicksListener onClickListener;

    public void setOnClickListener(OnClicksListener onClickListener){
        this.onClickListener=onClickListener;
    }

    public interface OnClicksListener {
        void onSuccess(String orderId,String price,int sum);
    }
}

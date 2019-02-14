package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.BuyTicketBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyTicketAdapter extends RecyclerView.Adapter<BuyTicketAdapter.ViewHolder> {


    private Context mContext;
    private List<BuyTicketBean.ResultBean> mDatas;

    public BuyTicketAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
    }

    public void setDatas(List<BuyTicketBean.ResultBean> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.buy_ticket_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        viewHolder.titleBuyTicket.setText(mDatas.get(i).getName());
        viewHolder.addressBuyTicket.setText(mDatas.get(i).getAddress());
        String logo = mDatas.get(i).getLogo();
        Glide.with(mContext).load(logo).into(viewHolder.imageBuyTicket);
        viewHolder.buyTicketItemInstance.setText(mDatas.get(i).getDistance()+"km");

        if(mDatas.get(i).getFollowCinema()==1){
            viewHolder.buy_ticket_fouce.setImageResource(R.mipmap.com_icon_collection_selected);
        }else {
            viewHolder.buy_ticket_fouce.setImageResource(R.mipmap.com_icon_collection_default);
        }
        //关注
        viewHolder.buy_ticket_fouce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClickLisener!=null){
                    onClickLisener.onSuccess(mDatas.get(i).getId(), mDatas.get(i).getFollowCinema(), i);
                }
            }
        });
        //影院的id
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onClick!=null){
                    onClick.onCinema(mDatas.get(i).getId(),i,mDatas.get(i).getName(),mDatas.get(i).getAddress());
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void updateTrue(int position){
        mDatas.get(position).setFollowCinema(1);
        notifyDataSetChanged();
    }
    public void updateFalse(int position){
        mDatas.get(position).setFollowCinema(2);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_buy_ticket)
        ImageView imageBuyTicket;
        @BindView(R.id.title_buy_ticket)
        TextView titleBuyTicket;
        @BindView(R.id.address_buy_ticket)
        TextView addressBuyTicket;
        @BindView(R.id.buy_ticket_item_instance)
        TextView buyTicketItemInstance;
        @BindView(R.id.buy_ticket_focuse)
        ImageView buy_ticket_fouce;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    OnClickLisener onClickLisener;

    OnsClick onClick;

    public void setOnClickLisener(OnsClick onsClick){
        this.onClick=onsClick;
    }
    public interface OnsClick {
        void onCinema(int id, int position, String name, String address);
    }

    public void setOnClickLisener(OnClickLisener onClickLisener){
        this.onClickLisener=onClickLisener;
    }
    public interface OnClickLisener {
        void onSuccess(int id, int followCinema, int position);
    }

}


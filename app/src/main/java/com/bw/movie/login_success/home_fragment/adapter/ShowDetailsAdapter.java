package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;

import java.util.ArrayList;
import java.util.List;

public class ShowDetailsAdapter extends RecyclerView.Adapter<ShowDetailsAdapter.ViewHolder> {

    private Context mContext;
    private List<HomeBannerBean.ResultBean> mDatas;

    public ShowDetailsAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas=new ArrayList();
    }

    public void setDatas(List<HomeBannerBean.ResultBean> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<HomeBannerBean.ResultBean> datas) {
        //mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ShowDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view=LayoutInflater.from(mContext).inflate(R.layout.show_details_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShowDetailsAdapter.ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

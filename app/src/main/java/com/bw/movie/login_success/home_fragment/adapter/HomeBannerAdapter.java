package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.tools.ToastUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author 郭淄恒
 *
 *    首页轮播图
 * @date 2019.1.24     19.40
 */
public class HomeBannerAdapter extends RecyclerView.Adapter<HomeBannerAdapter.ViewHolder> {

    private List<HomeBannerBean.ResultBean> data;
    private Context context;

    public HomeBannerAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<HomeBannerBean.ResultBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    @Override
    public HomeBannerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_homebanner,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeBannerAdapter.ViewHolder holder, final int position) {
        Glide.with(context).load(data.get(position%data.size()).getImageUrl()).into(holder.iamge);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.toast(position+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_img)
        ImageView iamge;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.activity.DetailsActivity;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
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

    public HomeBannerAdapter(List<HomeBannerBean.ResultBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public HomeBannerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_homebanner,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(HomeBannerAdapter.ViewHolder holder, final int position) {
        holder.iamge.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(data.get(position%data.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(holder.iamge);
        holder.name.setText(data.get(position%data.size()).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailsActivity.class);
                intent.putExtra("id",data.get(position%data.size()).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_home_img)
        ImageView iamge;
        @BindView(R.id.item_banner_name)
        TextView name;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

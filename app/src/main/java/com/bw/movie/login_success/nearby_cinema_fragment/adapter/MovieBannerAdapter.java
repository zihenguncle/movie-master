package com.bw.movie.login_success.nearby_cinema_fragment.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.activity.DetailsActivity;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBean;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.MovieImageBean;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieBannerAdapter extends RecyclerView.Adapter<MovieBannerAdapter.ViewHolder> {

    private List<MovieImageBean.ResultBean> data;
    private Context context;

    public MovieBannerAdapter(List<MovieImageBean.ResultBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @Override
    public MovieBannerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_homebanner,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieBannerAdapter.ViewHolder holder, final int position) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(holder.iamge, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        MovieImageBean.ResultBean resultBean = data.get(position);
        holder.iamge.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(resultBean.getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)).placeholder(R.drawable.rotate))
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        anim.cancel();
                        return false;
                    }
                })
                .into(holder.iamge);
        holder.name.setText(resultBean.getName());

    }

    @Override
    public int getItemCount() {
        return data.size();
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

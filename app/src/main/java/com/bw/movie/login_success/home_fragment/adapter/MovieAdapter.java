package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
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
import com.bw.movie.login_success.home_fragment.bean.HomeBannerBeanone;
import com.bw.movie.login_success.home_fragment.bean.HomeMovieBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private Context context;
    private List<HomeBannerBeanone.ResultBean> data;


    public MovieAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<HomeBannerBeanone.ResultBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.item_movie,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.image.setScaleType(ImageView.ScaleType.FIT_XY);
        Glide.with(context)
                .load(data.get(position%data.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(10)))
                .into(holder.image);
        holder.text.setText(data.get(position).getName());
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
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_movie_iamge)
        ImageView image;
        @BindView(R.id.item_movie_name)
        TextView text;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

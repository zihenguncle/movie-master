package com.bw.movie.login_success.person.personal_adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
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
import com.bw.movie.login_success.person.personal_bean.VideInformationBean;
import com.bw.movie.tools.SimpleDataUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    private List<VideInformationBean.ResultBean> mDatas;
    private Context mContext;

    public VideoAdapter(Context mContext) {
        this.mContext = mContext;
        mDatas = new ArrayList<>();
    }

    public void setDatas(List<VideInformationBean.ResultBean> datas) {
        //this.mDatas = mDatas;
        mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    public void addDatas(List<VideInformationBean.ResultBean> datas) {
        //this.mDatas = mDatas;
        //mDatas.clear();
        mDatas.addAll(datas);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.video_ticket_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(viewHolder.videoTicketItemImageview, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        Glide.with(mContext)
                .load(mDatas.get(i%mDatas.size()).getImageUrl())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(5)).placeholder(R.drawable.rotate))
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
                .into(viewHolder.videoTicketItemImageview);
        viewHolder.videoTicketItemName.setText(mDatas.get(i).getName());
        viewHolder.videoTicketItemTitle.setText(mDatas.get(i).getSummary());
        try {
            String data = SimpleDataUtils.longToString(mDatas.get(i).getReleaseTime(), "yyyy-MM-dd");
            viewHolder.videoTicketItemData.setText(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mContext, DetailsActivity.class);
                intent.putExtra("id",mDatas.get(i).getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.video_ticket_item_imageview)
        ImageView videoTicketItemImageview;
        @BindView(R.id.video_ticket_item_name)
        TextView videoTicketItemName;
        @BindView(R.id.video_ticket_item_title)
        TextView videoTicketItemTitle;
        @BindView(R.id.video_ticket_item_data)
        TextView videoTicketItemData;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

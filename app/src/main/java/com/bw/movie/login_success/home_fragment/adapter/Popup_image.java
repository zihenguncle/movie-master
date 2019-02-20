package com.bw.movie.login_success.home_fragment.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.DetailsBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Popup_image  extends RecyclerView.Adapter<Popup_image.ViewHolder> {

    private List<String> data;
    private Context context;

    public Popup_image(List<String> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.popup_image_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(viewHolder.imageView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) viewHolder.imageView.getLayoutParams();

        Glide.with(context).load(data.get(i))
                .apply(new RequestOptions().placeholder(R.drawable.rotate))
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
                .into(viewHolder.imageView);
        viewHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(xrecycleData != null){
                    xrecycleData.getdata(data.get(i));
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.popup_stage_iamge)
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public getXrecycleData xrecycleData;

    public void setXrecycleData(getXrecycleData getXrecycleData){
        xrecycleData = getXrecycleData;
    }
    public interface getXrecycleData{
        void getdata(String url);
    }

}

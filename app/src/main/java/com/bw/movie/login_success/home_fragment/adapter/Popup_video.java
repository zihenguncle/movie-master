package com.bw.movie.login_success.home_fragment.adapter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.bean.DetailsBean;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Popup_video extends RecyclerView.Adapter<Popup_video.ViewHolder> {

    private List<DetailsBean.ResultBean.ShortFilmListBean> data;
    private Context context;

    public Popup_video(List<DetailsBean.ResultBean.ShortFilmListBean> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.popup_notice_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        final ObjectAnimator anim = ObjectAnimator.ofInt(viewHolder.videoView, "ImageLevel", 0, 10000);
        anim.setDuration(800);
        anim.setRepeatCount(ObjectAnimator.INFINITE);
        anim.start();
        viewHolder.videoView.setPlayerType(NiceVideoPlayer.TYPE_IJK); // or NiceVideoPlayer.TYPE_NATIVE
        viewHolder.videoView.setUp(data.get(i).getVideoUrl(), null);
        TxVideoPlayerController controller = new TxVideoPlayerController(context);
        controller.setTitle("预告");
        Glide.with(context).load(data.get(i).getImageUrl())
                .apply(new RequestOptions().placeholder(R.drawable.rotate))
                .into(controller.imageView());
        viewHolder.videoView.setController(controller);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.video_item)
        NiceVideoPlayer videoView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}

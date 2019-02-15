package com.bw.movie.login_success.nearby_cinema_fragment.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login.LoginActivity;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.CinemaCommentBean;
import com.bw.movie.tools.SharedPreferencesUtils;
import com.bw.movie.tools.SimpleDataUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CinemaCommentBean.ResultBean> list;
    private Context context;
    private String sessionId;

    public CommentAdapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public void setList(List<CinemaCommentBean.ResultBean> list) {
        this.list .clear();
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }
    public void addList(List<CinemaCommentBean.ResultBean> list) {
        if(list!=null){
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.cinema_comment_item,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        sessionId = (String) SharedPreferencesUtils.getParam(context, "sessionId", "0");
        final CinemaCommentBean.ResultBean resultBean = list.get(i);
        Glide.with(context).load(resultBean.getCommentHeadPic()). apply(RequestOptions.bitmapTransform(new CircleCrop())).
        into(viewHolder.imageView_pic);
        viewHolder.textView_name.setText(resultBean.getCommentUserName());
        viewHolder.textView_content.setText(resultBean.getCommentContent());
        viewHolder.textView_num.setText(resultBean.getGreatNum()+"");
        try {
            String data = SimpleDataUtils.longToString(resultBean.getCommentTime(), "yyyy-MM-dd");
            viewHolder.textView_time.setText(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //点赞
        if(resultBean.getIsGreat()==1){
            viewHolder.imageView_praise.setImageResource(R.mipmap.com_icon_praise_selected);
        }else {
            viewHolder.imageView_praise.setImageResource(R.mipmap.com_icon_praise_default);
        }
        //点赞的点击事件
        viewHolder.imageView_praise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sessionId.equals("0")){
                    Intent intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                }else {
                    if(praiseCallBack!=null){
                        if(resultBean.getIsGreat()==1){
                            praiseCallBack.getPraiseInfo(resultBean.getCommentId(),resultBean.getIsGreat(),i);
                        }else {
                            praiseCallBack.getPraiseInfo(resultBean.getCommentId(),resultBean.getIsGreat(),i);
                        }

                    }
                }

            }
        });

    }
    public void addHand(int position){
        list.get(position).setIsGreat(1);
        list.get(position).setGreatNum(list.get(position).getGreatNum()+1);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.image_commentHeadPic)
        ImageView imageView_pic;
        @BindView(R.id.text_commentUserName)
        TextView textView_name;
        @BindView(R.id.text_commentContent)
        TextView textView_content;
        @BindView(R.id.text_commentTime)
        TextView textView_time;
        @BindView(R.id.text_greatNum)
        TextView textView_num;
        @BindView(R.id.image_praise)
        ImageView imageView_praise;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    //接口回调
    public PraiseCallBack praiseCallBack;
    public void setOnPraiseCallBack(PraiseCallBack callBack){
        this.praiseCallBack=callBack;
    }
    public interface PraiseCallBack{
        void getPraiseInfo(int commentId,int isGreat,int position);
    }
}

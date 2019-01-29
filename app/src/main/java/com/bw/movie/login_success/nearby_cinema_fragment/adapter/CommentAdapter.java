package com.bw.movie.login_success.nearby_cinema_fragment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bw.movie.R;
import com.bw.movie.login_success.nearby_cinema_fragment.bean.CinemaCommentBean;
import com.bw.movie.tools.SimpleDataUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.ViewHolder> {
    private List<CinemaCommentBean.ResultBean> list;
    private Context context;

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
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getCommentHeadPic()).into(viewHolder.imageView_pic);
        viewHolder.textView_name.setText(list.get(i).getCommentUserName());
        viewHolder.textView_content.setText(list.get(i).getCommentContent());
        viewHolder.textView_num.setText(list.get(i).getGreatNum()+"");
        try {
            String data = SimpleDataUtils.longToString(list.get(i).getCommentTime(), "yyyy-MM-dd");
            viewHolder.textView_time.setText(data);
        } catch (ParseException e) {
            e.printStackTrace();
        }

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
}

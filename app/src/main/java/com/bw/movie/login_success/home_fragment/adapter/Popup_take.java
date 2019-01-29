package com.bw.movie.login_success.home_fragment.adapter;

import android.content.Context;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.bw.movie.R;
import com.bw.movie.login_success.home_fragment.banner__round.GlidRoundUtils;
import com.bw.movie.login_success.home_fragment.bean.TakeBean;
import com.bw.movie.login_success.home_fragment.bean.Take_Take_Bean;
import com.bw.movie.mvp.presenter.IPresemterImpl;
import com.bw.movie.mvp.utils.Apis;
import com.bw.movie.mvp.view.IView;
import com.bw.movie.tools.ToastUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Popup_take extends RecyclerView.Adapter<Popup_take.ViewHolder>  {

    private List<TakeBean.ResultBean> data;
    private Context context;
    private int id;
    private IPresemterImpl iPresemter;
    private Popup_taketake popup_taketake;
    private int commentId;

    public Popup_take(Context context, int id) {
        this.context = context;
        this.id = id;
        data = new ArrayList<>();

    }

    public void setData(List<TakeBean.ResultBean> data) {
        this.data = data;
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }
    public void addData(List<TakeBean.ResultBean> data) {
        if(data != null){
            this.data.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Popup_take.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context,R.layout.popup_take_item,null);
        return new ViewHolder(view);
    }

    public static final String Time_Style = "yyyy-MM-dd HH:mm:ss";
    private int page;
    @Override
    public void onBindViewHolder(@NonNull final Popup_take.ViewHolder viewHolder, int i) {
        Glide.with(context).load(data.get(i).getCommentHeadPic()).into(viewHolder.itemTakeHeadimage);
        viewHolder.itemTakeName.setText(data.get(i).getCommentUserName());
        Glide.with(context).load(data.get(i).getCommentHeadPic())
                .apply(RequestOptions.bitmapTransform(new GlidRoundUtils(180)))
                .into(viewHolder.itemTakeHeadimage);
        SimpleDateFormat dateFormat = new SimpleDateFormat(Time_Style, Locale.getDefault());
        String format = dateFormat.format(data.get(i).getCommentTime());
        viewHolder.itemTakeDate.setText(format);
        viewHolder.itemTakeContent.setText(data.get(i).getMovieComment());
        viewHolder.itemTakeCommentnum.setText(data.get(i).getReplyNum()+"");
        viewHolder.itemTakePraisenum.setText(data.get(i).getGreatNum()+"");
        commentId = data.get(i).getCommentId();
        viewHolder.totalNum.setText("共"+data.get(i).getReplyNum()+"条评论");
        //设置适配器
        page=1;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        viewHolder.itemXrecycleItem.setLayoutManager(linearLayoutManager);
        viewHolder.itemXrecycleItem.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                page=1;
                getData();
            }
            @Override
            public void onLoadMore() {
                getData();
            }
        });
        getData();
        popup_taketake = new Popup_taketake(context);
        viewHolder.itemXrecycleItem.setAdapter(popup_taketake);
        viewHolder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewHolder.itemXrecycleItem.setVisibility(View.VISIBLE);
                viewHolder.totalNum.setVisibility(View.VISIBLE);
                if(xrecycleData != null){
                    xrecycleData.getdata();
                }
            }
        });


    }



    private void getData(){
        iPresemter.startRequestGet(String.format(Apis.URL_TAKE_TAKE,commentId,page,5),Take_Take_Bean.class);
    }
    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements IView{
        @BindView(R.id.item_take_headimage)
        ImageView itemTakeHeadimage;
        @BindView(R.id.item_take_name)
        TextView itemTakeName;
        @BindView(R.id.item_take_content)
        TextView itemTakeContent;
        @BindView(R.id.item_take_date)
        TextView itemTakeDate;
        @BindView(R.id.item_take_commentnum)
        TextView itemTakeCommentnum;
        @BindView(R.id.item_take_praisenum)
        TextView itemTakePraisenum;
        @BindView(R.id.total_num)
        TextView totalNum;
        @BindView(R.id.item_xrecycle_item)
        XRecyclerView itemXrecycleItem;
        @BindView(R.id.item_take_comment)
        ImageView comment;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            iPresemter=new IPresemterImpl(this);
        }

        @Override
        public void onSuccessed(Object data) {
            Take_Take_Bean take_take_bean = (Take_Take_Bean) data;
            if(page==1){
                popup_taketake.setData(((Take_Take_Bean) data).getResult());
            }else {
                popup_taketake.addData(((Take_Take_Bean) data).getResult());
            }
            page++;
            itemXrecycleItem.loadMoreComplete();
            itemXrecycleItem.refreshComplete();
        }

        @Override
        public void onFailed(String error) {
            ToastUtils.toast(error);
        }
    }

    public void detach(){
        iPresemter = null;
    }

    public getXrecycleData xrecycleData;

    public void setXrecycleData(getXrecycleData getXrecycleData){
        xrecycleData = getXrecycleData;
    }
    public interface getXrecycleData{
        void getdata();
    }

}

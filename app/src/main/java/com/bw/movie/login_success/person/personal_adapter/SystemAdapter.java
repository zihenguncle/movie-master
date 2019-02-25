package com.bw.movie.login_success.person.personal_adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.login_success.person.personal_bean.SystemInformationBean;
import com.bw.movie.tools.SimpleDataUtils;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SystemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<SystemInformationBean.ResultBean> mData;
    private static final int TYPE_READ=0;
    private static final int TYPE_SUCCESS=1;

    public SystemAdapter(Context mContext) {
        this.mContext = mContext;
        mData=new ArrayList<>();
    }

    public void setData(List<SystemInformationBean.ResultBean> data) {
       // this.mData = mData;
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<SystemInformationBean.ResultBean> data) {
       // this.mData = mData;
       // mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }



    @Override
    public int getItemViewType(int position) {
        if(mData.get(position).getStatus()==TYPE_SUCCESS){
            return TYPE_SUCCESS;
        }else{
            return TYPE_READ;
        }

    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        switch (i) {
            case TYPE_SUCCESS:
                View type_success = LayoutInflater.from(mContext).inflate(R.layout.read_success, viewGroup, false);
                return new SuccessViewHolder(type_success);
            case TYPE_READ:
                View type_read = LayoutInflater.from(mContext).inflate(R.layout.read_type, viewGroup, false);
                return new ReadViewHolder(type_read);
                default:
                    return null;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        switch (itemViewType){
            case TYPE_SUCCESS:
                SuccessViewHolder successViewHolder= (SuccessViewHolder) viewHolder;
                successViewHolder.read_title.setText(mData.get(i).getTitle());
                successViewHolder.read_count.setText(mData.get(i).getContent());
                try {
                    String time = SimpleDataUtils.longToString(mData.get(i).getPushTime(), "mm-dd hh:mm");
                    successViewHolder.read_time.setText(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            case TYPE_READ:
                ReadViewHolder readViewHolder= (ReadViewHolder) viewHolder;
                readViewHolder.title.setText(mData.get(i).getTitle());
                readViewHolder.count.setText(mData.get(i).getContent());
                try {
                    String time = SimpleDataUtils.longToString(mData.get(i).getPushTime(), "mm-dd hh:mm");
                    readViewHolder.time.setText(time);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                readViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //读取信息
                        if(onClickListeener!=null){
                            onClickListeener.onSuccess(mData.get(i).getId()+"",i,mData.get(i).getContent());
                        }
                    }
                });
                break;
                default:break;
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class SuccessViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.read_success_title)
        TextView read_title;
        @BindView(R.id.read_success_count)
        TextView read_count;
        @BindView(R.id.read_success_time)
        TextView read_time;
        public SuccessViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public class ReadViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.read_title)
        TextView title;
        @BindView(R.id.read_count)
        TextView count;
        @BindView(R.id.read_time)
        TextView time;
        @BindView(R.id.read_relative)
        RelativeLayout read_relative;
        public ReadViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    OnClickListeener onClickListeener;
    public void setOnClickListeener(OnClickListeener onClickListeener){
        this.onClickListeener=onClickListeener;
    }
    public interface OnClickListeener{
        void onSuccess(String id,int position,String title);
    }
}

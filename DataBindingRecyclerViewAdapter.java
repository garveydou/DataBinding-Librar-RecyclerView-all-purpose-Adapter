package com.NationalPhotograpy.weishoot.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Android on 2016/6/3.
 */
public class DataBindingRecyclerViewAdapter extends RecyclerView.Adapter<DataBindingRecyclerViewAdapter.DataBindingViewHolder> {

    Context mContext;
    int mLayoutId;
    int mVarId;
    private List mData;
    public static final int TYPE_HEADER = 1, TYPE_FOOTER = 2, TYPE_NORMAL = 0;
    boolean haveHeader = false;
    boolean haveFooter = false;
    View headerView,footerView;
    private ItemClickListener itemClickListener;
    public OnBindingViewHolderListener onBindingViewHolderListener;
    public DataBindingRecyclerViewAdapter(Context context, int layoutId,int varId,List data){
        mContext = context;
        mLayoutId = layoutId;
        mVarId = varId;
        mData = data;
    }

    public void setOnItemCkickListener(ItemClickListener listener){
        this.itemClickListener = listener;
    }
    public void setOnBindingViewHolderListener(OnBindingViewHolderListener listener){
        onBindingViewHolderListener = listener;
    }

    @Override
    public DataBindingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType){
            case TYPE_HEADER:
                return new DataBindingViewHolder(headerView,viewType);
            case TYPE_FOOTER:
                return new DataBindingViewHolder(footerView,viewType);
            case TYPE_NORMAL:
                return new DataBindingViewHolder(View.inflate(mContext,mLayoutId,null),viewType);
            default:
                return new DataBindingViewHolder(View.inflate(mContext,mLayoutId,null),viewType);
        }

    }

    @Override
    public void onBindViewHolder(final DataBindingViewHolder holder, final int position) {

        switch (holder.viewType){
            case TYPE_HEADER:
                break;
            case TYPE_FOOTER:
                break;
            case TYPE_NORMAL:
            default:
                ViewDataBinding binding = DataBindingUtil.bind(holder.itemView);
                Object data;
                if (haveHeader){
                    data = mData.get(position-1);
                }else{
                    data = mData.get(position);
                }

                if(itemClickListener !=null){
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            itemClickListener.itemClick(holder.itemView, position);
                        }
                    });
                }

                binding.setVariable(mVarId,data);
                binding.executePendingBindings();
                if(onBindingViewHolderListener!=null){
                    onBindingViewHolderListener.onHolderBinding(holder,position);
                }
                break;
        }

    }

    /**
     * 调用之后请调用NotifyDataSetChange 如果在setAdapter之后
     * @param view
     */
    public void addFooterView(View view){
        haveFooter=true;
        footerView = view;
    }

    /**
     * 调用之后请调用NotifyDataSetChange 如果在setAdapter之后
     * @param view
     */
    public void addHeaderView(View view){
        haveHeader=true;
        headerView = view;
    }

    public void removeFooterView(){
        footerView = null;
        haveFooter=false;
    }

    public View getFooterView(){
        return footerView;
    }

    public void cleanData(){
        mData.clear();
        notifyDataSetChanged();
    }

    public List getAllData(){
        return mData;
    }
    public void addData(List data){
        mData.addAll(data);
        notifyDataSetChanged();
    }
    @Override
    public int getItemCount() {
        int extraCount = 0;
        if(haveHeader){
            extraCount++;
        }else if(haveFooter){
            extraCount++;
        }
        return mData.size()+extraCount;
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0&&haveHeader){
            return TYPE_HEADER;
        }else if(position == mData.size()&&haveFooter){
            return TYPE_FOOTER;
        }else{
            return TYPE_NORMAL;
        }
    }

    public class DataBindingViewHolder extends RecyclerView.ViewHolder{

        int viewType;
        public DataBindingViewHolder(View itemView,int viewType) {
            super(itemView);
            this.viewType = viewType;
        }
    }

    public interface ItemClickListener{
        void itemClick(View view,int position);
    }

    public interface OnBindingViewHolderListener{
        void onHolderBinding(DataBindingViewHolder holder,int position);
    }
}

package com.open.dqmvvm.base;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.open.dqmvvm.databinding.ItemVmBinding;
import com.open.dqmvvm.main.VMItem;

/**
 * Created by Tink.Dan on 2018/1/5.
 */

public class BaseHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private View mConvertView;
    private Context mContext;

    public BaseHolder(Context context, View itemView) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<>();
    }


    public static BaseHolder get(Context context, ViewGroup parent, int layoutId) {
        View itemView = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return new BaseHolder(context, itemView);
    }

    public void bind(Object o){
        ((ItemVmBinding)DataBindingUtil.bind(itemView)).setLifecycleOwner((BaseActivity)mContext);
        ((ItemVmBinding)DataBindingUtil.bind(itemView)).setItem((VMItem) o);
    }

    /**
     * 通过viewId获取控件
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = mViews.get(viewId);
        if (view == null) {
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId, view);
        }
        return (T) view;
    }
}

package com.open.dqmvvm.base;

import android.content.Context;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tink.Dan on 2018/1/12.
 */

public abstract class BaseRvAdapter<T> extends RecyclerView.Adapter<BaseHolder> {

    protected Context context;
    protected List<T> items = new ArrayList<>();

    public BaseRvAdapter(Context context) {
        this.context = context;
        items = new ArrayList<>();
    }

    public void initItems(List<T> items){
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType){
        if (viewType == 1){
            return BaseHolder.get(context, parent, initLayout(viewType));
        }
        if (viewType == 2){
            return BaseHolder.get(context, parent, initLayout(viewType));
        }
        return BaseHolder.get(context, parent, initLayout(viewType));
    }

    @Override
    public abstract void onBindViewHolder(BaseHolder holder, final int position);

    @Override
    public int getItemCount() {
        return items.size();
    }

    public abstract int initLayout(int type);

    public void addItems(List<T> item){
        items.addAll(item);
        notifyDataSetChanged();
    }

    public void clearItems(){
        items.clear();
        notifyDataSetChanged();
    }
}

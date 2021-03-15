package com.nghiatl.common.loadmore;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<D> extends RecyclerView.ViewHolder {

    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
        //ButterKnife.bind(this, itemView);
    }

    protected abstract void setData(D item, int position);

    protected Context getContext() {
        return itemView.getContext();
    }
}
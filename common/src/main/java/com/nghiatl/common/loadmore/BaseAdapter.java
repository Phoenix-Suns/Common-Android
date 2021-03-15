package com.nghiatl.common.loadmore;

import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class BaseAdapter<D, VH extends BaseViewHolder> extends RecyclerView.Adapter<VH> {
    private final List<D> data = new ArrayList<>();

    public BaseAdapter() {
        init(new ArrayList<D>());
    }

    public BaseAdapter(List<D> data) {
        init(data);
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateViewHolderFactory(parent, viewType);
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(data.get(position), position);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    protected abstract VH onCreateViewHolderFactory(ViewGroup parent, int viewType);

    private void init(List<D> data) {
        this.data.addAll(data);
    }

    public void setData(List<D> data) {
        this.data.clear();
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public void addData(List<D> listOfItem) {
        int insertPosition = getItemCount();

        this.data.addAll(listOfItem);
        if (listOfItem.size() == 1)
            notifyItemInserted(insertPosition);
        else
            notifyItemRangeInserted(insertPosition, listOfItem.size());
    }

    public List<D> getData() {
        return data;
    }
}
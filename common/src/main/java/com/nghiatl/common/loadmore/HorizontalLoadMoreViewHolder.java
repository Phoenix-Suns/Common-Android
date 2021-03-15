package com.nghiatl.common.loadmore;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

public class HorizontalLoadMoreViewHolder extends BaseViewHolder<IItemViewModel> {

    public HorizontalLoadMoreViewHolder(ViewGroup parent, @LayoutRes int loadMoreLayout) {
        super(parent, loadMoreLayout);
    }

    @Override
    protected void setData(IItemViewModel item, int position) {

    }
}

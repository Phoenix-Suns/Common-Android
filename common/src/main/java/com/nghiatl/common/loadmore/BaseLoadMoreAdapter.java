package com.nghiatl.common.loadmore;

import android.view.ViewGroup;

import androidx.annotation.LayoutRes;

import java.util.List;

import static com.nghiatl.common.loadmore.IItemViewModel.LOAD_MORE_ITEM_VIEW_TYPE;

public abstract class BaseLoadMoreAdapter<D extends IItemViewModel, VH extends BaseViewHolder> extends BaseAdapter<D, VH> {

    private final int loadingLayout;
    private boolean isLoadMore = false;

    public BaseLoadMoreAdapter(@LayoutRes int loadingLayout) {
        this.loadingLayout = loadingLayout;
    }

    @Override
    public int getItemViewType(int position) {
        if (isLoadMore && position == getItemCount() - 1) {
            return LOAD_MORE_ITEM_VIEW_TYPE;
        }
        return getData().get(position).getItemViewType();
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_ITEM_VIEW_TYPE) {
            return onCreateLoadMoreViewHolder(parent, loadingLayout);
        }
        return onCreateViewHolderFactory(parent, viewType);
    }

    private VH onCreateLoadMoreViewHolder(ViewGroup parent, @LayoutRes int layoutRes) {
        return isVertical() ? (VH) new LoadMoreViewHolder(parent, loadingLayout) :
                (VH) new HorizontalLoadMoreViewHolder(parent, loadingLayout);
    }

    protected boolean isVertical() {
        return true;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        if (getItemViewType(position) != LOAD_MORE_ITEM_VIEW_TYPE) {
            holder.setData(getData().get(position), position);
        }
    }

    public void showLoadMore(boolean isLoadMore) {
        boolean previousState = this.isLoadMore;
        this.isLoadMore = isLoadMore;

        if (previousState != isLoadMore) {
            if (isLoadMore) {
                notifyItemInserted(super.getItemCount());
            } else {
                notifyItemRemoved(super.getItemCount());
            }
        }
    }

    public void addAll(List<D> data) {
        final int positionStart = getData().size() + 1;
        getData().addAll(data);
        notifyItemRangeInserted(positionStart, getData().size());
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + ((isLoadMore) ? 1 : 0);
    }
}
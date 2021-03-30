package com.nghiatl.common.view.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Spacing For RecyclerView Item
 * USING: mRecyclerView_Image.addItemDecoration(new DecorationGridSpacingItem(getResources().getDimensionPixelSize(R.dimen.grid_spacing), true))
 */
class ListItemSpacing(private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager is LinearLayoutManager) {
            val layoutManager = parent.layoutManager as LinearLayoutManager
            val position = parent.getChildAdapterPosition(view)

            if (layoutManager.orientation == LinearLayoutManager.HORIZONTAL) {
                outRect.left = spacing/2
                outRect.right = spacing/2

                if (includeEdge) {
                    if (position == 0) {
                        // First item
                        outRect.left = spacing
                    } else if (position == state.itemCount - 1 && state.itemCount > 0) {
                        // Last item
                        outRect.right = spacing
                    }
                } else {
                    if (position == 0) {
                        // First item
                        outRect.left = 0
                    } else if (position == state.itemCount - 1 && state.itemCount > 0) {
                        // Last item
                        outRect.right = 0
                    }
                }
            } else {
                outRect.top = spacing/2
                outRect.bottom = spacing/2

                if (includeEdge) {
                    if (position == 0) {
                        // First item
                        outRect.top = spacing
                    } else if (position == state.itemCount - 1 && state.itemCount > 0) {
                        // Last item
                        outRect.bottom = spacing
                    }
                } else {
                    if (position == 0) {
                        // First item
                        outRect.top = 0
                    } else if (position == state.itemCount - 1 && state.itemCount > 0) {
                        // Last item
                        outRect.bottom = 0
                    }
                }
            }
        } else if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanCount = layoutManager.spanCount
            val position = parent.getChildAdapterPosition(view)
            val column = position % spanCount

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount
                outRect.right = (column + 1) * spacing / spanCount

                // First Items
                if (position < spanCount) {
                    outRect.top = spacing
                }
                outRect.bottom = spacing
            } else {
                outRect.left = column * spacing / spanCount
                outRect.right = spacing - (column + 1) * spacing / spanCount
                if (position >= spanCount) {
                    outRect.top = spacing
                }
            }
        }
    }
}
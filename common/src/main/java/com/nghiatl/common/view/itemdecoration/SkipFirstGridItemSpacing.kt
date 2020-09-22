package com.nghiatl.common.view.itemdecoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Spacing For RecyclerView Item
 * Not apply first item
 * USING: mRecyclerView_Image.addItemDecoration(new DecorationGridSpacingItem(getResources().getDimensionPixelSize(R.dimen.grid_spacing), true))
 */
class SkipFirstGridItemSpacing(private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanCount = layoutManager.spanCount
            var position = parent.getChildAdapterPosition(view)

            if (position == 0) {
                // skip first

                outRect.top = spacing
                outRect.bottom = spacing

                if (includeEdge) {
                    outRect.left = spacing
                    outRect.right = spacing
                }

            } else {

                position += 1   // skip first => increase position
                val column = position % spanCount

                if (includeEdge) {
                    outRect.left = spacing - column * spacing / spanCount
                    outRect.right = (column + 1) * spacing / spanCount

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
}
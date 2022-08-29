package com.nghiatl.common.view.multistatelayout

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Scroll Bottom View (For Loading bottom)
 * Using:
recyclerView.addOnScrollListener(ScrollBottomListener(bottomView))

<FrameLayout
>
<androidx.recyclerview.widget.RecyclerView
android:layout_width="match_parent"
android:layout_height="match_parent"
android:clipToPadding="false"
android:paddingBottom="100dp"/>

<LinearLayout
android:id="@+id/bottomView"
android:layout_width="match_parent"
android:layout_height="wrap_content"
android:layout_marginBottom="0sp"
android:layout_gravity="bottom" />
</FrameLayout>
 */
class ShowRightViewScrollListener(private val rightView: View) :
    RecyclerView.OnScrollListener() {

    override fun onScrolled(
        recyclerView: RecyclerView,
        dx: Int,
        dy: Int
    ) {
        super.onScrolled(recyclerView, dx, dy)
        calculateShowBottom(recyclerView)
    }

    private fun calculateShowBottom(recyclerView: RecyclerView) {
        val layoutManager = recyclerView.layoutManager as LinearLayoutManager
        val totalItemCount = layoutManager.itemCount
        val lastVisibleItems = layoutManager.findLastVisibleItemPosition()

        // show right view when last item appear
        if (lastVisibleItems + 1 >= totalItemCount) {
            // left of right view = right of last item
            rightView.left = recyclerView.getChildAt(recyclerView.childCount - 1).right

            rightView.visibility = View.VISIBLE
        } else {
            rightView.visibility = View.GONE
        }
    }
}
package com.nghiatl.common.view

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Scroll Bottom View (For Loading bottom)
 * Using:
recyclerView.addOnScrollListener(ScrollBottomListener(bottomContainer))

<FrameLayout>
    <androidx.recyclerview.widget.RecyclerView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:clipToPadding="false"
        android:paddingBottom="100dp"/>
            <LinearLayout
                android:id="@+id/bottomContainer"
                android:layout_width="match_parent"
                android:layout_height="wrap_content"
                android:layout_marginBottom="0sp"
                android:layout_gravity="bottom" />
</FrameLayout>
 */
class ScrollBottomListener(private val bottomContainer: View) : RecyclerView.OnScrollListener() {
    /*override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)

        // Just Show on Bottom

        if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
            // IDLE on Bottom
            bottomContainer.visibility = View.VISIBLE
        } else {
            bottomContainer.visibility = View.GONE
        }
    }*/

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val mLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        val visibleItemCount = mLayoutManager.childCount;
        val totalItemCount = mLayoutManager.itemCount;
        val pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

        // when last item appear
        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
            bottomContainer.visibility = View.VISIBLE

            // bottom container scrolling distance
            val bottomDistance =
                (recyclerView.getChildAt(recyclerView.childCount - 1).bottom
                        + recyclerView.paddingBottom
                        - recyclerView.height)
            setMargins(bottomContainer, bottom = -bottomDistance)

        } else {
            bottomContainer.visibility = View.GONE
        }
    }

    private fun setMargins(
        view: View,
        start: Int? = null,
        top: Int? = null,
        end: Int? = null,
        bottom: Int? = null
    ) {
        if (view.layoutParams is ViewGroup.MarginLayoutParams) {
            val p = view.layoutParams as ViewGroup.MarginLayoutParams
            p.setMargins(
                start ?: p.leftMargin,
                top ?: p.topMargin,
                end ?: p.rightMargin,
                bottom ?: p.bottomMargin
            )
            view.requestLayout()
        }
    }
}
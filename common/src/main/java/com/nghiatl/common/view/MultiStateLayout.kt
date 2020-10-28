package com.nghiatl.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.FrameLayout
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.nghiatl.common.R
import com.nghiatl.common.extension.repairAutoAnimation

/** Loading, Empty, Content layout
 * - Using:
 <MultiStateLayout
        android:id="@+id/multipleStateView"
        app:empty_id="@id/viewEmpty"
        app:loading_center_id="@id/viewCenterLoading"
        app:loading_bottom_id="@id/viewBottomLoading"
        app:recycler_view_id="@id/recyclerView"
        app:SwipeRefreshLayout_id="">

        <!-- Content --!>

    </MultiStateLayout>

    - Change Layout:
        multipleStateView.showLoading()
        multipleStateView.showEmpty()
        multipleStateView.showContent()

    - Get View on Child Layout:
        multipleStateView.emptyView?.findViewById<View>(R.id.view_on_empty)
 */
class MultiStateLayout : FrameLayout {
    private var loadingBottomId: Int = LAYOUT_EMPTY
    private var loadingCenterId: Int = LAYOUT_EMPTY
    private var swipeRefreshLayoutId: Int = LAYOUT_EMPTY
    private var emptyId: Int = LAYOUT_EMPTY
    private var recyclerViewId: Int = LAYOUT_EMPTY

    var emptyView: View? = null
    var loadingCenterView: View? = null
    var loadingBottomView: View? = null
    var swipeRefreshLayout: SwipeRefreshLayout? = null
    var recyclerView: androidx.recyclerview.widget.RecyclerView? = null
    private var scrollBottomListener: ScrollBottomListener? = null

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.MultipleStateViewGroup,
            0,
            0
        )
        emptyId = typedArray.getResourceId(
            R.styleable.MultipleStateViewGroup_empty_id,
            LAYOUT_EMPTY
        )
        loadingCenterId = typedArray.getResourceId(
            R.styleable.MultipleStateViewGroup_loading_center_id,
            LAYOUT_EMPTY
        )
        loadingBottomId = typedArray.getResourceId(
            R.styleable.MultipleStateViewGroup_loading_bottom_id,
            LAYOUT_EMPTY
        )
        swipeRefreshLayoutId = typedArray.getResourceId(
            R.styleable.MultipleStateViewGroup_SwipeRefreshLayout_id,
            LAYOUT_EMPTY
        )
        recyclerViewId = typedArray.getResourceId(
            R.styleable.MultipleStateViewGroup_recycler_view_id,
            LAYOUT_EMPTY
        )
        typedArray.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()

        // Find views
        if (emptyId != LAYOUT_EMPTY)
            emptyView = findViewById(emptyId)
        if (loadingCenterId != LAYOUT_EMPTY)
            loadingCenterView = findViewById(loadingCenterId)
        if (loadingBottomId != LAYOUT_EMPTY) {
            loadingBottomView = findViewById(loadingBottomId)

            loadingBottomView?.let { loadingBottomView ->
                this.scrollBottomListener = ScrollBottomListener(loadingBottomView)
            }
        }
        if (recyclerViewId != LAYOUT_EMPTY) {
            recyclerView = findViewById(recyclerViewId)
        }
        if (swipeRefreshLayoutId != LAYOUT_EMPTY) {
            swipeRefreshLayout = findViewById(swipeRefreshLayoutId)
        }
    }

    fun showLoadingBottom(isShow: Boolean = true) {
        if (isShow) {
            loadingBottomView?.visibility = View.VISIBLE
            scrollBottomListener?.let { scrollBottomListener ->
                recyclerView?.addOnScrollListener(scrollBottomListener)
            }
        } else {
            loadingBottomView?.visibility = View.GONE
            scrollBottomListener?.let { scrollBottomListener ->
                recyclerView?.removeOnScrollListener(scrollBottomListener)
            }
        }
    }

    fun showState(isShow: Boolean, isFirstPage: Boolean, listSize: Int) {
        if (isShow) {
            if (isFirstPage) {
                if (listSize == 0) {
                    displayLayout(showCenterLoading = true)
                    return
                }
                displayLayout(showRefreshLayout = true)
                return
            }
            showLoadingBottom(true)
            return
        } else if (listSize == 0) {
            displayLayout(showEmpty = true)
            return
        }
        showContent()
    }

    fun showContent() {
        displayLayout()
        showLoadingBottom(false)
    }

    private fun displayLayout(
        showEmpty: Boolean = false,
        showCenterLoading: Boolean = false,
        showRefreshLayout: Boolean = false
    ) {
        repairAutoAnimation(100)
        emptyView?.visibility = if (showEmpty) View.VISIBLE else View.GONE
        loadingCenterView?.visibility = if (showCenterLoading) View.VISIBLE else View.GONE
        swipeRefreshLayout?.isRefreshing = showRefreshLayout
    }

    companion object {
        private const val LAYOUT_EMPTY = -1
    }
}
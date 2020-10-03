package com.nghiatl.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nghiatl.common.R
import com.nghiatl.common.extension.repairAutoAnimation

/** Loading, Empty, Content layout
 * - Using:
 * <MultipleStateView
        android:id="@+id/multipleStateView"
        app:layout_empty="@layout/layout_empty"
        app:layout_loading="@layout/layout_loading">

        <!-- Content --!>

    </MultipleStateView>

    - Change Layout:
        multipleStateView.showLoading()
        multipleStateView.showEmpty()
        multipleStateView.showContent()

    - Get View on Child Layout:
        multipleStateView.emptyView?.findViewById<View>(R.id.view_on_empty)
 */
class MultipleStateViewGroup : FrameLayout {
    var emptyView: View? = null
    var loadingView: View? = null
    private val LAYOUT_EMPTY = R.layout.layout_empty

    private var emptyLayoutId: Int = LAYOUT_EMPTY
    private var loadingLayoutId: Int = LAYOUT_EMPTY

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs, 0) {
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
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MultipleStateViewGroup, 0, 0)
        try {
            this.loadingLayoutId = typedArray.getResourceId(R.styleable.MultipleStateViewGroup_layout_loading, LAYOUT_EMPTY)
            this.emptyLayoutId = typedArray.getResourceId(R.styleable.MultipleStateViewGroup_layout_empty, LAYOUT_EMPTY)
        } finally {
            typedArray.recycle()
        }

        val inflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // add Empty layout
        emptyView = initLayout(inflater, emptyLayoutId)
        addView(emptyView)

        // add Loading layout
        loadingView = initLayout(inflater, loadingLayoutId)
        addView(loadingView)
    }

    private fun initLayout(inflater: LayoutInflater, layoutId: Int): View? {
        val emptyView = inflater.inflate(layoutId, null)
        emptyView.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        emptyView.elevation = 100f //
        emptyView.visibility = GONE
        return emptyView
    }

    fun showLoading() {
        displayLayout(showLoading = true, showEmpty = false)
    }

    fun showEmpty() {
        displayLayout(showLoading = false, showEmpty = true)
    }

    fun showContent() {
        displayLayout(showLoading = false, showEmpty = false)
    }

    private fun displayLayout(showLoading: Boolean, showEmpty: Boolean) {
        repairAutoAnimation(listener = null)
        loadingView?.visibility = if (showLoading) View.VISIBLE else View.GONE
        emptyView?.visibility = if (showEmpty) View.VISIBLE else View.GONE
    }
}


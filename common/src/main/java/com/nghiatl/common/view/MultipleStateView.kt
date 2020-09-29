package com.nghiatl.common.view

import android.content.Context
import android.transition.AutoTransition
import android.transition.Transition
import android.transition.TransitionManager
import android.transition.TransitionSet
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.nghiatl.common.R

private var LAYOUT_EMPTY = R.layout.layout_empty

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
        multipleStateView.emptyView?.findViewById<View>(R.id.btnReload)
 */
class MultipleStateView : FrameLayout {
    var emptyView: View? = null
    var loadingView: View? = null

    private var emptyLayoutId: Int = LAYOUT_EMPTY
    private var loadingLayoutId: Int = LAYOUT_EMPTY

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
        val typedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.MultipleStateView, 0, 0)
        try {
            this.loadingLayoutId = typedArray.getResourceId(R.styleable.MultipleStateView_layout_loading, LAYOUT_EMPTY)
            this.emptyLayoutId = typedArray.getResourceId(R.styleable.MultipleStateView_layout_empty, LAYOUT_EMPTY)
        } finally {
            typedArray.recycle()
        }

        val inflater = getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        // Empty layout
        emptyView = initLayout(inflater, emptyLayoutId)
        addView(emptyView)

        // Loading layout
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
        repairAutoAnimation(null)
        loadingView?.visibility = if (showLoading) View.VISIBLE else View.GONE
        emptyView?.visibility = if (showEmpty) View.VISIBLE else View.GONE
    }
}

/**
 * Auto Show/Hide Animation
 * @param viewGroup
 * @param listener
 */
fun ViewGroup?.repairAutoAnimation(
    listener: Transition.TransitionListener?
) {
    val transition = AutoTransition()
    transition.duration = 200
    transition.ordering = TransitionSet.ORDERING_TOGETHER
    if (listener != null) transition.addListener(listener)
    TransitionManager.beginDelayedTransition(this, transition)
}


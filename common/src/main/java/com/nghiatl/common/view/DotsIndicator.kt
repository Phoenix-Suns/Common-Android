package com.nghiatl.common.view

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.nghiatl.common.R
import com.nghiatl.common.extension.dp
import kotlin.math.max

class DotsIndicator : View {

    private var _recyclerView: RecyclerView? = null
    var recyclerView: RecyclerView?
        get() = _recyclerView
        set(value) {
            _recyclerView = value
            _recyclerView?.addOnScrollListener(object : RecyclerView.OnScrollListener() {

                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    invalidateTextPaintAndMeasurements()
                }
            })
            invalidateTextPaintAndMeasurements()
        }

    private var _dotsColor: Int? = null
    var dotColor: Int?
        get() = _dotsColor
        set(value) {
            _dotsColor = value
            invalidateTextPaintAndMeasurements()
        }

    private var _selectedDotColor: Int? = null
    var selectedDotColor: Int?
        get() = _selectedDotColor
        set(value) {
            _selectedDotColor = value
            invalidateTextPaintAndMeasurements()
        }

    private var _dotsSize: Float? = null
    var dotsSize: Float?
        get() = _dotsSize
        set(value) {
            _dotsSize = value
            invalidateTextPaintAndMeasurements()
        }

    private var _dotsSpacing: Float? = null
    var dotsSpacing: Float?
        get() = _dotsSpacing
        set(value) {
            _dotsSpacing = value
            invalidateTextPaintAndMeasurements()
        }

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private var indicatorHeight = 0
    private var indicatorItemPadding = 0
    private var radius = 0

    private val inactivePaint = Paint()
    private val activePaint = Paint()

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.DotsIndicator, defStyle, 0
        )

        _dotsColor = a.getColor(
            R.styleable.DotsIndicator_dotsColor,
            Color.GRAY
        )

        _selectedDotColor = a.getColor(
            R.styleable.DotsIndicator_selectedDotColor,
            Color.WHITE
        )

        _dotsSize = a.getDimension(
            R.styleable.DotsIndicator_dotsSize,
            20f
        )

        _dotsSpacing = a.getDimension(
            R.styleable.DotsIndicator_dotsSpacing,
            20f
        )

        a.recycle()

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements()
    }

    private fun invalidateTextPaintAndMeasurements() {
        // Setup paint
        val strokeWidth =
            Resources.getSystem().displayMetrics.density * 1
        this.radius = (dotsSize ?: 20.dp).toInt() / 2
        inactivePaint.strokeCap = Paint.Cap.ROUND
        inactivePaint.strokeWidth = strokeWidth
        inactivePaint.style = Paint.Style.FILL
        inactivePaint.isAntiAlias = true
        inactivePaint.color = dotColor ?: Color.GRAY

        activePaint.strokeCap = Paint.Cap.ROUND
        activePaint.strokeWidth = strokeWidth
        activePaint.style = Paint.Style.FILL
        activePaint.isAntiAlias = true
        activePaint.color = selectedDotColor ?: Color.WHITE
        indicatorItemPadding = (dotsSpacing ?: 20.dp).toInt()
        this.indicatorHeight = (dotsSize ?: 20.dp).toInt()

        //redraw the view
        invalidate();
        requestLayout();
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val adapter = recyclerView?.adapter ?: return
        val itemCount = adapter.itemCount

        // center horizontally, calculate width and subtract half from center
        val totalLength = radius * 2 * itemCount.toFloat()
        val paddingBetweenItems = max(0, itemCount - 1) * indicatorItemPadding.toFloat()
        val indicatorTotalWidth = totalLength + paddingBetweenItems
        val indicatorStartX = (width - indicatorTotalWidth) / 2f

        // center vertically in the allotted space
        val indicatorPosY = height - indicatorHeight / 2f
        drawInactiveDots(canvas, indicatorStartX, indicatorPosY, itemCount)
        val activePosition: Int = when (recyclerView?.layoutManager) {
            is GridLayoutManager -> (recyclerView?.layoutManager as GridLayoutManager).findFirstVisibleItemPosition()

            is LinearLayoutManager -> (recyclerView?.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()

            else -> return // not supported layout manager
        }
        if (activePosition == RecyclerView.NO_POSITION) {
            return
        }

        // find offset of active page if the user is scrolling
        val activeChild =
            recyclerView?.layoutManager?.findViewByPosition(activePosition) ?: return
        drawActiveDot(canvas, indicatorStartX, indicatorPosY, activePosition)
    }

    private fun drawInactiveDots(
        c: Canvas,
        indicatorStartX: Float,
        indicatorPosY: Float,
        itemCount: Int
    ) {
        // width of item indicator including padding
        val itemWidth = radius * 2 + indicatorItemPadding.toFloat()
        var start = indicatorStartX + radius
        for (i in 0 until itemCount) {
            c.drawCircle(start, indicatorPosY, radius.toFloat(), inactivePaint)
            start += itemWidth
        }
    }

    private fun drawActiveDot(
        c: Canvas, indicatorStartX: Float, indicatorPosY: Float,
        highlightPosition: Int
    ) {
        // width of item indicator including padding
        val itemWidth = radius * 2 + indicatorItemPadding.toFloat()
        val highlightStart =
            indicatorStartX + radius + itemWidth * highlightPosition
        c.drawCircle(highlightStart, indicatorPosY, radius.toFloat(), activePaint)
    }
}
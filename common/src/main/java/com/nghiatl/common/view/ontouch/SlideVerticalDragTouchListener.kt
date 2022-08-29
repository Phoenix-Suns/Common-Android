package com.nghiatl.common.view.ontouch

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener

private const val IDLE_DISTANCE_X = 10f
private const val IDLE_DISTANCE_Y = 10f
private const val LONG_PRESS_TIME = 1000L // 1s

/**
BubbleDragTouchListener onLisaMoveTouch = new BubbleDragTouchListener(container);
onLisaMoveTouch.setOnDragActionListener(new BubbleDragTouchListener.OnDragActionListener() {
//...
});
container.setOnTouchListener(onLisaMoveTouch);
 */
class SlideVerticalDragTouchListener @JvmOverloads constructor(
    val view: View,
    val parent: View = view.parent as View,
    var onDragActionListener: OnDragActionListener? = null
) : OnTouchListener {

    /**
     * Callback used to indicate when the drag is finished
     */
    interface OnDragActionListener {
        /**
         * Called when drag event is started
         *
         * @param view The view dragged
         */
        fun onDragStart(view: View?)

        /**
         * Called when drag event is completed
         *
         * @param view The view dragged
         */
        fun onDragEnd(view: View)
    }

    private var isDragging = false
    private var isInitialized = false

    private var xWhenAttached = 0f
    private var maxLeft = 0f
    private var maxRight = 0f
    private var centerHorizontal = 0f
    private var actionDownX = 0f
    private var dX = 0f

    private var yWhenAttached = 0f
    private var maxTop = 0f
    private var maxBottom = 0f
    private var centerVertical = 0f
    private var actionDownY = 0f
    private var dY = 0f

    private fun updateBounds() {
        updateViewBounds()
        updateParentBounds()
        isInitialized = true
    }

    private fun updateViewBounds() {
        xWhenAttached = view.x
        dX = 0f

        yWhenAttached = view.y
        dY = 0f
    }

    private fun updateParentBounds() {
        maxLeft = 0f
        maxRight = maxLeft + parent.width
        centerHorizontal = maxRight / 2

        maxTop = 0f
        maxBottom = maxTop + parent.height
        centerVertical = maxBottom / 2
    }

    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (isDragging) {
            // LEFT
            var left = event.rawX + dX
            if (left < maxLeft) {
                left = maxLeft
            }
            // RIGHT
            var right = left + view.width
            if (right > maxRight) {
                right = maxRight
                left = right - view.width
            }
            // TOP
            var top = event.rawY + dY
            if (top < maxTop) {
                top = maxTop
            }
            // BOTTOM
            var bottom = top + view.height
            if (bottom > maxBottom) {
                bottom = maxBottom
                top = bottom - view.height
            }
            when (event.action) {
                MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {

                    // Move to Edge
                    val moveToY = if (top <= centerVertical) {
                        0f // to Top edge
                    } else {
                        maxBottom
                    }
                    //Log.e("///", "top $top, centerVertical $centerVertical, moveToY $moveToY")
                    view.animate().y(moveToY).setDuration(250).start()
                    onDragFinish()
                }
                MotionEvent.ACTION_MOVE -> {
                    //Log.e("///", "top $top")
                    view.animate().y(top).setDuration(0).start()
                }
            }
            return true
        } else {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    isDragging = true
                    if (!isInitialized) {
                        updateBounds()
                    }

                    dX = v.x - event.rawX
                    dY = v.y - event.rawY
                    onDragActionListener?.onDragStart(view)

                    actionDownX = v.x
                    actionDownY = v.y
                    return true
                }
            }
        }


//        when (event.action) {
//            MotionEvent.ACTION_MOVE -> {
//                view.animate().y(top).setDuration(0).start()
//                return true
//            }
//        }
        return false
    }

    private fun onDragFinish() {
        onDragActionListener?.onDragEnd(view)

        dX = 0f
        dY = 0f
        isDragging = false
    }
}



package com.nghiatl.common.view.ontouch

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.View.OnTouchListener


/**
val containerOnDrag = DragDownTouchListener(container.parent as View)
containerOnDrag.onDragActionListener = object : DragDownTouchListener.OnDragActionListener { ... }
container.isClickable = false
container.setOnTouchListener(containerOnDrag)
 */
private const val CHANGE_STATE_DISTANCE = 150

class DragDownTouchListener constructor(val parent: View) : OnTouchListener {

    var onDragActionListener: OnDragActionListener? = null

    var dY = 0f
    var startY = 0f

    private var isDragging = false
    private var isInitialized = false

    private var maxTop = 0f
    private var maxBottom = 0f

    private fun updateBounds() {
        updateViewBounds()
        updateParentBounds()
        isInitialized = true
    }

    private fun updateViewBounds() {
        dY = 0f
    }

    private fun updateParentBounds() {
        maxTop = 0f
        maxBottom = maxTop + parent.height
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {

        // TOP
        var top = event.rawY + dY
        if (top < maxTop) {
            top = maxTop
        }

        when (event.action) {
            ACTION_DOWN -> {
                isDragging = true
                if (!isInitialized) {
                    updateBounds()
                }

                startY = view.y
                dY = view.y - event.rawY

                //onDragActionListener?.onDragStart()
            }
            ACTION_MOVE -> {
                view.y = top
            }
            MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> {

                val endY = view.y
                val distanceY = endY - startY

                // Drag down above 150px
                if (distanceY >= CHANGE_STATE_DISTANCE) {
                    // move to bottom
                    //view.y = maxBottom
                    view.animate().y(maxBottom).setDuration(250).setListener(object : AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            onDragActionListener?.onBottom()

                        }
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationRepeat(animation: Animator?) {}
                    }).start()
                } else {
                    // move to top
                    //view.y = 0f
                    view.animate().y(0f).setDuration(250).setListener(object : AnimatorListener {
                        override fun onAnimationStart(animation: Animator?) {}
                        override fun onAnimationEnd(animation: Animator?) {
                            onDragActionListener?.onTop()
                        }
                        override fun onAnimationCancel(animation: Animator?) {}
                        override fun onAnimationRepeat(animation: Animator?) {}
                    }).start()
                }

                onDragFinish()
            }
            else -> return false
        }
        return true
    }

    private fun onDragFinish() {
        //onDragActionListener?.onDragEnd()

        dY = 0f
        isDragging = false
    }

    /**
     * Callback used to indicate when the drag is finished
     */
    interface OnDragActionListener {
        //fun onDragStart()

        //fun onDragEnd()

        fun onTop()

        fun onBottom()
    }
}
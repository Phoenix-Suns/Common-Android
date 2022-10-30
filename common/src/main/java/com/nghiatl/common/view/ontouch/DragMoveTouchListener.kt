package com.nghiatl.common.view.ontouch

import android.view.MotionEvent
import android.view.MotionEvent.ACTION_DOWN
import android.view.MotionEvent.ACTION_MOVE
import android.view.View
import android.view.View.OnTouchListener

/**
DragDownTouchListener onLisaMoveTouch = new DragDownTouchListener();
container.setOnTouchListener(onLisaMoveTouch);
 */
class DragMoveTouchListener : OnTouchListener {
    var dX = 0f
    var dY = 0f

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            ACTION_DOWN -> {
                dX = view.x - event.rawX
                dY = view.y - event.rawY
            }
            ACTION_MOVE -> view.animate()
                .x(event.rawX + dX)
                .y(event.rawY + dY)
                .setDuration(0)
                .start()
            else -> return false
        }
        return true
    }
}
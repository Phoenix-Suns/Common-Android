package com.nghiatl.common.view.ontouch

import android.content.Context
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View

class OnSwipeCheckTouchListener(ctx: Context) : View.OnTouchListener {

    private val gestureDetector: GestureDetector = GestureDetector(ctx, GestureListener())

    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        // TODO Auto-generated method stub
        return gestureDetector.onTouchEvent(event)
    }

    private class GestureListener : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 60
        private val SWIPE_VELOCITY_THRESHOLD = 60

        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var result = false
            try {
                val diffY = e2.y - e1.y
                val diffX = e2.x - e1.x
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            onSwipeRight()
                        } else {
                            onSwipeLeft()
                        }
                    }
                    result = true
                } else if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHOLD) {
                    if (diffY > 0) {
                        onSwipeBottom()
                    } else {
                        onSwipeTop()
                    }
                }
                result = true
            } catch (exception: Exception) {
                exception.printStackTrace()
            }
            return result
        }

        fun onSwipeRight() {
            Log.i("Post", "right")
        }

        fun onSwipeLeft() {
            Log.i("Post", "left")
            //val intent = Intent(this@Swipe_Activity, Questions_Answers_Activity::class.java)
            //startActivity(intent)
        }

        fun onSwipeTop() {
            Log.i("Post", "Top")
        }

        fun onSwipeBottom() {
            Log.i("Post", "Bottom")
        }
    }



}

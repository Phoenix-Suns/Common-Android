package com.nghiatl.common.base

import android.content.Context
import android.content.Intent
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.nghiatl.common.functional.Dispatcher
import com.nghiatl.common.lifecycle.LifeRegister
import com.nghiatl.common.lifecycle.ResultLifecycle
import com.nghiatl.common.lifecycle.ResultRegistry

abstract class BaseActivity : AppCompatActivity(), Dispatcher {

    val resultLife: ResultLifecycle = ResultRegistry()
    val lifeRegister by lazy { LifeRegister.of(this) }
    private var mProgressBarHandler : ProgressBarHandler? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mProgressBarHandler = ProgressBarHandler(this)
    }

    protected open fun onActivityBackPressed() {
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        (resultLife as ResultRegistry).handleActivityResult(requestCode, resultCode, data)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        (resultLife as ResultRegistry).handlePermissionsResult(requestCode, permissions, grantResults)
    }

    protected fun showProgress() {
        mProgressBarHandler?.showProgress()
    }

    protected fun hideProgress() {
        mProgressBarHandler?.hideProgress()
    }

    /**
     * Clear focus on touch outside for all EditText inputs.
     */
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm!!.hideSoftInputFromWindow(v.windowToken, 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }

    /**
     * goto @baseFragment
     */
    open fun pushFragment(baseFragment: BaseFragment) {}

    /**
     * remove fragment in back stack
     */
    open fun popFragment() {}

    /**
     * goto @baseFragment fragment in back stack (it will remove back fragment in back stack)
     * ex: backstack[0,1,2,3] -> popToFragment(1) --> backstack[0,1] (fragment 2,3 will be removed)
     */
    open fun popToFragment(baseFragment: BaseFragment) {}

    /**
     * remove all fragment in back stack (except main fragments)
     */
    open fun popToRootFragment() {}
}
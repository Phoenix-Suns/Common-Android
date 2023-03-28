package com.vn.onewayradio.base

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.democommon.databinding.ActivityLoginBinding


abstract class BaseActivity<T : BasePresenter, VB: ViewDataBinding> : AppCompatActivity() {

    var presenter : T? = null
    protected lateinit var binding: VB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, layoutId())
        setContentView(layoutId())
        initPresenter()
        initView()
        start()
    }


    //Init layout ID
    abstract fun layoutId(): Int


    //Use for initialize Presenter, get bundle data from previous activity...
    abstract fun initPresenter()


    //Handle UI
    abstract fun initView()


    //Handle Data (Call Api)
    abstract fun start()


    //Click to refresh rootView when error
    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        start()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter?.unSubscribe()
    }


}

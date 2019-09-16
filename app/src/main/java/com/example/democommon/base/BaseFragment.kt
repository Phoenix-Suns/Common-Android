package com.vn.onewayradio.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


abstract class BaseFragment<T : BasePresenter> : Fragment() {


    private var isViewPrepare = false
    private var hasLoadData = false
    var presenter : T? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutId(),null)
    }


    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isVisibleToUser) {
            lazyLoadDataIfPrepared()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isViewPrepare = true
        initPresenter()
        initView()
        lazyLoadDataIfPrepared()
    }


    //Just load data once in fragment
    private fun lazyLoadDataIfPrepared() {
        if (userVisibleHint && isViewPrepare && !hasLoadData) {
            lazyLoad()
            hasLoadData = true
        }
    }


    //Init layout ID
    @LayoutRes
    abstract fun getLayoutId():Int


    //Use for initialize Presenter, get bundle data from previous activity...
    abstract fun initPresenter()


    //Handle UI
    abstract fun initView()


    //Handle Data (Call Api)
    abstract fun lazyLoad()


    open val mRetryClickListener: View.OnClickListener = View.OnClickListener {
        lazyLoad()
    }


    override fun onDestroy() {
        super.onDestroy()
        presenter?.unSubscribe()
    }


}
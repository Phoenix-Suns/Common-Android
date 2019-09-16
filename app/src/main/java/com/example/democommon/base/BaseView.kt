package com.vn.onewayradio.base

interface BaseView<T> {

    fun showLoading()

    fun dismissLoading()

    fun showError(errorMsg:String, errorCode:Int)

}
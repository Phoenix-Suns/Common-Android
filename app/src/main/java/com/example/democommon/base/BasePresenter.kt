package com.vn.onewayradio.base

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

abstract class BasePresenter  {


    var compositeDisposable = CompositeDisposable()


    fun addSubscription(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }


    fun unSubscribe() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }


}
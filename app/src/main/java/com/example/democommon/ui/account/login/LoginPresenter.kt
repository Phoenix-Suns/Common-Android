package com.example.democommon.ui.account.login

import com.example.democommon.api.RetrofitManager
import com.vn.onewayradio.api.exception.ExceptionHandle
import com.vn.onewayradio.base.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginPresenter(private val view: LoginView) : BasePresenter() {

    fun login(email: String, password: String) {
        view.showLoading()

        val disposable = RetrofitManager.service.login(email, password)
                .subscribeOn(Schedulers.newThread())        // “work” on io thread
                .observeOn(AndroidSchedulers.mainThread())  // “listen” on UIThread
                .subscribe({ it ->
                    view.apply {
                        dismissLoading()
                        onLoginSuccess(it.data)
                    }
                }, { t ->
                    view.apply {
                        dismissLoading()
                        showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                    }
                })

        addSubscription(disposable)
    }

    fun loginFacebook(accessToken: String) {
        view.showLoading()

        val disposable = RetrofitManager.service.loginFacebook(accessToken)
            .subscribeOn(Schedulers.newThread())        // “work” on io thread
            .observeOn(AndroidSchedulers.mainThread())  // “listen” on UIThread
            .subscribe({ it ->
                view.apply {
                    dismissLoading()
                    onLoginSuccess(it.data)
                }
            }, { t ->
                view.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)
    }


    fun loginGoogle(accessToken: String) {
        view.showLoading()

        val disposable = RetrofitManager.service.loginGoogle(accessToken)
            .subscribeOn(Schedulers.newThread())        // “work” on io thread
            .observeOn(AndroidSchedulers.mainThread())  // “listen” on UIThread
            .subscribe({ it ->
                view.apply {
                    dismissLoading()
                    onLoginSuccess(it.data)
                }
            }, { t ->
                view.apply {
                    dismissLoading()
                    showError(ExceptionHandle.handleException(t), ExceptionHandle.errorCode)
                }
            })

        addSubscription(disposable)
    }
}
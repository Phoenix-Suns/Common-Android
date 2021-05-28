package com.example.democommon.ui.account.login_mvp

import com.example.democommon.models.response.LoginRespond
import com.vn.onewayradio.base.BaseView

interface LoginView : BaseView<Any?> {

    fun onLoginSuccess(data: LoginRespond?)

}
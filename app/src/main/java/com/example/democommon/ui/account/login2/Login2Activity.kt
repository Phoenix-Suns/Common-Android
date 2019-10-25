package com.example.democommon.ui.account.login2

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.example.github.vo.Status
import com.example.democommon.BuildConfig
import com.example.democommon.R
import com.example.democommon.app.AppViewModelFactory
import com.example.democommon.extension.text
import com.example.democommon.extension.validate
import com.example.democommon.models.response.LoginRespond
import com.nghiatl.common.dialog.LoadingDialog
import com.nghiatl.common.extension.isEmailValid
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_login2.*
import timber.log.Timber
import javax.inject.Inject

class Login2Activity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: Login2ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        initView()
        initViewModel()
    }

    private fun initView() {
        if (BuildConfig.DEBUG) {
            editText_username.text = "luunghiatran@gmail.com"
            editText_password.text = "123456"
        }
    }

    private fun initViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(Login2ViewModel::class.java)
        viewModel.loginResult.observe(this, Observer { result ->
            when (result.status) {
                Status.LOADING -> {
                    showLoading()
                }
                Status.ERROR -> {
                    dismissLoading()
                    result.message?.let { showError(it) }
                }
                Status.SUCCESS -> {
                    dismissLoading()
                    loginSuccess(result.data)
                }
            }
        })
    }

    fun loginSuccess(data: LoginRespond?) {
        Timber.d("server token:%s", data?.token)
        Toast.makeText(this, "Login Success, Token: " + data?.token, Toast.LENGTH_LONG).show()
    }

    fun showLoading() {
        LoadingDialog.show(this)
    }

    fun dismissLoading() {
        LoadingDialog.dismiss()
    }

    fun showError(errorMsg: String) {
        Toast.makeText(this, errorMsg, Toast.LENGTH_LONG).show()
    }

    fun loginClick(view: View) {
        // TODO: abc
        // get data
        // validate
        // send server
        // receive data


        // đăng kí di view model
        // base viewmodel, activiy, fragment
        if (!validateInput()) {
            return
        }

        viewModel.login(editText_username.text, editText_password.text)
    }

    private fun validateInput(): Boolean {
        if (!editText_username.validate(getString(R.string.error_not_format_email)) { it.isEmailValid() }) {
            return false
        }

        if (!editText_password.validate(getString(R.string.error_not_empty)) { it.isNotEmpty() }) {
            return false
        }

        return true
    }
}

package com.example.democommon.ui.account.login_mvvm

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.android.example.github.vo.Status
import com.example.democommon.BuildConfig
import com.example.democommon.R
import com.example.democommon.app.AppViewModelFactory
import com.example.democommon.databinding.ActivityLogin2Binding
import com.example.democommon.extension.text
import com.example.democommon.extension.validate
import com.example.democommon.models.response.LoginRespond
import com.nghiatl.common.dialog.DialogUtil
import com.nghiatl.common.dialog.LoadingDialogView
import com.nghiatl.common.extension.isEmailValid
import dagger.android.AndroidInjection
import timber.log.Timber
import javax.inject.Inject

class Login2Activity : AppCompatActivity() {

    @Inject
    internal lateinit var viewModelFactory: AppViewModelFactory
    private lateinit var viewModel: Login2ViewModel
    protected lateinit var binding: ActivityLogin2Binding

    private val loadingDialog by lazy {
        LoadingDialogView(findViewById(R.id.container))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login2)
        setContentView(binding.root)

        initView()
        initViewModel()
    }

    private fun initView() {
        if (BuildConfig.DEBUG) {
            binding.editTextUsername.text = "luunghiatran@gmail.com"
            binding.editTextPassword.text = "123456"
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
        DialogUtil.showLoadingDialog(this, true)
    }

    fun dismissLoading() {

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

        viewModel.login(binding.editTextUsername.text, binding.editTextPassword.text)
    }

    private fun validateInput(): Boolean {
        if (!binding.editTextUsername.validate(getString(R.string.error_not_format_email)) { it.isEmailValid() }) {
            return false
        }

        if (!binding.editTextPassword.validate(getString(R.string.error_not_empty)) { it.isNotEmpty() }) {
            return false
        }

        return true
    }
}

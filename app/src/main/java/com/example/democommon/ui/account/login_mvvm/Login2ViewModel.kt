package com.example.democommon.ui.account.login_mvvm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.android.example.github.vo.Resource
import com.example.democommon.api.services.AccountService
import com.example.democommon.models.response.LoginRespond
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class Login2ViewModel @Inject constructor(val accountService: AccountService): ViewModel() {

    private val _loginResult = MutableLiveData<Resource<LoginRespond>>()
    val loginResult: LiveData<Resource<LoginRespond>>
        get() = _loginResult

    fun login(email: String, password: String) {
        _loginResult.postValue(Resource.loading(null))

        accountService.login(email, password)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _loginResult.postValue(Resource.success(it.data))
            }, { error ->
                _loginResult.postValue(Resource.error(""+error.message, null))
            })
    }
}
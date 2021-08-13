package com.example.democommon.ui.user_info_mvi_presenter

import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import com.google.gson.annotations.SerializedName

class GetUserMVIPresenter(private var view: GetUserMVIPresenterActivity) {
    private val compositeDisposable = CompositeDisposable()

    fun bind(view: GetUserMVIPresenterActivity) {
        this.view = view
        compositeDisposable.add(observeUserDisplay())
    }

    fun unbind() {
        if (!compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
    }

    private fun observeUserDisplay() = loadUserInfo()
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .doOnSubscribe { view?.render(MainState.LoadingState) }
        .doOnNext { view?.render(MainState.DataState(it)) }
        .subscribe()

    private fun loadUserInfo(): Observable<UserDetailModel> {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val githubService = retrofit.create(GithubWS::class.java)

        return githubService.getGitHubUserDetail("ahmedrizwan")
    }
}

sealed class MainState {
    object LoadingState : MainState()
    data class DataState(val data: UserDetailModel) : MainState()
}

internal interface GithubWS {
    @GET("users/{username}")
    fun getGitHubUserDetail(@Path("username") userName: String?): Observable<UserDetailModel>
}

class UserDetailModel() {
    @SerializedName("role")
    var role: String = ""
    @SerializedName("is_active")
    var isActive: Boolean = false
    @SerializedName("dial_code")
    var dialCode: String = ""
    @SerializedName("sex")
    var sex: String = ""
    @SerializedName("name")
    var name: String =""
    @SerializedName("created_at")
    var createdAt: String = ""
    @SerializedName("_id")
    var id: String = ""
    @SerializedName("type")
    var type: String = ""
    @SerializedName("email")
    var email: String = ""
    @SerializedName("phone")
    var phone: String = ""
    @SerializedName("picture")
    var picture: String = ""
    @SerializedName("dob")
    var dob: String = ""
    @SerializedName("total_follower")
    var totalFollower: Int = 0
    @SerializedName("total_following")
    var totalFollowing: Int = 0
    @SerializedName("is_follow")
    var isFollow: Boolean = false
}
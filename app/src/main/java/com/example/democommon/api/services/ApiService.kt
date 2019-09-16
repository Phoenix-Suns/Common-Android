package com.example.democommon.api.services

import com.example.democommon.models.BaseRespond
import com.example.democommon.models.LoginRespond
import com.example.democommon.api.UrlHelper
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService {
    @POST(UrlHelper.ENDPOINT_LOGIN)
    @FormUrlEncoded
    fun login(@Field("email") email: String,
              @Field("password") password: String,
              @Field("rule") rule: String = "user"): Observable<BaseRespond<LoginRespond>>

    @POST(UrlHelper.ENDPOINT_LOGIN_FACEBOOK)
    @FormUrlEncoded
    fun loginFacebook(@Field("access_token") accessToken: String): Observable<BaseRespond<LoginRespond>>

    @POST(UrlHelper.ENDPOINT_LOGIN_GOOGLE)
    @FormUrlEncoded
    fun loginGoogle(@Field("access_token") accessToken: String): Observable<BaseRespond<LoginRespond>>

    @POST(UrlHelper.ENDPOINT_REFRESH_TOKEN)
    @FormUrlEncoded
    fun refreshToken(@Field("email") email: String,
                     @Field("refreshToken") refreshToken: String): Call<BaseRespond<LoginRespond>>
}
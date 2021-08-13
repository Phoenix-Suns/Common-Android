package com.example.democommon.di.module

import android.app.Application
import com.example.democommon.BuildConfig
import com.example.democommon.api.services.AccountService
import com.example.democommon.app.AppPrefs
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.nghiatl.common.extension.toPrettyJSONString
import com.nghiatl.common.network.NetworkUtil
import dagger.Module
import dagger.Provides
import okhttp3.*
import okhttp3.internal.platform.Platform
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

private const val CATCH_SIZE = (10 * 1024 * 1024).toLong() // 10 MB
private const val CONNECT_TIMEOUT = 30L  // Second

@Module
class ApiModule {

    @Provides
    @Singleton
    internal fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()
        return gsonBuilder.create()
    }

    @Provides
    @Singleton
    internal fun provideCache(application: Application): Cache {
        val httpCacheDirectory = File(application.cacheDir, "http-cache")
        return Cache(httpCacheDirectory, CATCH_SIZE)
    }

    @Provides
    @Singleton
    internal fun provideOkhttpClient(cache: Cache, application: Application): OkHttpClient {
        val httpClient = OkHttpClient.Builder().apply {
            cache(cache)
            addNetworkInterceptor(headerInterceptor())          // Add request header
            // addInterceptor(cacheInterceptor(application))    // Save request Catch
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
        }

        // Write Log
        if (BuildConfig.DEBUG) {
            val logging = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message ->
                Timber.tag("OkHttp").i(message.toPrettyJSONString())
            })
            logging.level = HttpLoggingInterceptor.Level.BODY
            httpClient.addInterceptor(logging)
        }

        return httpClient.build()
    }

    @Provides
    @Singleton
    internal fun provideRetrofit(gson: Gson, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder().apply {
            addConverterFactory(GsonConverterFactory.create(gson))
            addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            baseUrl(BuildConfig.BASE_URL)
            client(okHttpClient)
        }.build()
    }

    @Provides
    @Singleton
    internal fun provideAccountService(retrofit: Retrofit): AccountService {
        return retrofit.create(AccountService::class.java)
    }

    /**
     * Add Header
     */
    private fun headerInterceptor(): Interceptor {
        return Interceptor { chain ->
            val token = AppPrefs.getAccessToken()
            val originalRequest = chain.request()
            val requestBuilder = originalRequest.newBuilder()

            if(token.isNotEmpty()) {
                requestBuilder .header("Authorization", "bearer $token")
                    .header("Content-Type", "application/json")
                    .method(originalRequest.method,originalRequest.body)
            }

            val request: Request
            request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * Save request Catch
     */
    private fun cacheInterceptor(application: Application): Interceptor {
        return Interceptor { chain ->

            var request = chain.request()
            val isNetworkConnected = NetworkUtil.isInternetConnected(application.applicationContext)

            if (!isNetworkConnected) {
                request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build()
            }
            val response = chain.proceed(request)
            if (isNetworkConnected) {
                val maxAge = 0
                response.newBuilder()
                    .header("Cache-Control", "public, max-age=$maxAge")
                    .removeHeader("Retrofit")
                    .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28
                response.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .removeHeader("nyn")
                    .build()
            }
            response
        }
    }
}
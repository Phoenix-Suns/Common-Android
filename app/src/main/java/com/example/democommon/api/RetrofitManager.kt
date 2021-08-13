package com.example.democommon.api

import android.app.Application
import com.example.democommon.api.services.ApiService
import com.example.democommon.app.AppPrefs
import com.example.democommon.app.MyApplication
import com.nghiatl.common.network.NetworkUtil
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

private const val CATCH_SIZE = (10 * 1024 * 1024).toLong() // 10 MB
private const val CONNECT_TIMEOUT = 30L  // Second

object RetrofitManager {

    private var client: OkHttpClient? = null
    private var retrofit: Retrofit? = null


    val service: ApiService by lazy {
        getRetrofit().create(ApiService::class.java)
    }


    private fun addQueryParameterInterceptor(): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val request: Request
            val modifiedUrl = originalRequest.url.newBuilder()
                    .addQueryParameter("phoneSystem", "")
                    .addQueryParameter("phoneModel", "")
                    .build()

            request = originalRequest.newBuilder().url(modifiedUrl).build()

            chain.proceed(request)
        }
    }

    private fun getRetrofit(): Retrofit {
        if (retrofit == null) {
            synchronized(RetrofitManager::class.java) {
                if (retrofit == null) {
                    val httpLoggingInterceptor = HttpLoggingInterceptor()
                    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

                    val cacheFile = File(MyApplication.context.cacheDir, "cache")
                    val cache = Cache(cacheFile, 1024 * 1024 * 10)

                    client = OkHttpClient.Builder().apply {
                        cache(cache)
                        addNetworkInterceptor(headerInterceptor())          // Add request header
                        addInterceptor(logInterceptor())                    // Write request Log
                        // addInterceptor(cacheInterceptor(application))    // Save request Catch
                        connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                        readTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
                    }.build()

                    retrofit = Retrofit.Builder()
                            .baseUrl(UrlHelper.BASE_URL)
                            .client(client!!)
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build()
                }
            }
        }

        return retrofit!!
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
                    .method(originalRequest.method ,originalRequest.body)
            }

            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    }

    /**
     * Write Log for Request
     */
    private fun logInterceptor(): Interceptor {
        return HttpLoggingInterceptor { message ->
            Timber.d(message)
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


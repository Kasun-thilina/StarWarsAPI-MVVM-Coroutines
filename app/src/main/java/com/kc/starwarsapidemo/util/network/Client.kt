package com.kc.starwarsapidemo.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Handler
import android.os.Looper
import com.google.gson.GsonBuilder
import com.kc.starwarsapidemo.R
import com.kc.starwarsapidemo.util.extensions.showToastLong
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit


const val TIME_OUT_DURATION = 40L
const val HEADER_CACHE_CONTROL = "Cache-Control"
const val HEADER_PRAGMA = "Pragma"

fun createNetworkClient(
    context: Context,
    baseUrl: String,
    debug: Boolean = false,
    supportInterceptor: SupportInterceptor
) = retrofitClient(baseUrl, httpClient(context, debug, supportInterceptor))

private fun myCache(context: Context): Cache {
    val cacheSize = (100.times(1024)
        .times(1024)
        .toLong())

    return Cache(File(context.cacheDir, "data"), cacheSize)
}

private fun httpClient(
    context: Context,
    debug: Boolean,
    supportInterceptor: SupportInterceptor
): OkHttpClient {
    val myCache = myCache(context)

    val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger.DEFAULT)
    val clientBuilder = OkHttpClient.Builder()
        .apply {
            cache(myCache)
            connectTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
            writeTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)
            readTimeout(TIME_OUT_DURATION, TimeUnit.SECONDS)

            if (debug) {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                addInterceptor(httpLoggingInterceptor)
            }
            addInterceptor(supportInterceptor)
            addNetworkInterceptor(networkInterceptor())
            addInterceptor(offlineInterceptor(context))
        }

    //clientBuilder.authenticator(supportInterceptor)
    return clientBuilder.build()
}

private fun retrofitClient(
    baseUrl: String,
    httpClient: OkHttpClient
): Retrofit {
    val gson = GsonBuilder().create();

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}


private fun networkInterceptor(): Interceptor = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        val cacheControl = CacheControl.Builder()
            .maxAge(5, TimeUnit.SECONDS)
            .build()
        return response.newBuilder()
            .removeHeader(HEADER_PRAGMA)
            .removeHeader(HEADER_CACHE_CONTROL)
            .header(HEADER_CACHE_CONTROL, cacheControl.toString())
            .build()
    }
}

private fun offlineInterceptor(context: Context): Interceptor = object : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!hasNetwork(context)!!) {
            val cacheControl = CacheControl.Builder()
                .maxStale(7, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .removeHeader(HEADER_PRAGMA)
                .removeHeader(HEADER_CACHE_CONTROL)
                .cacheControl(cacheControl)
                .build()
        }
        return chain.proceed(request)
    }
}

fun hasNetwork(context: Context): Boolean? {
    var isConnected: Boolean? = false
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
    if (activeNetwork != null && activeNetwork.isConnected) {
        isConnected = true
    } else {
        val handler = Handler(Looper.getMainLooper())
        handler.post {
            context.showToastLong(context.getString(R.string.msg_offline))
        }
    }
    return isConnected
}
package com.kc.starwarsapidemo.util.network

import okhttp3.*

class SupportInterceptor() : Interceptor, Authenticator {
    var accessToken: String? = null
    private var req: Request.Builder? = null
    private var callback: AuthenticatorCallBack? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        var request: Request.Builder = chain.request().newBuilder()

//        request.addHeader("x-api-key", apiKey)
        request.addHeader("Content-Type", "application/json")
        request.addHeader("Accept", "application/json")

//        if (!accessToken.isNullOrBlank())
//            request.addHeader("x-access-token", accessToken!!)

        return chain.proceed(request.build())
    }

    override fun authenticate(route: Route?, response: Response): Request? {

        return if (response.code != 200) {
            callback?.onUnAuthorizedResponse(response.code)
            null
        } else {
            response.request
                .newBuilder()
                .build()
        }
    }

    fun setAuthCallBackListner(authenticatorCallBack: AuthenticatorCallBack) {
        callback = authenticatorCallBack
    }

    interface AuthenticatorCallBack {
        fun onUnAuthorizedResponse(responseCode: Int)
    }
}

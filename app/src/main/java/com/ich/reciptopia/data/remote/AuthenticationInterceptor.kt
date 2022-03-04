package com.ich.reciptopia.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()

        val builder = original.newBuilder().header("Authorization", "Bearer ")

        val request = builder.build()

        return chain.proceed(request)
    }
}
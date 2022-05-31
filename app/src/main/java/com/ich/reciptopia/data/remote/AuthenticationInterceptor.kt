package com.ich.reciptopia.data.remote

import com.ich.reciptopia.application.ReciptopiaApplication
import okhttp3.Interceptor
import okhttp3.Response

class AuthenticationInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val authToken = ReciptopiaApplication.instance?.user?.value?.token

        val builder = original.newBuilder().header("Authorization", "Bearer ${authToken ?: ""}")

        val request = builder.build()

        return chain.proceed(request)
    }
}
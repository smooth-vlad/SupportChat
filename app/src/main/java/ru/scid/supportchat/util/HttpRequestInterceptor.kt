package ru.scid.supportchat.util

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import ru.scid.supportchat.domain.repositories.UserDataRepository

class HttpRequestInterceptor(private val userDataRepository: UserDataRepository) :
    Interceptor {

    private val CONTENT_TYPE_PARAM = "Content-Type"
    private val CONTENT_TYPE_VALUE = "application/json"
    private val AUTHORIZATION_PARAM = "Authorization"

    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        val requestBuilder = original.newBuilder().header(CONTENT_TYPE_PARAM, CONTENT_TYPE_VALUE)

        if (userDataRepository.useClientAuth) {
            requestBuilder.header(
                AUTHORIZATION_PARAM,
                Credentials.basic(userDataRepository.email, userDataRepository.password)
            )
        } else {
            requestBuilder.header(
                AUTHORIZATION_PARAM,
                Credentials.basic(userDataRepository.adminEmail + "/token", userDataRepository.adminToken)
            )
        }

        val request = requestBuilder.method(original.method, original.body).build()
        return chain.proceed(request)
    }

}
package com.nerdery.rtaza.mvvmdemo.data.core

import android.support.annotation.StringRes
import com.nerdery.rtaza.mvvmdemo.R
import java.net.HttpURLConnection

const val CODE_UNKNOWN = -1
const val CODE_CLIENT_IO = 50

/**
 * Classification of all errors known to the application.
 */
sealed class Error(
    @StringRes val messageResourceId: Int,
    val code: Int,
    throwable: Throwable?
) : kotlin.Exception(throwable?.message, throwable) {

    class HttpClientIo(throwable: Throwable) : Error(R.string.error_http_io, CODE_CLIENT_IO, throwable)

    class HttpServer(throwable: Throwable) :
        Error(R.string.error_http_server, HttpURLConnection.HTTP_INTERNAL_ERROR, throwable)

    class HttpUnauthorized(throwable: Throwable) :
        Error(R.string.error_http_unauthorized, HttpURLConnection.HTTP_UNAUTHORIZED, throwable)

    class Unknown(throwable: Throwable? = null) : Error(R.string.error_unknown, CODE_UNKNOWN, throwable)

    companion object {

        /**
         * Converts an error [code] to an [Error] instance.
         */
        fun getFromCode(code: Int, throwable: Throwable): Error {
            return when (code) {
                CODE_CLIENT_IO -> HttpClientIo(throwable)
                HttpURLConnection.HTTP_INTERNAL_ERROR -> HttpServer(throwable)
                HttpURLConnection.HTTP_UNAUTHORIZED -> HttpUnauthorized(throwable)
                else -> Unknown(throwable)
            }
        }
    }
}
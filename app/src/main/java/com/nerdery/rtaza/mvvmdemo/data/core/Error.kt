package com.nerdery.rtaza.mvvmdemo.data.core

import android.content.Context
import android.support.annotation.DrawableRes
import android.support.annotation.StringRes
import com.nerdery.rtaza.mvvmdemo.R
import java.net.HttpURLConnection

const val CODE_UNKNOWN = -1
const val CODE_CLIENT_IO = 50

/**
 * Classification of all errors known to the application.
 */
sealed class Error(
    @DrawableRes val iconResourceId: Int,
    @StringRes val titleResourceId: Int,
    @StringRes val descriptionResourceId: Int,
    val code: Int,
    throwable: Throwable?
) : kotlin.Exception(throwable?.message, throwable) {

    open fun getFullErrorMessage(context: Context): String = context.getString(
        R.string.format_error_title_description,
        context.getString(titleResourceId),
        context.getString(descriptionResourceId)
    )

    class HttpClientIo(throwable: Throwable) :
        Error(
            R.drawable.icon_error_client_io,
            R.string.error_title_http_io,
            R.string.error_description_http_io,
            CODE_CLIENT_IO,
            throwable
        )

    class HttpServer(throwable: Throwable) :
        Error(
            R.drawable.icon_error_server,
            R.string.error_title_http_server,
            R.string.error_description_http_server,
            HttpURLConnection.HTTP_INTERNAL_ERROR,
            throwable
        )

    class HttpUnauthorized(throwable: Throwable) :
        Error(
            R.drawable.icon_error_uauthorized,
            R.string.error_title_http_unauthorized,
            R.string.error_description_http_unauthorized,
            HttpURLConnection.HTTP_UNAUTHORIZED,
            throwable
        )

    class Unknown(throwable: Throwable? = null) :
        Error(
            R.drawable.icon_error_unknown,
            R.string.error_title_unknown,
            R.string.error_description_unknown,
            CODE_UNKNOWN,
            throwable
        ) {

        override fun getFullErrorMessage(context: Context): String = context.getString(R.string.error_title_unknown)
    }

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
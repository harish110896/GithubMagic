package com.example.githubmagic.data.util

import android.annotation.SuppressLint
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

sealed class Util {
    companion object {

        @SuppressLint("SimpleDateFormat")
        fun convertToLocalTime(timeStamp: String?): Date? {

            if (timeStamp.isNullOrBlank())
                return null

            val ustInputTime = SimpleDateFormat(Constants.DATE_FORMAT_UTC).let {
                it.timeZone = TimeZone.getTimeZone("UTC")
                it.parse(timeStamp)
            }

            return SimpleDateFormat(Constants.DATE_FORMAT_LOCAL).let {
                it.timeZone = TimeZone.getDefault()
                it.parse(it.format(ustInputTime))
            }
        }

        @JvmStatic
        fun getErrorMessage(ex: Throwable): String {

            return when (ex) {
                is HttpException -> {
                    when (ex.code()) {
                        400 -> "Bad Request"
                        401 -> "Unauthorised"
                        403 -> "Forbidden"
                        404 -> "NotFound"
                        500 -> "Internal server error"
                        503 -> "Service unavailable"
                        else -> {
                            when {
                                ex.code() / 100 == 4 -> "Client error"
                                ex.code() / 100 == 5 -> "Server error"
                                else -> "Unknown error occurred: ${ex.code()}"
                            }
                        }
                    }
                }
                is IOException -> "Internet not available"
                else -> "Error occurred: ${ex.message}"
            }
        }
    }
}

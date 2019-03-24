package com.twitter.core.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.twitter.core.exception.AppException
import retrofit2.HttpException
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.reflect.Type
import java.net.HttpURLConnection
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HttpErrorHandler @Inject constructor(
    private val gson: Gson
) {

  private companion object {
    const val REASON_CODE_JSON_NAME = "errorType"
    const val MESSAGE_JSON_NAME = "message"
  }

  fun handleError(throwable: Throwable) =
      throwable.takeIf { it is HttpException }
          ?.let {
            (it as HttpException)
                .let { httpException ->
                  httpException.response()
                      ?.takeUnless { response -> response.code() >= HttpURLConnection.HTTP_INTERNAL_ERROR }?.errorBody()
                      ?.let { errorBody ->
                        toApiError(
                            readJsonValue(errorBody.byteStream(), JsonObject::class.java, gson),
                            httpException
                        )
                      }
                    ?: httpException
                }
          }
        ?: throwable

  private fun toApiError(error: JsonObject, httpException: HttpException) =
      error.get(REASON_CODE_JSON_NAME)?.asString?.let { reasonCode ->
        AppException.ApiException(
            reasonCode,
            error[MESSAGE_JSON_NAME].asString,
            httpException
        )
      } ?: httpException

  private fun <T> readJsonValue(stream: InputStream, typeOfT: Type, gson: Gson): T =
      BufferedReader(InputStreamReader(stream)).use { gson.fromJson(it, typeOfT) }
}

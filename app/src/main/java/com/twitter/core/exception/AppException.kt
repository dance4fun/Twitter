package com.twitter.core.exception

sealed class AppException : RuntimeException {
  constructor(message: String? = null) : super(message)
  constructor(message: String? = null, cause: Throwable?) : super(message, cause)

  open class ServerException : AppException {
    constructor(message: String? = null) : super(message)
    constructor(message: String? = null, cause: Throwable?) : super(message, cause)
  }

  data class ApiException(
      @ErrorType val errorType: String,
      override val message: String? = null,
      override val cause: Throwable? = null
  ) : AppException.ServerException(message, cause)

  data class UnknownException(
      override val message: String? = null,
      override val cause: Throwable? = null
  ) : AppException(message, cause)

}

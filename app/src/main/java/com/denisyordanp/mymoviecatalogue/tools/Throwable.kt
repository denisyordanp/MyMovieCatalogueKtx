package com.denisyordanp.mymoviecatalogue.tools

import retrofit2.HttpException

const val HTTP_401 = "HTTP 401"

fun Throwable.isAuthError() = this is HttpException && this.message?.trim().equals(HTTP_401)

package com.denisyordanp.mymoviecatalogue.tools

import retrofit2.HttpException

const val HTTP_401 = 401

fun Throwable.isAuthError() = this is HttpException && this.code() == HTTP_401

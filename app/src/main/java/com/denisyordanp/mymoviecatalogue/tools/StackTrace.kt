package com.denisyordanp.mymoviecatalogue.tools

import com.denisyordanp.mymoviecatalogue.BuildConfig

object StackTrace {
    fun printStackTrace(throwable: Throwable) {
        if (BuildConfig.DEBUG) {
            throwable.printStackTrace()
        }
    }
}
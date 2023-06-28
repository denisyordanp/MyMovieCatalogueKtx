package com.denisyordanp.mymoviecatalogue.tools

fun Long.thousand(): String = String.format("%,d", this)
fun Double.oneDecimalFormat(): String = String.format("%.1f", this)